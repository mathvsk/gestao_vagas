package br.com.matheusviscki.gestao_vagas.modules.company.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "job")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Vaga para design")
    private String description;

    @Schema(example = "GymPass, Plano de saúde")
    private String benefits;

    @Schema(example = "SENIOR")
    private String level;

    @ManyToOne()
    @JoinColumn(name="company_id", insertable = false, updatable = false)
    private CompanyEntity company;

    @Column(name = "company_id")
    private UUID companyId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
