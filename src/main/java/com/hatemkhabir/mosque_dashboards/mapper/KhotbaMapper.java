package com.hatemkhabir.mosque_dashboards.mapper;

import com.hatemkhabir.mosque_dashboards.common.KhotbaType;
import com.hatemkhabir.mosque_dashboards.dto.Khotba.KhotbaRegistrationDTO;
import com.hatemkhabir.mosque_dashboards.model.Khotba;

public class KhotbaMapper {

    public static KhotbaRegistrationDTO KhotbaResponseMapper(Khotba khotba){
        return KhotbaRegistrationDTO.builder()
                .khotbaType(khotba.getKhotbaType().getDisplayName())
                .title(khotba.getTitle())
                .content(khotba.getContent())
                .officialLanguage(khotba.getOfficialLanguage().getEnglishName())
                .mosqueId(khotba.getId())
                .khotbaId(khotba.getId())
                .build();
    }
}
