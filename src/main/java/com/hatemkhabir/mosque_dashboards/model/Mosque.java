package com.hatemkhabir.mosque_dashboards.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Mosque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please specify Mosque Country")
    @Column(nullable = false)
    private String country;

    @NotBlank(message = "Please Specify Mosque City")
    @Column(nullable = false)
    private String city;

    private String address;

    private Boolean verified=false;

    @NotBlank(message = "Please Specify Mosque Name")
    @Column(nullable = false)
    private String mosqueName;

    @OneToOne
    @JoinColumn(name = "admin_id",nullable = false)
    private MosqueAdmin mosqueAdmin;

    @OneToMany(mappedBy = "mosque",cascade = CascadeType.ALL)
    private List<Khotba> mosqueKhotbas;


}
