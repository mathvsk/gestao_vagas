package br.com.matheusviscki.gestao_vagas.modules.candidate.services;

import br.com.matheusviscki.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.matheusviscki.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.matheusviscki.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID id) {
        var candidate = this.candidateRepository.findById(id).
                orElseThrow(() -> {
                    throw  new UsernameNotFoundException("Candidate not found");
                });

        var candidateDTO = ProfileCandidateResponseDTO.builder()
                .description(candidate.getDescription())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .name(candidate.getName())
                .id(candidate.getId())
                .build();

        return candidateDTO;
    }
}
