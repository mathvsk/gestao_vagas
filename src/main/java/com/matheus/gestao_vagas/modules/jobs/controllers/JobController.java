package com.matheus.gestao_vagas.modules.jobs.controllers;

import com.matheus.gestao_vagas.modules.jobs.JobEntity;
import com.matheus.gestao_vagas.modules.jobs.dto.CreateJobDTO;
import com.matheus.gestao_vagas.modules.jobs.useCases.CreateJobUseCase;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public ResponseEntity<JobEntity> create(@RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        var jobEntity = JobEntity.builder()
            .description(createJobDTO.getDescription())
            .level(createJobDTO.getLevel())
            .benefits(createJobDTO.getBenefits())
            .companyId(UUID.fromString(companyId.toString()))
            .build();

        var result = this.createJobUseCase.execute(jobEntity);

        return ResponseEntity.ok(result);
    }
}
