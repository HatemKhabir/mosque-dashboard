package com.hatemkhabir.mosque_dashboards.repository;

import com.hatemkhabir.mosque_dashboards.model.Mosque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MosqueRepository extends JpaRepository<Mosque,Long>, JpaSpecificationExecutor<Mosque> {

    List<Mosque> findByCountryIgnoreCaseAndCityIgnoreCase(String country,String city);
}
