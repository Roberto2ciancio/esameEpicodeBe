package com.example.esameEpicodeBe.exception;

import lombok.Getter;

@Getter
public class PrenotazioneException extends RuntimeException {
    public PrenotazioneException(String message) {
        super(message);
    }
}