package com.matheus.gestao_vagas.modules.candidate.useCases;

import com.matheus.gestao_vagas.exceptions.UserFoundException;
import com.matheus.gestao_vagas.modules.candidate.CandidateEntity;
import com.matheus.gestao_vagas.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository
            .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
            .ifPresent((user) -> {
                throw new UserFoundException();
            });

        var passwordEncoder = this.passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(passwordEncoder);

        this.candidateRepository.save(candidateEntity);

        return candidateEntity;
    }
}
