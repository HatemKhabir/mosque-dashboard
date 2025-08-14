package com.hatemkhabir.mosque_dashboards.model;

import com.hatemkhabir.mosque_dashboards.common.KhotbaLanguage;
import com.hatemkhabir.mosque_dashboards.common.KhotbaType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Khotba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mosque_id",nullable = false)
    private Mosque mosque;

    @NotBlank(message = "Please Specify Khotba Title/Topic")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KhotbaLanguage officialLanguage;

    private String content;

    @NonNull
    private String pdfUrl;
    private String audioUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KhotbaType khotbaType;

    @PastOrPresent(message = "Date cannot be in the future")
    @Column(nullable = false)
    private LocalDate date;

    @PrePersist
    public void prePersist(){
        this.date=LocalDate.now();
    }

}
