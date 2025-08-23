package com.hatemkhabir.mosque_dashboards.dto.Mosque;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MosqueRegistrationDto {

    private String country;
    private String city;
    private String address;
    private String adminEmail;
    private String adminPhone;
    private Boolean verified;
    private String mosqueName;

}
