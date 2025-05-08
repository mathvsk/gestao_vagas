package com.matheus.gestao_vagas.modules.candidate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, Long> {
}
