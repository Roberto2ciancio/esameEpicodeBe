package com.example.esameEpicodeBe.Dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class PrenotazioneRequestDto {
    @NotNull
    private Long viaggioId;
    
    @NotNull
    private Long dipendenteId;
    
    private String note;
}