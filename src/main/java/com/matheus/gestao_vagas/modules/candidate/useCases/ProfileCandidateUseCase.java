package com.matheus.gestao_vagas.modules.candidate.useCases;

import com.matheus.gestao_vagas.modules.candidate.CandidateRepository;
import com.matheus.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID id) {
        var candidate = this.candidateRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return ProfileCandidateResponseDTO.builder()
                .email(candidate.getEmail())
                .name(candidate.getName())
                .description(candidate.getDescription())
                .username(candidate.getUsername())
                .id(candidate.getId())
                .build();
    }
}
