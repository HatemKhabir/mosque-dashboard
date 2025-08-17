package com.hatemkhabir.mosque_dashboards.repository;

import com.hatemkhabir.mosque_dashboards.model.Khotba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface KhotbaRepository extends JpaRepository<Khotba, Long>, JpaSpecificationExecutor<Khotba> {
}
