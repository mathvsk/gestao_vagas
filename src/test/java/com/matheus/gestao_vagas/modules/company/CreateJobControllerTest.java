package com.matheus.gestao_vagas.modules.company;

import com.matheus.gestao_vagas.exceptions.CompanyNotFoundException;
import com.matheus.gestao_vagas.modules.jobs.dto.CreateJobDTO;
import com.matheus.gestao_vagas.modules.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // carrega o application-test.properties
public class CreateJobControllerTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    // roda o servidor e faz as requisições
    private MockMvc mockMvc;

    @Value("${jwt.key}")
    private String secretKey;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void ShouldBeAbleToCreateANewJob() throws Exception {

        var company = CompanyEntity.builder()
                .description("COMPANY_DESCRIPTION")
                .email("email@company.com")
                .password("1234567890")
                .username("COMPANY_USERNAME")
                .name("COMPANY_NAME").build();

        company = this.companyRepository.saveAndFlush(company);

        var createdJobDTO = CreateJobDTO.builder()
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION_TEST")
                .level("LEVEL_TEST")
                .build();

        var bodyContent = TestUtils.objectToJson(createdJobDTO);
        var roles = List.of("COMPANY");
        var token = TestUtils.generateToken(company.getId() , this.secretKey, roles);

        var result = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent)
                .header("Authorization", "Bearer " + token)
            ).andExpect(MockMvcResultMatchers.status().isOk());

        System.out.println(result);
    }

    @Test
    public void ShouldNotBeAbleToCreateANewJobIfCompanyNotFound() throws Exception{
        var createdJobDTO = CreateJobDTO.builder()
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION_TEST")
                .level("LEVEL_TEST")
                .build();

        var roles = List.of("COMPANY");
        var token = TestUtils.generateToken(UUID.randomUUID(), this.secretKey, roles);

        try {
            this.mockMvc.perform(
                MockMvcRequestBuilders.post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createdJobDTO))
                .header("Authorization", "Bearer " + token)
            );
        } catch(CompanyNotFoundException e){
            assertThat(e).isInstanceOf(CompanyNotFoundException.class);
        }
    }
}
