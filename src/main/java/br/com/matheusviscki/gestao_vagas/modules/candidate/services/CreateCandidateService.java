package br.com.matheusviscki.gestao_vagas.modules.candidate.services;

import br.com.matheusviscki.gestao_vagas.exceptions.UserFoundException;
import br.com.matheusviscki.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.matheusviscki.gestao_vagas.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateService {
    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
            .ifPresent(candidate -> {
                throw new UserFoundException();
            });

        var password = this.passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);

        return  this.candidateRepository.save(candidateEntity);

    }
}
