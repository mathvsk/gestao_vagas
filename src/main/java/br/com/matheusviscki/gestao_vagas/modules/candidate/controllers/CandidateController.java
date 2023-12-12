package br.com.matheusviscki.gestao_vagas.modules.candidate.controllers;

import br.com.matheusviscki.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.matheusviscki.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.matheusviscki.gestao_vagas.modules.candidate.services.ApplyJobCandidateService;
import br.com.matheusviscki.gestao_vagas.modules.candidate.services.CreateCandidateService;
import br.com.matheusviscki.gestao_vagas.modules.candidate.services.ListAllJobsByFilterService;
import br.com.matheusviscki.gestao_vagas.modules.candidate.services.ProfileCandidateService;
import br.com.matheusviscki.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Rotas do candidato")
public class CandidateController {

    @Autowired
    private CreateCandidateService createCandidateService;
    @PostMapping("/")
    @Operation(summary = "Cadastro de candidato", description = "Essa função é responsável por cadastrar um candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            var result = this.createCandidateService.execute(candidateEntity);

            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Autowired
    private ProfileCandidateService profileCandidateService;

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Essa funçao é responsável por buscar as informações do candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class) )
            }),
            @ApiResponse(responseCode = "400", description = "Candidato não encontrado")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request) {
        var candidateID = request.getAttribute("candidate_id");
        var convertedCandidateID = UUID.fromString(candidateID.toString());

        try {
            var profile = this.profileCandidateService.execute(convertedCandidateID);

            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Autowired
    private ListAllJobsByFilterService listAllJobsByFilterService;

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
        })
    })
    @Operation(summary = "Listar vagas por filtro", description = "Listar vagas por filtro")
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterService.execute(filter);
    }

    @Autowired
    private ApplyJobCandidateService applyJobCandidateService;

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Inscrição do candidato para uma vaga", description = "Essa função é responsável por realizar a inscrição do candidato em uma vaga.")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob) {
        var idCandidate = request.getAttribute("candidate_id");

        try {
            var result = this.applyJobCandidateService.execute(UUID.fromString(idCandidate.toString()), idJob);

            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
