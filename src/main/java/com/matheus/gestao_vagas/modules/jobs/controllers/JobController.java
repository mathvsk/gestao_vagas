package com.matheus.gestao_vagas.modules.jobs.controllers;

import com.matheus.gestao_vagas.modules.jobs.JobEntity;
import com.matheus.gestao_vagas.modules.jobs.useCases.CreateJobUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public ResponseEntity<JobEntity> create(@RequestBody JobEntity job) {
        var result = this.createJobUseCase.execute(job);

        return ResponseEntity.ok(result);
    }
}
