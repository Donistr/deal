package org.example.deal.repository;

import org.example.deal.entity.SetMainBorrowerMessage;
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

import java.time.ZonedDateTime;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class SetMainBorrowerMessageRepositoryTest {

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
    private SetMainBorrowerMessageRepository setMainBorrowerMessageRepository;

    @Test
    @Transactional
    @Rollback
    public void findAllByIsSuccessFalseTest() {
        setMainBorrowerMessageRepository.saveAndFlush(SetMainBorrowerMessage.builder()
                .contractorId("123")
                .isActiveMainBorrower(true)
                .createDate(ZonedDateTime.now())
                .isSuccess(true)
                .build());
        setMainBorrowerMessageRepository.saveAndFlush(SetMainBorrowerMessage.builder()
                .contractorId("321")
                .isActiveMainBorrower(true)
                .createDate(ZonedDateTime.now())
                .isSuccess(false)
                .build());

        Assertions.assertEquals(1, setMainBorrowerMessageRepository.findAllByIsSuccessFalse().size());
    }

}
