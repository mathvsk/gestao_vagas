package br.com.matheusviscki.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {
    @Schema(example = "Desenvolvedor Java")
    private String description;
    @Schema(example = "matheus@email.com")
    private String username;
    @Schema(example = "vskmatheus")
    private String email;
    private UUID id;
    @Schema(example = "Matheus Viscki")
    private String name;
}
