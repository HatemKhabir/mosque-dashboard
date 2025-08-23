package com.hatemkhabir.mosque_dashboards.controller;


import com.hatemkhabir.mosque_dashboards.dto.Mosque.MosqueRegistrationDto;
import com.hatemkhabir.mosque_dashboards.dto.Mosque.MosqueResponseDto;
import com.hatemkhabir.mosque_dashboards.model.Mosque;
import com.hatemkhabir.mosque_dashboards.service.MosqueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mosque")
@Slf4j
@RequiredArgsConstructor
public class MosqueRestController {

    private final MosqueService mosqueService;

    @GetMapping
    public ResponseEntity<List<MosqueResponseDto>> getAllMosques(@RequestParam(required = false) String city, @RequestParam(required = false) String country){
        return ResponseEntity.ok(mosqueService.listMosques(country,city));
    }

    @PostMapping
    public ResponseEntity<?> registerMosque(@RequestBody MosqueRegistrationDto mosqueRegistration){
        log.info("Registration called");
        Long mosqueId=mosqueService.registerMosque(mosqueRegistration);

        return ResponseEntity.status(HttpStatus.CREATED).body(mosqueId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mosque> getMosqueById(@PathVariable Long id) {
        return ResponseEntity.ok(mosqueService.getMosqueDetails(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMosque(@PathVariable Long id) {
        mosqueService.deleteMosque(id);
        return ResponseEntity.noContent().build();
    }


}
