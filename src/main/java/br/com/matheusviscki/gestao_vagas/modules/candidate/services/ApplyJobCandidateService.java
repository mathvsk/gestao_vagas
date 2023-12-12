package br.com.matheusviscki.gestao_vagas.modules.candidate.services;

import br.com.matheusviscki.gestao_vagas.exceptions.JobNotFoundException;
import br.com.matheusviscki.gestao_vagas.exceptions.UserNotFoundException;
import br.com.matheusviscki.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.matheusviscki.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateService {

    @Autowired
    private CandidateRepository candidateEntity;

    @Autowired
    private JobRepository jobEntity;

    public void execute(UUID idCandidate, UUID idJob){
        this.candidateEntity.findById(idCandidate).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        this.jobEntity.findById(idJob).orElseThrow(() -> {
            throw new JobNotFoundException();
        });
    }
}
