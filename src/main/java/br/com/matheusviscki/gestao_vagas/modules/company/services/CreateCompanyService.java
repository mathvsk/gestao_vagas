package br.com.matheusviscki.gestao_vagas.modules.company.services;

import br.com.matheusviscki.gestao_vagas.exceptions.UserFoundException;
import br.com.matheusviscki.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.matheusviscki.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
            .ifPresent(company -> {
                throw new UserFoundException();
            });

        var password = companyEntity.getPassword();
        var encodedPassword = this.passwordEncoder.encode(password);
        companyEntity.setPassword(encodedPassword);

        return this.companyRepository.save(companyEntity);
    }
}
