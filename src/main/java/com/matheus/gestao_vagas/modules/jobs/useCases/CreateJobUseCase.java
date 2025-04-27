package com.matheus.gestao_vagas.modules.jobs.useCases;

import com.matheus.gestao_vagas.modules.jobs.JobEntity;
import com.matheus.gestao_vagas.modules.jobs.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    public JobEntity execute(JobEntity jobEntity) {
        return this.jobRepository.save(jobEntity);
    }
}
