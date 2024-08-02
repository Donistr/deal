package org.example.deal.repository;

import org.example.deal.entity.ContractorRole;
import org.example.deal.entity.help.ContractorRoleEnum;
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

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class ContractorRoleRepositoryTest {

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
    private ContractorRoleRepository contractorRoleRepository;

    @Test
    public void findByIdAndIsActiveTrueTest() {
        Arrays.stream(ContractorRoleEnum.values()).forEach(role -> {
            Optional<ContractorRole> find = contractorRoleRepository.findByIdAndIsActiveTrue(ContractorRoleEnum.BORROWER);
            Assertions.assertTrue(find.isPresent());
            Assertions.assertTrue(find.get().isActive());
        });

    }

}
