package com.example.esameEpicodeBe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "prenotazioni")
@Data
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "viaggio_id", nullable = false)
    @NotNull
    private Viaggio viaggio;

    @ManyToOne
    @JoinColumn(name = "dipendente_id", nullable = false)
    @NotNull
    private Dipendente dipendente;

    @NotNull
    private LocalDate dataRichiesta = LocalDate.now();

    private String note;
}