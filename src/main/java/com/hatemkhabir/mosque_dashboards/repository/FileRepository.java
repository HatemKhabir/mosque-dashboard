package com.hatemkhabir.mosque_dashboards.repository;

import com.hatemkhabir.mosque_dashboards.model.FileResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileResource, Long> {
    List<FileResource> findAllByKhotbaId(Long khotbaId);
}
