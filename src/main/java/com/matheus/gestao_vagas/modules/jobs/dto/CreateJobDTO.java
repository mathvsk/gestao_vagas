package com.matheus.gestao_vagas.modules.jobs.dto;

import lombok.Data;

@Data
public class CreateJobDTO {
    private String description;
    private String benefits;
    private String level;
}
