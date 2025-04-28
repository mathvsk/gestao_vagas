package com.matheus.gestao_vagas.modules.company.useCases;

import com.matheus.gestao_vagas.exceptions.UserFoundException;
import com.matheus.gestao_vagas.modules.company.CompanyEntity;
import com.matheus.gestao_vagas.modules.company.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity company) {
        this.companyRepository
            .findByUsernameOrEmail(company.getUsername(), company.getEmail())
            .ifPresent((user) -> {
                throw new UserFoundException();
            });

        var passwordEncoder = this.passwordEncoder.encode(company.getPassword());
        company.setPassword(passwordEncoder);

        this.companyRepository.save(company);

        return company;
    }
}
