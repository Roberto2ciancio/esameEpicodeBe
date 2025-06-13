package com.example.esameEpicodeBe.Dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DipendenteDto {
    @NotEmpty(message = "l'username non puo essere vuoto")
    private String username;
    @NotEmpty(message = "il cognome non puo essere vuoto")
    private String cognome;
    @Email(message = "inserire una email valida")
    private String email;
    private  int viaggiId;

}
