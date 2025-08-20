package com.hatemkhabir.mosque_dashboards.service;


import com.hatemkhabir.mosque_dashboards.common.KhotbaLanguage;
import com.hatemkhabir.mosque_dashboards.common.KhotbaType;
import com.hatemkhabir.mosque_dashboards.dto.MosqueRegistrationDto;
import com.hatemkhabir.mosque_dashboards.model.Khotba;
import com.hatemkhabir.mosque_dashboards.model.Mosque;
import com.hatemkhabir.mosque_dashboards.model.MosqueAdmin;
import com.hatemkhabir.mosque_dashboards.pagination.MosqueSpecifications;
import com.hatemkhabir.mosque_dashboards.repository.KhotbaRepository;
import com.hatemkhabir.mosque_dashboards.repository.MosqueRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
* registerMosque(mosqueData, adminId) - Create new mosque profile
getMosqueDetails(mosqueId) - Retrieve mosque information
updateMosqueDetails(mosqueId, mosqueData) - Update mosque profile
listMosques(filters, pageable) - Search/list mosques with filters
getMosquesByLocation(country, city) - Find mosques by location
verifyMosque(mosqueId) - Mark mosque as verified
* */

@Service
@Slf4j
@RequiredArgsConstructor
public class MosqueService {

    private final MosqueRepository mosqueRepository;
    private final MosqueAdminService mosqueAdminService;

    public Long registerMosque(MosqueRegistrationDto mosqueData, Long adminId){
        Optional<Mosque> existingMosque=mosqueRepository.findById(mosqueData.getId());
        if (existingMosque.isPresent()){
            return existingMosque.get().getId();
        }
        Mosque newMosque=Mosque.builder()
                .id(mosqueData.getId())
                .adminEmail(mosqueData.getAdminEmail())
                .adminPhone(mosqueData.getAdminPhone())
                .mosqueName(mosqueData.getMosqueName())
                .mosqueKhotbas(new ArrayList<Khotba>())
                .city(mosqueData.getCity())
                .country(mosqueData.getCountry())
                .address(mosqueData.getAddress())
                .verified(false).build();
        return newMosque.getId();
    }

    public Mosque getMosqueDetails(Long mosqueId){
        return mosqueRepository.findById(mosqueId).orElseThrow();
    }

    public List<Mosque> listMosques(String country, String city){
        return mosqueRepository.findAll(MosqueSpecifications.hasCity(country).or(MosqueSpecifications.hasCity(city))).stream().toList();
    }

    @Transactional
    public boolean verifyMosque(Long mosqueId){
        Mosque existingMosque=mosqueRepository.findById(mosqueId).orElseThrow(()->new EntityNotFoundException("Mosque not found"));
        if (existingMosque.getVerified())
            return true;
        existingMosque.setVerified(true);
        mosqueAdminService.receiveCredentials(existingMosque);
        mosqueRepository.save(existingMosque);
    return existingMosque.getVerified();
    }


}
