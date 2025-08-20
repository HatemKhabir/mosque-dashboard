package com.hatemkhabir.mosque_dashboards.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String fileUrl;
    private String cloudinaryId;
    private String fileType;
    private Long fileSize;
    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "khotba_id")
    private Khotba khotba;

    @PreRemove
    public void deleteFromCloudinary(){

    }

}
