package org.example.deal.service;

import org.example.deal.dto.SetMainBorrowerDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.entity.SetMainBorrowerMessage;
import org.example.deal.quartz.ContractorServiceClient;
import org.example.deal.repository.ContractorRepository;
import org.example.deal.repository.SetMainBorrowerMessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class SetMainBorrowerServiceTest {

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
    private SetMainBorrowerService setMainBorrowerService;

    @MockBean
    private ContractorServiceClient contractorServiceClient;

    @Autowired
    private SetMainBorrowerMessageRepository setMainBorrowerMessageRepository;

    @Autowired
    private ContractorRepository contractorRepository;

    @Test
    @Transactional
    @Rollback
    public void setMainBorrowerTest() {
        Contractor contractor = contractorRepository
                .findByIdAndIsActiveTrue(UUID.fromString("01ebc64d-f477-41e7-a300-53871b4f2ada")).get();
        setMainBorrowerService.setMainBorrower(contractor, true);

        List<SetMainBorrowerMessage> messages = setMainBorrowerMessageRepository.findAll();
        Assertions.assertEquals(1, messages.size());
        Assertions.assertEquals(contractor.getContractorId(), messages.get(0).getContractorId());
    }

    @Test
    @Transactional
    @Rollback
    public void sendMessagesOkSituationTest() {
        Mockito.when(contractorServiceClient.setMainBorrower(any(SetMainBorrowerDTO.class)))
                .thenReturn(ResponseEntity.ok().build());

        SetMainBorrowerMessage message = SetMainBorrowerMessage.builder()
                .contractorId("c_id")
                .isActiveMainBorrower(true)
                .isSuccess(false)
                .build();
        setMainBorrowerMessageRepository.saveAndFlush(message);

        setMainBorrowerService.sendMessages();

        List<SetMainBorrowerMessage> messages = setMainBorrowerMessageRepository.findAll();
        Assertions.assertEquals(1, messages.size());
        Assertions.assertEquals(true, messages.get(0).getIsSuccess());
        verify(contractorServiceClient, times(1)).setMainBorrower(any(SetMainBorrowerDTO.class));
    }

    @Test
    @Transactional
    @Rollback
    public void sendMessagesBadSituationTest() {
        Mockito.when(contractorServiceClient.setMainBorrower(any(SetMainBorrowerDTO.class)))
                .thenReturn(ResponseEntity.notFound().build())
                .thenReturn(ResponseEntity.notFound().build())
                .thenReturn(ResponseEntity.ok().build());

        SetMainBorrowerMessage message = SetMainBorrowerMessage.builder()
                .contractorId("c_id")
                .isActiveMainBorrower(true)
                .isSuccess(false)
                .build();
        setMainBorrowerMessageRepository.saveAndFlush(message);

        setMainBorrowerService.sendMessages();
        setMainBorrowerService.sendMessages();
        setMainBorrowerService.sendMessages();

        List<SetMainBorrowerMessage> messages = setMainBorrowerMessageRepository.findAll();
        Assertions.assertEquals(1, messages.size());
        Assertions.assertEquals(true, messages.get(0).getIsSuccess());
        verify(contractorServiceClient, times(3)).setMainBorrower(any(SetMainBorrowerDTO.class));
    }

}
