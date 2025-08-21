package com.hatemkhabir.mosque_dashboards.controller;


import com.hatemkhabir.mosque_dashboards.model.Mosque;
import com.hatemkhabir.mosque_dashboards.service.MosqueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/superadmin")
@Slf4j
@RequiredArgsConstructor
public class SuperAdminRestController {

        private final MosqueService mosqueService;


        @PostMapping("/mosques/{id}/verify")
    public ResponseEntity<?> verifyMosque(@PathVariable Long mosqueId){
            return ResponseEntity.ok(mosqueService.verifyMosque(mosqueId));
        }

        @GetMapping
    public ResponseEntity<List<Mosque>> getAllMosques(){
            return ResponseEntity.status(HttpStatus.FOUND).body(mosqueService.listMosques(null,null));
        }


}
