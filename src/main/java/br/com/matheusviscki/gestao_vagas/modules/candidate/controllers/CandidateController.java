package br.com.matheusviscki.gestao_vagas.modules.candidate.controllers;

import br.com.matheusviscki.gestao_vagas.modules.candidate.CandidateEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @PostMapping("/")
    public String create(@RequestBody CandidateEntity candidateEntity) {
        System.out.println(candidateEntity);
        System.out.println(candidateEntity.getName());

        return null;
    }
}
