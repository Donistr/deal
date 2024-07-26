package org.example.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.deal.dto.ContractorChangeRoleDTO;
import org.example.deal.entity.DealContractorRole;
import org.example.deal.entity.help.ContractorRoleEnum;
import org.example.deal.entity.help.DealContractorRoleId;
import org.example.deal.repository.ContractorRepository;
import org.example.deal.repository.ContractorRoleRepository;
import org.example.deal.repository.DealContractorRoleRepository;
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
public class ContractorRoleControllerTest {

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
    private DealContractorRoleRepository dealContractorRoleRepository;

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private ContractorRoleRepository contractorRoleRepository;

    @Test
    @Transactional
    @Rollback
    public void createContractorRoleTest() throws Exception {
        ContractorChangeRoleDTO request = ContractorChangeRoleDTO.builder()
                .contractorId(UUID.fromString("01ebc64d-f477-41e7-a300-53871b4f2ada"))
                .roleId(ContractorRoleEnum.BORROWER)
                .build();

        mockMvc.perform(put("http://localhost:8081/contractor-to-role/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractor").exists())
                .andExpect(jsonPath("$.role").exists())
                .andExpect(jsonPath("$.contractor.id").value(request.getContractorId().toString()))
                .andExpect(jsonPath("$.role.id").value(request.getRoleId().getValue()));
    }

    @Test
    @Transactional
    @Rollback
    public void changeContractorRoleTest() throws Exception {
        ContractorChangeRoleDTO request = ContractorChangeRoleDTO.builder()
                .contractorId(UUID.fromString("01ebc64d-f477-41e7-a300-53871b4f2ada"))
                .roleId(ContractorRoleEnum.WARRANTY)
                .build();

        mockMvc.perform(put("http://localhost:8081/contractor-to-role/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractor").exists())
                .andExpect(jsonPath("$.role").exists())
                .andExpect(jsonPath("$.contractor.id").value(request.getContractorId().toString()))
                .andExpect(jsonPath("$.role.id").value(request.getRoleId().getValue()));
    }

    @Test
    @Transactional
    @Rollback
    public void deleteContractorRoleTest() throws Exception {
        ContractorChangeRoleDTO request = ContractorChangeRoleDTO.builder()
                .contractorId(UUID.fromString("01ebc64d-f477-41e7-a300-53871b4f2ada"))
                .roleId(ContractorRoleEnum.BORROWER)
                .build();

        mockMvc.perform(delete("http://localhost:8081/contractor-to-role/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<DealContractorRole> find = dealContractorRoleRepository.findById(DealContractorRoleId.builder()
                .contractor(contractorRepository.findByIdAndIsActiveTrue(UUID.fromString("01ebc64d-f477-41e7-a300-53871b4f2ada")).get())
                .contractorRole(contractorRoleRepository.findByIdAndIsActiveTrue(ContractorRoleEnum.BORROWER).get())
                .build());
        Assertions.assertTrue(find.isPresent());
        Assertions.assertFalse(find.get().getIsActive());
    }

}
