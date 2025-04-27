package com.matheus.gestao_vagas.modules.candidate;

import lombok.Data;

// Cria getters e setters para todos os atributos
@Data
public class CandidateEntity {
    private String name;
    private String username;
    private String email;
    private String password;
    private String description;
    private String curriculum;
}
