package org.example.deal.service;

import org.example.deal.dto.ContractorChangeRoleDTO;
import org.example.deal.dto.DealContractorRoleDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.entity.ContractorRole;
import org.example.deal.entity.DealContractorRole;
import org.example.deal.entity.help.ContractorRoleEnum;
import org.example.deal.entity.help.DealContractorRoleId;
import org.example.deal.repository.ContractorRepository;
import org.example.deal.repository.ContractorRoleRepository;
import org.example.deal.repository.DealContractorRoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class ContractorRoleServiceTest {

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
    private ContractorRoleService contractorRoleService;

    @Autowired
    private DealContractorRoleRepository dealContractorRoleRepository;

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private ContractorRoleRepository contractorRoleRepository;

    @Test
    @Transactional
    @Rollback
    public void createContractorRoleTest() {
        Contractor contractor = contractorRepository.findByIdAndIsActiveTrue(UUID.fromString("01ebc64d-f477-41e7-a300-53871b4f2ada")).get();
        ContractorRole contractorRole = contractorRoleRepository.findByIdAndIsActiveTrue(ContractorRoleEnum.DRAWER).get();
        DealContractorRoleId id = DealContractorRoleId.builder()
                .contractor(contractor)
                .contractorRole(contractorRole)
                .build();

        ContractorChangeRoleDTO request = ContractorChangeRoleDTO.builder()
                .contractorId(contractor.getId())
                .roleId(contractorRole.getId())
                .build();

        Assertions.assertTrue(dealContractorRoleRepository.findById(id).isEmpty());

        DealContractorRoleDTO response = contractorRoleService.createOrUpdate(request);
        Assertions.assertEquals(request.getContractorId(), response.getContractor().getId());
        Assertions.assertEquals(request.getRoleId(), response.getContractorRole().getId());

        Assertions.assertTrue(dealContractorRoleRepository.findById(id).isPresent());
    }

    @Test
    @Transactional
    @Rollback
    public void rewriteInactiveContractorRoleTest() {
        Contractor contractor = contractorRepository.findByIdAndIsActiveTrue(UUID.fromString("01ebc64d-f477-41e7-a300-53871b4f2ada")).get();
        ContractorRole contractorRole = contractorRoleRepository.findByIdAndIsActiveTrue(ContractorRoleEnum.WARRANTY).get();
        DealContractorRoleId id = DealContractorRoleId.builder()
                .contractor(contractor)
                .contractorRole(contractorRole)
                .build();

        Optional<DealContractorRole> find = dealContractorRoleRepository.findById(id);
        Assertions.assertTrue(find.isPresent());
        Assertions.assertFalse(find.get().getIsActive());

        ContractorChangeRoleDTO request = ContractorChangeRoleDTO.builder()
                .contractorId(contractor.getId())
                .roleId(contractorRole.getId())
                .build();

        DealContractorRoleDTO response = contractorRoleService.createOrUpdate(request);
        Assertions.assertEquals(request.getContractorId(), response.getContractor().getId());
        Assertions.assertEquals(request.getRoleId(), response.getContractorRole().getId());

        find = dealContractorRoleRepository.findById(id);
        Assertions.assertTrue(find.isPresent());
        Assertions.assertTrue(find.get().getIsActive());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteContractorRoleTest() {
        Contractor contractor = contractorRepository.findByIdAndIsActiveTrue(UUID.fromString("01ebc64d-f477-41e7-a300-53871b4f2ada")).get();
        ContractorRole contractorRole = contractorRoleRepository.findByIdAndIsActiveTrue(ContractorRoleEnum.BORROWER).get();
        DealContractorRoleId id = DealContractorRoleId.builder()
                .contractor(contractor)
                .contractorRole(contractorRole)
                .build();

        Optional<DealContractorRole> find = dealContractorRoleRepository.findById(id);
        Assertions.assertTrue(find.isPresent());
        Assertions.assertTrue(find.get().getIsActive());

        ContractorChangeRoleDTO request = ContractorChangeRoleDTO.builder()
                .contractorId(contractor.getId())
                .roleId(contractorRole.getId())
                .build();

        contractorRoleService.delete(request);

        find = dealContractorRoleRepository.findById(id);
        Assertions.assertTrue(find.isPresent());
        Assertions.assertFalse(find.get().getIsActive());
    }

}
