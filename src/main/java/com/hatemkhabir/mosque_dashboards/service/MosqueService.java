package com.hatemkhabir.mosque_dashboards.service;


import com.hatemkhabir.mosque_dashboards.dto.Mosque.MosqueRegistrationDto;
import com.hatemkhabir.mosque_dashboards.dto.Mosque.MosqueResponseDto;
import com.hatemkhabir.mosque_dashboards.mapper.MosqueMapper;
import com.hatemkhabir.mosque_dashboards.model.Mosque;
import com.hatemkhabir.mosque_dashboards.model.MosqueAdmin;
import com.hatemkhabir.mosque_dashboards.pagination.MosqueSpecifications;
import com.hatemkhabir.mosque_dashboards.repository.MosqueAdminRepository;
import com.hatemkhabir.mosque_dashboards.repository.MosqueRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
    private final MosqueAdminRepository mosqueAdminRepository;
    private final SuperAdminService superAdminService;

    @Transactional
    public Long registerMosque(MosqueRegistrationDto mosqueData){
   //add duplicate mosque constraint
        log.info("mosque data received , {}",mosqueData);
        MosqueAdmin mosqueAdmin=MosqueAdmin.builder()
                .email(mosqueData.getAdminEmail())
                .phoneNumber(mosqueData.getAdminPhone())
                .build();
        mosqueAdminRepository.save(mosqueAdmin);
        Mosque newMosque=Mosque.builder()
                .adminEmail(mosqueData.getAdminEmail())
                .adminPhone(mosqueData.getAdminPhone())
                .mosqueAdmin(mosqueAdmin)
                .mosqueName(mosqueData.getMosqueName())
                .mosqueKhotbas(new ArrayList<>())
                .city(mosqueData.getCity())
                .country(mosqueData.getCountry())
                .address(mosqueData.getAddress())
                .verified(false).build();
        mosqueRepository.save(newMosque);
        return newMosque.getId();
    }

    public Mosque getMosqueDetails(Long mosqueId){
        return mosqueRepository.findById(mosqueId).orElseThrow();
    }

    public List<MosqueResponseDto> listMosques(String country, String city){
        return mosqueRepository.findAll(
                MosqueSpecifications.hasCountry(country).or(MosqueSpecifications.hasCity(city))
                )
                .stream()
                .map(MosqueMapper::mapToDto).toList();
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
