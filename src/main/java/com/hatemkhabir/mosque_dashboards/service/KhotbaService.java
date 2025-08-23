package com.hatemkhabir.mosque_dashboards.service;


/*
* uploadKhotba(khotbaData, files, creatorId) - Create new khutbah with files
getKhotbaDetails(khotbaId) - Retrieve khutbah information
updateKhotba(khotbaId, khotbaData) - Update khutbah details
publishKhotba(khotbaId) - Make khutbah publicly available

* */

import com.hatemkhabir.mosque_dashboards.common.KhotbaLanguage;
import com.hatemkhabir.mosque_dashboards.common.KhotbaType;
import com.hatemkhabir.mosque_dashboards.dto.Khotba.KhotbaRegistrationDTO;
import com.hatemkhabir.mosque_dashboards.mapper.KhotbaMapper;
import com.hatemkhabir.mosque_dashboards.model.FileResource;
import com.hatemkhabir.mosque_dashboards.model.Khotba;
import com.hatemkhabir.mosque_dashboards.model.Mosque;
import com.hatemkhabir.mosque_dashboards.pagination.KhotbaSpecifications;
import com.hatemkhabir.mosque_dashboards.repository.KhotbaRepository;
import com.hatemkhabir.mosque_dashboards.repository.MosqueRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KhotbaService {

    private final KhotbaRepository khotbaRepository;
    private final MosqueRepository mosqueRepository;
    private final FileService fileService;

    public Page<KhotbaRegistrationDTO> getKhotbasByFilter(Long mosqueId, KhotbaLanguage khotbaLanguage, KhotbaType khotbaType,Pageable page){
        return khotbaRepository.findAll(KhotbaSpecifications.specificMosque(mosqueId).or(KhotbaSpecifications.hasLanguage(khotbaLanguage)).or(KhotbaSpecifications.hasType(khotbaType)),page).map(KhotbaMapper::KhotbaResponseMapper);
    }
    public Page<KhotbaRegistrationDTO> getAllKhotbas(Pageable page){
        return khotbaRepository.findAll(page).map(KhotbaMapper::KhotbaResponseMapper);
    }

    public Khotba createKhotbaByText(KhotbaRegistrationDTO khotbaDto){
        Mosque mosque= mosqueRepository.findById(khotbaDto.getMosqueId()).orElseThrow(()->new EntityNotFoundException("mosque not found"));
        Khotba khotba=Khotba.builder()
                .mosque(mosque)
                .content(khotbaDto.getContent())
                .khotbaType(KhotbaType.valueOf(khotbaDto.getKhotbaType().toUpperCase()))
                .title(khotbaDto.getTitle())
                .officialLanguage(KhotbaLanguage.valueOf(khotbaDto.getOfficialLanguage().toUpperCase()))
                .approved(false)
                .build();
        return khotbaRepository.save(khotba);
    }

    public void deleteKhotba(Long khotbaId){
        Khotba khotba=khotbaRepository.findById(khotbaId).orElseThrow(()->new EntityNotFoundException("Khotba not found"));
        try {
            fileService.deleteFilesByKhotba(khotba);
            khotbaRepository.delete(khotba);
            log.info("Khotba with id {} is successfully deleted ! ",khotbaId);
        }catch (Exception e){
            log.error("Khotba failed to deleted with error {}",e.getMessage());
        }
    }

    public FileResource uploadKhotbaFile(Long khotbaId, MultipartFile file, String fileType) {
        Khotba khotba = khotbaRepository.findById(khotbaId)
                .orElseThrow(() -> new EntityNotFoundException("Khotba not found"));

        try {
            return fileService.uploadFile(file, fileType, khotba);
        } catch (IOException e) {
            log.error("Error uploading file for khotba {}: {}", khotbaId, e.getMessage());
            throw new RuntimeException("File upload failed", e);
        }
    }

    public List<FileResource> getKhotbaFiles(Long khotbaId) {
        return fileService.listFiles(khotbaId);
    }

}
