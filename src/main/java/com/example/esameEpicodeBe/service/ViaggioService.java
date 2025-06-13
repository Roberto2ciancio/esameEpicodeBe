package com.example.esameEpicodeBe.service;

import com.example.esameEpicodeBe.model.Viaggio;
import com.example.esameEpicodeBe.repository.ViaggiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViaggioService {
    private final ViaggiRepository viaggiRepository;

    public List<Viaggio> findAll() {
        return viaggiRepository.findAll();
    }

    public Viaggio findById(Long id) {
        return viaggiRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Viaggio non trovato con id: " + id));
    }

    @Transactional
    public Viaggio save(Viaggio viaggio) {
        // Qui puoi aggiungere eventuali validazioni o logica di business
        return viaggiRepository.save(viaggio);
    }

    @Transactional
    public Viaggio updateStato(Long id, Viaggio.StatoViaggio stato) {
        Viaggio viaggio = findById(id);
        viaggio.setStato(stato);
        return viaggiRepository.save(viaggio);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!viaggiRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Viaggio non trovato con id: " + id);
        }
        viaggiRepository.deleteById(id);
    }
}