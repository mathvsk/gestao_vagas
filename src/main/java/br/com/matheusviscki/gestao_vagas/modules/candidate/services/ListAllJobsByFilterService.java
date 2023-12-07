package br.com.matheusviscki.gestao_vagas.modules.candidate.services;

import br.com.matheusviscki.gestao_vagas.modules.company.entities.JobEntity;
import br.com.matheusviscki.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsByFilterService {

    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(String filter) {
        return this.jobRepository.findByDescriptionContaining(filter);
    }
}
