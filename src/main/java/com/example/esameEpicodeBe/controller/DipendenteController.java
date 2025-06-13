package com.example.esameEpicodeBe.controller;

import com.example.esameEpicodeBe.controller.ApiResponse;
import com.example.esameEpicodeBe.model.Dipendente;
import com.example.esameEpicodeBe.service.DipendenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dipendenti")
@RequiredArgsConstructor
@CrossOrigin
public class DipendenteController {

    private final DipendenteService dipendenteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Dipendente>>> getAllDipendenti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok(ApiResponse.success(dipendenteService.getAllDipendenti(page, size, sortBy)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Dipendente>> getDipendente(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(dipendenteService.getDipendenteById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Dipendente>> createDipendente(@Valid @RequestBody Dipendente dipendente) {
        Dipendente newDipendente = dipendenteService.saveDipendente(dipendente);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(newDipendente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Dipendente>> updateDipendente(
            @PathVariable Long id,
            @Valid @RequestBody Dipendente dipendente) {
        Dipendente updatedDipendente = dipendenteService.updateDipendente(id, dipendente);
        return ResponseEntity.ok(ApiResponse.success(updatedDipendente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDipendente(@PathVariable Long id) {
        dipendenteService.deleteDipendente(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping(value = "/{id}/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadProfileImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = dipendenteService.uploadProfileImage(id, file);
            return ResponseEntity.ok(ApiResponse.success(imageUrl));
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Errore durante l'upload dell'immagine: " + e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Dipendente>>> searchDipendenti(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email) {
        List<Dipendente> results = dipendenteService.searchDipendenti(username, email);
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Dipendente>> patchDipendente(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        Dipendente patchedDipendente = dipendenteService.patchDipendente(id, updates);
        return ResponseEntity.ok(ApiResponse.success(patchedDipendente));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getDipendentiCount() {
        return ResponseEntity.ok(ApiResponse.success(dipendenteService.getDipendentiCount()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
    }
}


