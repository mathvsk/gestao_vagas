package com.matheus.gestao_vagas.modules.company;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "company")
public class CompanyEntity {
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
    private String website;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private String description;
}
