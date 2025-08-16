package com.hatemkhabir.mosque_dashboards.dto;

import com.hatemkhabir.mosque_dashboards.common.KhotbaLanguage;
import com.hatemkhabir.mosque_dashboards.model.Mosque;
import com.hatemkhabir.mosque_dashboards.model.MosqueAdmin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MosqueRegistrationDto {

    private Long id;
    private String country;
    private String city;
    private String address;
    private MosqueAdmin mosqueAdmin;
    private Boolean verified;
    private String mosqueName;

}
