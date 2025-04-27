package com.matheus.gestao_vagas.modules.candidate.useCases;

import com.matheus.gestao_vagas.exceptions.UserFoundException;
import com.matheus.gestao_vagas.modules.candidate.CandidateEntity;
import com.matheus.gestao_vagas.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository
            .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
            .ifPresent((user) -> {
                throw new UserFoundException();
            });

        this.candidateRepository.save(candidateEntity);

        return candidateEntity;
    }
}
