package com.matheus.gestao_vagas.modules.company.useCases;

import com.matheus.gestao_vagas.exceptions.UserFoundException;
import com.matheus.gestao_vagas.modules.company.CompanyEntity;
import com.matheus.gestao_vagas.modules.company.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity company) {
        this.companyRepository
            .findByUsernameOrEmail(company.getUsername(), company.getEmail())
            .ifPresent((user) -> {
                throw new UserFoundException();
            });

        this.companyRepository.save(company);

        return company;
    }
}
