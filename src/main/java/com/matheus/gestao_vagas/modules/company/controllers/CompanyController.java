package com.matheus.gestao_vagas.modules.company.controllers;

import com.matheus.gestao_vagas.modules.company.CompanyEntity;
import com.matheus.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity company) {
        try {
            var result = this.createCompanyUseCase.execute(company);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
