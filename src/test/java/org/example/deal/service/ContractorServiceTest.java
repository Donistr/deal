package org.example.deal.service;

import org.example.deal.dto.ContractorCreateOrUpdateDTO;
import org.example.deal.dto.ContractorDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.exception.DealNotFoundException;
import org.example.deal.repository.ContractorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class ContractorServiceTest {

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
    private ContractorService contractorService;

    @Autowired
    private ContractorRepository contractorRepository;

    @MockBean
    private SetMainBorrowerService setMainBorrowerService;

    @Test
    @Transactional
    @Rollback
    public void createContractorTest() {
        ContractorCreateOrUpdateDTO request = ContractorCreateOrUpdateDTO.builder()
                .contractorId("asd123")
                .dealId(UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e"))
                .name("name123")
                .inn("inn123")
                .main(false)
                .build();

        ContractorDTO response = contractorService.createOrUpdate(request);
        Assertions.assertEquals(request.getContractorId(), response.getContractorId());
        Assertions.assertEquals(request.getName(), response.getName());
        Assertions.assertEquals(request.getInn(), response.getInn());
        Assertions.assertEquals(request.getMain(), response.getMain());
        Assertions.assertEquals(0, response.getRoles().size());

        Optional<Contractor> findOptional = contractorRepository.findById(response.getId());
        Assertions.assertTrue(findOptional.isPresent());
        Contractor find = findOptional.get();
        Assertions.assertEquals(response.getId(), find.getId());
        Assertions.assertEquals(request.getMain(), find.getMain());
        Assertions.assertEquals(response.getContractorId(), find.getContractorId());
        Assertions.assertEquals(response.getName(), find.getName());
        Assertions.assertEquals(response.getInn(), find.getInn());
        Assertions.assertEquals(response.getMain(), find.getMain());
        Assertions.assertEquals(request.getDealId(), find.getDeal().getId());
    }

    @Test
    @Transactional
    @Rollback
    public void createContractorDealNotFoundExceptionTest() {
        ContractorCreateOrUpdateDTO request = ContractorCreateOrUpdateDTO.builder()
                .contractorId("asd123")
                .name("name123")
                .inn("inn123")
                .main(false)
                .build();
        Assertions.assertThrows(DealNotFoundException.class, () -> contractorService.createOrUpdate(request));
        request.setDealId(UUID.fromString("e769b707-e162-4ae6-8555-c8a68006535e"));
        Assertions.assertThrows(DealNotFoundException.class, () -> contractorService.createOrUpdate(request));
    }

    @Test
    @Transactional
    @Rollback
    public void changeContractorTest() {
        UUID id = UUID.fromString("01ebc64d-f477-41e7-a300-53871b4f2ada");

        ContractorCreateOrUpdateDTO request = ContractorCreateOrUpdateDTO.builder()
                .id(id)
                .contractorId("asd123")
                .dealId(UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e"))
                .name("name123")
                .inn("inn123")
                .main(false)
                .build();

        ContractorDTO response = contractorService.createOrUpdate(request);
        Assertions.assertEquals(request.getId(), response.getId());
        Assertions.assertEquals(request.getContractorId(), response.getContractorId());
        Assertions.assertEquals(request.getName(), response.getName());
        Assertions.assertEquals(request.getInn(), response.getInn());
        Assertions.assertEquals(request.getMain(), response.getMain());

        Optional<Contractor> findOptional = contractorRepository.findByIdAndIsActiveTrue(id);
        Assertions.assertTrue(findOptional.isPresent());
        Contractor find = findOptional.get();

        Assertions.assertEquals(request.getId(), find.getId());
        Assertions.assertEquals(request.getContractorId(), find.getContractorId());
        Assertions.assertEquals(request.getName(), find.getName());
        Assertions.assertEquals(request.getInn(), find.getInn());
        Assertions.assertEquals(request.getMain(), find.getMain());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteContractorTest() {
        UUID id = UUID.fromString("01ebc64d-f477-41e7-a300-53871b4f2ada");

        Optional<Contractor> find = contractorRepository.findById(id);
        Assertions.assertTrue(find.isPresent());
        Assertions.assertTrue(find.get().getIsActive());

        contractorService.delete(id);

        find = contractorRepository.findById(id);
        Assertions.assertTrue(find.isPresent());
        Assertions.assertFalse(find.get().getIsActive());
        find.get().getRoles().forEach(role -> Assertions.assertFalse(role.getIsActive()));

        verify(setMainBorrowerService, times(1)).setMainBorrower(any(Contractor.class), any(Boolean.class));
    }

}
