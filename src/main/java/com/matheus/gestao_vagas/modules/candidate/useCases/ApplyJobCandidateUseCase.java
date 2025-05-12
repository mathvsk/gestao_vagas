package com.matheus.gestao_vagas.modules.candidate.useCases;

import com.matheus.gestao_vagas.exceptions.JobNotFoundException;
import com.matheus.gestao_vagas.exceptions.UserNotFoundException;
import com.matheus.gestao_vagas.modules.candidate.ApplyJobEntity;
import com.matheus.gestao_vagas.modules.candidate.ApplyJobRepository;
import com.matheus.gestao_vagas.modules.candidate.CandidateRepository;
import com.matheus.gestao_vagas.modules.jobs.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID candidateId,UUID jobId) {
        this.candidateRepository.findById(candidateId).orElseThrow(UserNotFoundException::new);

        this.jobRepository.findById(jobId).orElseThrow(JobNotFoundException::new);

        var applyJob = ApplyJobEntity.builder()
                .candidateId(candidateId)
                .jobId(jobId)
                .build();

        return this.applyJobRepository.save(applyJob);
    }
}
