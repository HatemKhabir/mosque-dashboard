package com.hatemkhabir.mosque_dashboards.dto.Khotba;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KhotbaRegistrationDTO {

    private Long mosqueId; // link khotba to mosque
    private String title;
    private String officialLanguage; // enum as String
    private String content;
    private String khotbaType;
    private Long khotbaId;
}
