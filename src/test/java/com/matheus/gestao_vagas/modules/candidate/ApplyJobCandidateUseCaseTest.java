package com.matheus.gestao_vagas.modules.candidate;

import com.matheus.gestao_vagas.exceptions.JobNotFoundException;
import com.matheus.gestao_vagas.exceptions.UserNotFoundException;
import com.matheus.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import com.matheus.gestao_vagas.modules.jobs.JobRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    // Geralmente usa o InjectMocks pra classe que vai ser testada
    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    // Usa-se mock para informar as dependencias dentro da classe que vai ser testada
    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Test
    @DisplayName("Should not be able to apply for a job if candidate does not found")
    public void ShouldNotBeAbleToApplyJobWithNotFoundCandidate() {
        try {
            this.applyJobCandidateUseCase.execute(null, null);
        } catch (UserNotFoundException e) {
            Assertions.assertInstanceOf(UserNotFoundException.class, e);
        }
    }

    @Test
    public void ShouldNotBeAbleToApplyJobWithNotFoundJob() {
        var idCandidate = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try {
            this.applyJobCandidateUseCase.execute(idCandidate, null);
        }catch (JobNotFoundException e) {
            Assertions.assertInstanceOf(JobNotFoundException.class, e);
        }
    }
}
