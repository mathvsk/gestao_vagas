package com.matheus.gestao_vagas.modules.jobs.controllers;

import com.matheus.gestao_vagas.modules.jobs.JobEntity;
import com.matheus.gestao_vagas.modules.jobs.dto.CreateJobDTO;
import com.matheus.gestao_vagas.modules.jobs.useCases.CreateJobUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(name = "Vagas", description = "rotas das vagas")
    @Operation(summary = "Cadastro de vaga", description = "Essa função é responsável por cadastrar as vagas dentro da empresa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = JobEntity.class))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
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
