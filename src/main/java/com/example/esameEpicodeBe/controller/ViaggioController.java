package com.example.esameEpicodeBe.controller;

import com.example.esameEpicodeBe.model.Viaggio;
import com.example.esameEpicodeBe.service.ViaggioService;
import com.example.esameEpicodeBe.controller.ApiResponse;
import com.example.esameEpicodeBe.model.Viaggio;
import com.example.esameEpicodeBe.service.ViaggioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viaggi")
@RequiredArgsConstructor
public class ViaggioController {

    private final ViaggioService viaggioService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Viaggio>>> getAllViaggi() {
        return ResponseEntity.ok(ApiResponse.success(viaggioService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Viaggio>> getViaggio(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(viaggioService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Viaggio>> createViaggio(@Valid @RequestBody Viaggio viaggio) {
        return ResponseEntity.ok(ApiResponse.success(viaggioService.save(viaggio)));
    }

    @PutMapping("/{id}/stato")
    public ResponseEntity<ApiResponse<Viaggio>> updateStatoViaggio(
            @PathVariable Long id, 
            @RequestParam Viaggio.StatoViaggio stato) {
        return ResponseEntity.ok(ApiResponse.success(viaggioService.updateStato(id, stato)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteViaggio(@PathVariable Long id) {
        viaggioService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}