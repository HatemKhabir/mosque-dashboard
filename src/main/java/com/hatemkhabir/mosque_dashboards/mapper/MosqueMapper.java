package com.hatemkhabir.mosque_dashboards.mapper;

import com.hatemkhabir.mosque_dashboards.dto.Mosque.MosqueResponseDto;
import com.hatemkhabir.mosque_dashboards.model.Mosque;

public class MosqueMapper {
    public static MosqueResponseDto mapToDto(Mosque mosque){
        MosqueResponseDto dto=new MosqueResponseDto();
        dto.setId(mosque.getId());
        dto.setMosqueName(mosque.getMosqueName());
        dto.setCountry(mosque.getCountry());
        dto.setCity(mosque.getCity());
        dto.setAddress(mosque.getAddress());
        dto.setVerified(mosque.isVerified());
        return dto;
    }
}
