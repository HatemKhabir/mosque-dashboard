package com.hatemkhabir.mosque_dashboards.pagination;

import com.hatemkhabir.mosque_dashboards.model.Mosque;
import org.springframework.data.jpa.domain.Specification;

public class MosqueSpecifications {

    public static Specification<Mosque> hasCountry(String country){
        return (root, query, criteriaBuilder) ->
                country==null?null:criteriaBuilder.equal(criteriaBuilder.lower(root.get("country")),country.toLowerCase());
    }

    public static Specification<Mosque> hasCity(String city) {
        return (root, query, cb) ->
                city == null ? null : cb.equal(cb.lower(root.get("city")), city.toLowerCase());
    }


}
