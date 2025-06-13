package com.example.esameEpicodeBe.repository;

import com.example.esameEpicodeBe.model.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ViaggiRepository extends JpaRepository<Viaggio, Long> {
    // Query personalizzate utili:
    List<Viaggio> findByStato(Viaggio.StatoViaggio stato);
    List<Viaggio> findByData(LocalDate data);
}