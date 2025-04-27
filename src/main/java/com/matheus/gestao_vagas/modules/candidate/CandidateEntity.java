package com.matheus.gestao_vagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

// Cria getters e setters para todos os atributos
@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Pattern(regexp = "\\S+", message = "Username should not be empty and should not contain only spaces")
    private String username;

    @Email(message = "Email should be valid")
    private String email;

    @Length(min = 10, max = 100, message = "Password should be between 10 and 100 characters")
    private String password;
    private String description;
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
