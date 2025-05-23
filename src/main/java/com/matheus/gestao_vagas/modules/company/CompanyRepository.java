package com.matheus.gestao_vagas.modules.company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    Optional<CompanyRepository> findByUsernameOrEmail(String username, String email);
    Optional<CompanyEntity> findByUsername(String username);
}
