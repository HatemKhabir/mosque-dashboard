package com.hatemkhabir.mosque_dashboards.model;

import com.hatemkhabir.mosque_dashboards.common.KhotbaLanguage;
import com.hatemkhabir.mosque_dashboards.common.KhotbaType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

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

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "khotba",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<FileResource> khotbaFiles;

    private boolean approved=true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KhotbaType khotbaType;

    @PastOrPresent(message = "Date cannot be in the future")
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate date;

    @PrePersist
    public void prePersist(){
        this.date=LocalDate.now();
    }

}
