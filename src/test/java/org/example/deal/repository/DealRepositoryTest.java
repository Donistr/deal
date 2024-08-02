package org.example.deal.repository;

import org.example.deal.entity.Deal;
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
class DealRepositoryTest {

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
    private DealRepository dealRepository;

    @Test
    public void findByIdAndIsActiveTrueTest() {
        UUID id = UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e");
        Optional<Deal> findOptional = dealRepository.findByIdAndIsActiveTrue(id);

        Assertions.assertTrue(findOptional.isPresent());
        Deal find = findOptional.get();
        Assertions.assertEquals(id, find.getId());
        Assertions.assertTrue(find.getIsActive());
    }

}
