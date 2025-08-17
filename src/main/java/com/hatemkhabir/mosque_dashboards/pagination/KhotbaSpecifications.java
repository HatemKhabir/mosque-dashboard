package com.hatemkhabir.mosque_dashboards.pagination;

import com.hatemkhabir.mosque_dashboards.common.KhotbaLanguage;
import com.hatemkhabir.mosque_dashboards.common.KhotbaType;
import com.hatemkhabir.mosque_dashboards.model.Khotba;
import org.springframework.data.jpa.domain.Specification;

public class KhotbaSpecifications {

    public static Specification<Khotba> hasLanguage(KhotbaLanguage khotbaLanguage){
        return (root, query, criteriaBuilder) -> {
            if (khotbaLanguage.getEnglishName().isEmpty())
               return  criteriaBuilder.conjunction();
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("officialLanguage")),khotbaLanguage.getEnglishName().toLowerCase());
        };
    }

    public static Specification<Khotba> specificMosque(Long mosqueId){
        return (root, query, criteriaBuilder) -> {
            if (mosqueId==null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("mosque").get("id"),mosqueId);
        };
    }

    public static Specification<Khotba> hasType(KhotbaType khotbaType){
        return (root, query, criteriaBuilder) -> {
            if (khotbaType.getDisplayName().isEmpty())
                criteriaBuilder.conjunction();
        return criteriaBuilder.equal(criteriaBuilder.lower(root.get("khotbaType")),khotbaType.getDisplayName().toLowerCase());
        };
    }

}
