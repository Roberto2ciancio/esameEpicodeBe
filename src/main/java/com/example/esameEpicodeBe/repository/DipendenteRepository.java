package com.example.esameEpicodeBe.repository;

import com.example.esameEpicodeBe.model.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DipendenteRepository extends JpaRepository<Dipendente, Long> {
    Optional<Dipendente> findByUsername(String username);
    Optional<Dipendente> findByEmail(String email);
    List<Dipendente> findByUsernameContaining(String username);
    List<Dipendente> findByEmailContaining(String email);
    List<Dipendente> findByUsernameContainingOrEmailContaining(String username, String email);
}