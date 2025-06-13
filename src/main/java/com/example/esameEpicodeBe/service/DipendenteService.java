package com.example.esameEpicodeBe.service;


import com.cloudinary.Cloudinary;
import com.example.esameEpicodeBe.exception.ResourceNotFoundException;
import com.example.esameEpicodeBe.model.Dipendente;
import com.example.esameEpicodeBe.repository.DipendenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DipendenteService {

    private final DipendenteRepository dipendenteRepository;
    private final Cloudinary cloudinary;

    public List<Dipendente> getAllDipendenti(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        return dipendenteRepository.findAll(pageRequest).getContent();
    }

    public Dipendente getDipendenteById(Long id) {
        return dipendenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato con id: " + id));
    }

    @Transactional
    public Dipendente saveDipendente(Dipendente dipendente) {
        // Verifica se username o email sono già in uso
        if (dipendenteRepository.findByUsername(dipendente.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username già in uso");
        }
        if (dipendenteRepository.findByEmail(dipendente.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email già in uso");
        }
        return dipendenteRepository.save(dipendente);
    }

    @Transactional
    public Dipendente updateDipendente(Long id, Dipendente dipendente) {
        Dipendente existingDipendente = getDipendenteById(id);
        
        // Verifica se il nuovo username è già in uso da un altro dipendente
        dipendenteRepository.findByUsername(dipendente.getUsername())
                .ifPresent(d -> {
                    if (!d.getId().equals(id)) {
                        throw new IllegalArgumentException("Username già in uso");
                    }
                });

        // Verifica se la nuova email è già in uso da un altro dipendente
        dipendenteRepository.findByEmail(dipendente.getEmail())
                .ifPresent(d -> {
                    if (!d.getId().equals(id)) {
                        throw new IllegalArgumentException("Email già in uso");
                    }
                });

        dipendente.setId(id);
        dipendente.setUrlImmagineProfilo(existingDipendente.getUrlImmagineProfilo());
        return dipendenteRepository.save(dipendente);
    }

    @Transactional
    public void deleteDipendente(Long id) {
        if (!dipendenteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Dipendente non trovato con id: " + id);
        }
        dipendenteRepository.deleteById(id);
    }

    @Transactional
    public String uploadProfileImage(Long id, MultipartFile file) throws IOException {
        Dipendente dipendente = getDipendenteById(id);
        
        // Upload dell'immagine su Cloudinary
        Map<String, String> options = Map.of(
            "resource_type", "auto",
            "folder", "profile-images"
        );
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        String imageUrl = (String) uploadResult.get("secure_url");
        
        // Aggiornamento URL immagine profilo
        dipendente.setUrlImmagineProfilo(imageUrl);
        dipendenteRepository.save(dipendente);
        
        return imageUrl;
    }

    public List<Dipendente> searchDipendenti(String username, String email) {
        if (username != null && email != null) {
            return dipendenteRepository.findByUsernameContainingOrEmailContaining(username, email);
        } else if (username != null) {
            return dipendenteRepository.findByUsernameContaining(username);
        } else if (email != null) {
            return dipendenteRepository.findByEmailContaining(email);
        }
        return dipendenteRepository.findAll();
    }

    @Transactional
    public Dipendente patchDipendente(Long id, Map<String, Object> updates) {
        Dipendente dipendente = getDipendenteById(id);
        
        updates.forEach((key, value) -> {
            switch (key) {
                case "username":
                    String newUsername = (String) value;
                    dipendenteRepository.findByUsername(newUsername)
                            .ifPresent(d -> {
                                if (!d.getId().equals(id)) {
                                    throw new IllegalArgumentException("Username già in uso");
                                }
                            });
                    dipendente.setUsername(newUsername);
                    break;
                case "email":
                    String newEmail = (String) value;
                    dipendenteRepository.findByEmail(newEmail)
                            .ifPresent(d -> {
                                if (!d.getId().equals(id)) {
                                    throw new IllegalArgumentException("Email già in uso");
                                }
                            });
                    dipendente.setEmail(newEmail);
                    break;
                case "nome":
                    dipendente.setNome((String) value);
                    break;
                case "cognome":
                    dipendente.setCognome((String) value);
                    break;
            }
        });
        
        return dipendenteRepository.save(dipendente);
    }

    public Long getDipendentiCount() {
        return dipendenteRepository.count();
    }
}