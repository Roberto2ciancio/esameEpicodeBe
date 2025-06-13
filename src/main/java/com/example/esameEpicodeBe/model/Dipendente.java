package com.example.esameEpicodeBe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "dipendenti")
@Data
public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username è obbligatorio")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Nome è obbligatorio")
    private String nome;

    @NotBlank(message = "Cognome è obbligatorio")
    private String cognome;

    @Email(message = "Email non valida")
    @NotBlank(message = "Email è obbligatoria")
    @Column(unique = true)
    private String email;

    private String urlImmagineProfilo;
}