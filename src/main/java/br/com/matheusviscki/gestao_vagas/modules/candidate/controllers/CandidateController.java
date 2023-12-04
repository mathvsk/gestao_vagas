package br.com.matheusviscki.gestao_vagas.modules.candidate.controllers;

import br.com.matheusviscki.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.matheusviscki.gestao_vagas.modules.candidate.services.CreateCandidateService;
import br.com.matheusviscki.gestao_vagas.modules.candidate.services.ProfileCandidateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CreateCandidateService createCandidateService;
    @PostMapping("/")
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
}
