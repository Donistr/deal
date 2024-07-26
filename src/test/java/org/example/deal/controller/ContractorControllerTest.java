package org.example.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.deal.dto.ContractorCreateOrUpdateDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.repository.ContractorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ContractorControllerTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("DB_URL", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("DB_USERNAME", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("DB_PASSWORD", POSTGRE_SQL_CONTAINER::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ContractorRepository contractorRepository;

    @Test
    @Transactional
    @Rollback
    public void createContractorTest() throws Exception {
        ContractorCreateOrUpdateDTO request = ContractorCreateOrUpdateDTO.builder()
                .contractorId("asd123")
                .dealId(UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e"))
                .name("name123")
                .inn("inn123")
                .main(false)
                .build();

        mockMvc.perform(put("http://localhost:8081/deal-contractor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.contractor_id").value(request.getContractorId()))
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.inn").value(request.getInn()))
                .andExpect(jsonPath("$.main").value(request.getMain()))
                .andExpect(jsonPath("$.roles").isArray());
    }

    @Test
    @Transactional
    @Rollback
    public void changeContractorTest() throws Exception {
        UUID id = UUID.fromString("01ebc64d-f477-41e7-a300-53871b4f2ada");
        ContractorCreateOrUpdateDTO request = ContractorCreateOrUpdateDTO.builder()
                .id(id)
                .dealId(UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e"))
                .contractorId("asd123")
                .name("name123")
                .inn("inn123")
                .main(false)
                .build();

        mockMvc.perform(put("http://localhost:8081/deal-contractor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.contractor_id").value(request.getContractorId()))
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.inn").value(request.getInn()))
                .andExpect(jsonPath("$.main").value(request.getMain()))
                .andExpect(jsonPath("$.roles").isArray());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteContractorTest() throws Exception {
        mockMvc.perform(delete("http://localhost:8081/deal-contractor/delete/01ebc64d-f477-41e7-a300-53871b4f2ada"))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Contractor> find = contractorRepository.findById(UUID.fromString("01ebc64d-f477-41e7-a300-53871b4f2ada"));
        Assertions.assertTrue(find.isPresent());
        Assertions.assertFalse(find.get().getIsActive());
    }

}
