package com.matheus.gestao_vagas.modules.company.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.matheus.gestao_vagas.modules.company.CompanyRepository;
import com.matheus.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import com.matheus.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCompanyUseCase {

    @Value("${jwt.key}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername())
            .orElseThrow(() -> new RuntimeException("username/password invalid"));

        var isPasswordValid = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if (!isPasswordValid) {
            throw new AuthenticationException("username/password invalid");
        }

        // cria a assinatura do token
        // withIssuer define quem emitiu o token
        // withSubject define o Subject (campo sub no payload do JWT).
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresAt = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("javagas")
            .withSubject(company.getId().toString())
            .withExpiresAt(expiresAt)
            .withClaim("roles", List.of("COMPANY"))
            .sign(algorithm);

        return AuthCompanyResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresAt.toEpochMilli())
                .build();
    }
}
