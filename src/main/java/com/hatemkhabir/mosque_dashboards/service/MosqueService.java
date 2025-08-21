package com.hatemkhabir.mosque_dashboards.service;


import com.hatemkhabir.mosque_dashboards.dto.MosqueRegistrationDto;
import com.hatemkhabir.mosque_dashboards.model.Khotba;
import com.hatemkhabir.mosque_dashboards.model.Mosque;
import com.hatemkhabir.mosque_dashboards.pagination.MosqueSpecifications;
import com.hatemkhabir.mosque_dashboards.repository.MosqueRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final SuperAdminService superAdminService;

    public Long registerMosque(MosqueRegistrationDto mosqueData){
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
        return mosqueRepository.findAll(MosqueSpecifications.hasCountry(country).or(MosqueSpecifications.hasCity(city))).stream().toList();
    }

    public void deleteMosque(Long mosqueId){
        try {
            Optional<Mosque> existingMosque=mosqueRepository.findById(mosqueId);
            if (existingMosque.isEmpty()){
                throw new EntityNotFoundException("Mosque Not Found");
            }
            mosqueRepository.deleteById(mosqueId);
        }catch (Exception e){
            log.error("Error deleting mosque {}",e.getMessage());
        }
        }

    @Transactional
    public boolean verifyMosque(Long mosqueId){
        Mosque existingMosque=mosqueRepository.findById(mosqueId).orElseThrow(()->new EntityNotFoundException("Mosque not found"));
        if (existingMosque.isVerified())
            return true;
        existingMosque.setVerified(true);
        superAdminService.sendCredentials(existingMosque);
        mosqueRepository.save(existingMosque);
    return existingMosque.isVerified();
    }


}
