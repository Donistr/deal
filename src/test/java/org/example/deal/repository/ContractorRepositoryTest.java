package org.example.deal.repository;

import org.example.deal.entity.Contractor;
import org.example.deal.entity.help.DealStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class ContractorRepositoryTest {

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
    private ContractorRepository contractorRepository;

    @Test
    public void findByIdAndIsActiveTrueTest() {
        UUID id = UUID.fromString("01ebc64d-f477-41e7-a300-53871b4f2ada");
        Optional<Contractor> findOptional = contractorRepository
                .findByIdAndIsActiveTrue(id);

        Assertions.assertTrue(findOptional.isPresent());
        Contractor find = findOptional.get();
        Assertions.assertEquals(id, find.getId());
        Assertions.assertTrue(find.getIsActive());
    }

    @Test
    public void countAllDealsWithStatusWhereContractorMainBorrowerTest() {
        UUID id = UUID.fromString("01ebc64d-f477-41e7-a300-53871b4f2ada");
        Assertions.assertEquals(1, contractorRepository.countAllDealsWithStatusWhereContractorMainBorrower(id, DealStatusEnum.ACTIVE));
    }

}
