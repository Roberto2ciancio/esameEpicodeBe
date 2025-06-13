package com.example.esameEpicodeBe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "viaggi")
@Data
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String destinazione;

    @NotNull
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @NotNull
    private StatoViaggio stato = StatoViaggio.IN_PROGRAMMA;

    public enum StatoViaggio {
        IN_PROGRAMMA, COMPLETATO
    }
}