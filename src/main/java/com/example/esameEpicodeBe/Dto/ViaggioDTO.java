package com.example.esameEpicodeBe.Dto;

import com.example.esameEpicodeBe.model.Viaggio.StatoViaggio;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ViaggioDTO {
    private Long id;
    
    @NotNull(message = "La destinazione è obbligatoria")
    private String destinazione;
    
    @NotNull(message = "La data è obbligatoria")
    private LocalDate data;
    
    @NotNull(message = "Lo stato è obbligatorio")
    private StatoViaggio stato;
}