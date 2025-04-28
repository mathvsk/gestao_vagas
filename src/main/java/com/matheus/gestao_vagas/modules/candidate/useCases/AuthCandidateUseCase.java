package com.matheus.gestao_vagas.modules.candidate.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.matheus.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import com.matheus.gestao_vagas.modules.candidate.CandidateRepository;
import com.matheus.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCandidateUseCase {
    @Value("${jwt.key.candidate}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateDTO) throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(authCandidateDTO.username())
            .orElseThrow(() -> new RuntimeException("username/password invalid"));

        var isPasswordValid = this.passwordEncoder.matches(authCandidateDTO.password(), candidate.getPassword());

        if (!isPasswordValid) {
            throw new AuthenticationException("username/password invalid");
        }

        // cria a assinatura do token
        // withIssuer define quem emitiu o token
        // withSubject define o Subject (campo sub no payload do JWT).
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresAt = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("javagas")
            .withSubject(candidate.getId().toString())
            .withExpiresAt(expiresAt)
            .withClaim("roles", List.of("candidate"))
            .sign(algorithm);

        return AuthCandidateResponseDTO.builder()
            .access_token(token)
            .expires_in(expiresAt.toEpochMilli())
            .build();
    }
}
