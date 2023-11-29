package br.com.matheusviscki.gestao_vagas.modules.candidate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
public class CandidateEntity {
    private UUID id;
    private String name;

    @NotBlank(message = "O campo [username] é obrigatório")
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço")
    private String username;

    @Email(message = "O campo [email] deve ser um email válido")
    private String email;

    @Length(min = 10, max = 100, message = "O campo [password] deve ter no mínimo 6 caracteres e máximo 100")
    private String password;
    private String description;
    private String curriculum;
}
