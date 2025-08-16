package com.hatemkhabir.mosque_dashboards.service;


import com.hatemkhabir.mosque_dashboards.dto.MosqueRegistrationDto;
import com.hatemkhabir.mosque_dashboards.model.Khotba;
import com.hatemkhabir.mosque_dashboards.model.Mosque;
import com.hatemkhabir.mosque_dashboards.model.MosqueAdmin;
import com.hatemkhabir.mosque_dashboards.repository.MosqueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public Long registerMosque(MosqueRegistrationDto mosqueData, Long adminId){
        Optional<Mosque> existingMosque=mosqueRepository.findById(mosqueData.getId());
        if (existingMosque.isPresent()){
            return existingMosque.get().getId();
        }
        Mosque newMosque=Mosque.builder()
                .id(mosqueData.getId())
                .mosqueAdmin(mosqueData.getMosqueAdmin())
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

    public List<Mosque> getMosqueByCountry

    public void verifyMosque(Long mosqueId){

    }

}
