package com.hatemkhabir.mosque_dashboards.controller;


import com.hatemkhabir.mosque_dashboards.common.KhotbaLanguage;
import com.hatemkhabir.mosque_dashboards.common.KhotbaType;
import com.hatemkhabir.mosque_dashboards.dto.Khotba.KhotbaRegistrationDTO;
import com.hatemkhabir.mosque_dashboards.model.Khotba;
import com.hatemkhabir.mosque_dashboards.service.KhotbaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/khotbas")
@RequiredArgsConstructor
public class KhotbaRestController {

    private final KhotbaService khotbaService;

    @GetMapping()
    public ResponseEntity<Page<KhotbaRegistrationDTO>> getAllKhotba(Pageable page){
        return ResponseEntity.ok(khotbaService.getAllKhotbas(page));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<KhotbaRegistrationDTO>> getKhotbasByFilter(@RequestParam(required = false) Long mosqueId, @RequestParam(required = false)KhotbaLanguage khotbaLanguage, @RequestParam(required = false)KhotbaType khotbaType,Pageable page){
        return ResponseEntity.ok(khotbaService.getKhotbasByFilter(mosqueId, khotbaLanguage, khotbaType, page));
    }

    @PostMapping
    public ResponseEntity<KhotbaRegistrationDTO> createKhotbaFromText(@RequestBody KhotbaRegistrationDTO khotba){
        Khotba savedKhotba=khotbaService.createKhotbaByText(khotba);
        return ResponseEntity.status(HttpStatus.CREATED).body(khotba);
    }

    @DeleteMapping("/{khotbaId}")
    public ResponseEntity<Void> deleteKhotba(@PathVariable Long khotbaId){
        khotbaService.deleteKhotba(khotbaId);
        return ResponseEntity.noContent().build();
    }


}
