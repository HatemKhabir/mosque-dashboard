package com.hatemkhabir.mosque_dashboards.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MosqueAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Email not valid")
    @NotBlank(message = "Email should not be empty")
    private String email;
    private String passwordHash;

    private String phoneNumber;

    @OneToOne(mappedBy = "mosqueAdmin")
    private Mosque mosque;
}
