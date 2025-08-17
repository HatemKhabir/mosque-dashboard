package com.hatemkhabir.mosque_dashboards.service;


/*
* uploadKhotba(khotbaData, files, creatorId) - Create new khutbah with files
getKhotbaDetails(khotbaId) - Retrieve khutbah information
updateKhotba(khotbaId, khotbaData) - Update khutbah details
publishKhotba(khotbaId) - Make khutbah publicly available

* */

import com.hatemkhabir.mosque_dashboards.common.KhotbaLanguage;
import com.hatemkhabir.mosque_dashboards.common.KhotbaType;
import com.hatemkhabir.mosque_dashboards.model.Khotba;
import com.hatemkhabir.mosque_dashboards.pagination.KhotbaSpecifications;
import com.hatemkhabir.mosque_dashboards.repository.KhotbaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KhotbaService {

    private final KhotbaRepository khotbaRepository;

    public Page<Khotba> getKhotbasByFilter(Long mosqueId, KhotbaLanguage khotbaLanguage, KhotbaType khotbaType,Pageable page){
        return khotbaRepository.findAll(KhotbaSpecifications.specificMosque(mosqueId).or(KhotbaSpecifications.hasLanguage(khotbaLanguage)).or(KhotbaSpecifications.hasType(khotbaType)),page);
    }

    public void deleteKhotba(Long khotbaId){
        Khotba khotba=khotbaRepository.findById(khotbaId).orElseThrow(()->new EntityNotFoundException("Khotba not found"));
        try {
            khotbaRepository.delete(khotba);
            log.info("Khotba with id {} is successfully deleted ! ",khotbaId);
        }catch (Exception e){
            log.error("Khotba failed to deleted with error {}",e.getMessage());
        }
    }



}
