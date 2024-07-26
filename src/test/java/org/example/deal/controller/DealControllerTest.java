package org.example.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.deal.dto.DealChangeStatusDTO;
import org.example.deal.dto.DealCreateOrUpdateDTO;
import org.example.deal.dto.DealSearchRequestDTO;
import org.example.deal.entity.Deal;
import org.example.deal.entity.help.DealStatusEnum;
import org.example.deal.entity.help.DealTypeEnum;
import org.example.deal.repository.DealRepository;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class DealControllerTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

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
    private DealRepository dealRepository;

    @Test
    @Transactional
    @Rollback
    public void createDealTest() throws Exception {
        DealCreateOrUpdateDTO request = DealCreateOrUpdateDTO.builder()
                .description("description_test")
                .agreementNumber("agreement_number_test")
                .agreementDate(LocalDateTime.now())
                .agreementStartDate(LocalDateTime.now())
                .availabilityDate(LocalDateTime.now())
                .typeId(DealTypeEnum.CREDIT)
                .sum(123.4)
                .closeDate(LocalDateTime.now())
                .build();

        mockMvc.perform(put("http://localhost:8081/deal/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value(request.getDescription()))
                .andExpect(jsonPath("$.agreement_number").value(request.getAgreementNumber()))
                .andExpect(jsonPath("$.type.id").value(request.getTypeId().getValue()))
                .andExpect(jsonPath("$.status.id").value(DealStatusEnum.DRAFT.getValue()))
                .andExpect(jsonPath("$.sum").value(request.getSum()))
                .andExpect(jsonPath("$.contractors").isArray());
    }

    @Test
    @Transactional
    @Rollback
    public void changeDealTest() throws Exception {
        Deal deal = dealRepository.findById(UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e")).get();

        DealCreateOrUpdateDTO request = DealCreateOrUpdateDTO.builder()
                .id(deal.getId())
                .description(deal.getDescription())
                .agreementNumber(deal.getAgreementNumber())
                .agreementDate(deal.getAgreementDate())
                .agreementStartDate(deal.getAgreementStartDate())
                .availabilityDate(deal.getAvailabilityDate())
                .typeId(deal.getType().getId())
                .sum(321.0)
                .closeDate(deal.getCloseDate())
                .build();

        mockMvc.perform(put("http://localhost:8081/deal/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(request.getId().toString()))
                .andExpect(jsonPath("$.description").value(request.getDescription()))
                .andExpect(jsonPath("$.agreement_number").value(request.getAgreementNumber()))
                .andExpect(jsonPath("$.agreement_date").value(request.getAgreementDate().format(FORMATTER)))
                .andExpect(jsonPath("$.agreement_start_dt").value(request.getAgreementStartDate().format(FORMATTER)))
                .andExpect(jsonPath("$.availability_date").value(request.getAvailabilityDate().format(FORMATTER)))
                .andExpect(jsonPath("$.type.id").value(request.getTypeId().getValue()))
                .andExpect(jsonPath("$.status.id").value(deal.getStatus().getId().getValue()))
                .andExpect(jsonPath("$.sum").value(request.getSum()))
                .andExpect(jsonPath("$.close_dt").value(request.getCloseDate().format(FORMATTER)))
                .andExpect(jsonPath("$.contractors").isArray());
    }

    @Test
    @Transactional
    @Rollback
    public void changeDealStatusTest() throws Exception {
        Deal deal = dealRepository.findById(UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e")).get();

        DealChangeStatusDTO request = DealChangeStatusDTO.builder()
                .id(deal.getId())
                .dealStatusId(DealStatusEnum.ACTIVE)
                .build();

        mockMvc.perform(patch("http://localhost:8081/deal/change/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(deal.getId().toString()))
                .andExpect(jsonPath("$.status.id").value(DealStatusEnum.ACTIVE.getValue()));
    }

    @Test
    @Transactional
    @Rollback
    public void getDealById() throws Exception {
        Deal deal = dealRepository.findById(UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e")).get();

        mockMvc.perform(get("http://localhost:8081/deal/" + deal.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(deal.getId().toString()))
                .andExpect(jsonPath("$.description").value(deal.getDescription()))
                .andExpect(jsonPath("$.agreement_number").value(deal.getAgreementNumber()))
                .andExpect(jsonPath("$.type.id").value(deal.getType().getId().getValue()))
                .andExpect(jsonPath("$.status.id").value(deal.getStatus().getId().getValue()))
                .andExpect(jsonPath("$.sum").value(deal.getSum()))
                .andExpect(jsonPath("$.contractors").isArray());
    }

    @Test
    @Transactional
    @Rollback
    public void getDealsWithFilters() throws Exception {
        Deal deal = dealRepository.findById(UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e")).get();

        DealSearchRequestDTO request = DealSearchRequestDTO.builder()
                .id(deal.getId())
                .description(deal.getDescription())
                .agreementNumber("num")
                .build();

        mockMvc.perform(post("http://localhost:8081/deal/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(deal.getId().toString()))
                .andExpect(jsonPath("$[0].description").value(deal.getDescription()))
                .andExpect(jsonPath("$[0].agreement_number").value(deal.getAgreementNumber()))
                .andExpect(jsonPath("$[0].type.id").value(deal.getType().getId().getValue()))
                .andExpect(jsonPath("$[0].status.id").value(deal.getStatus().getId().getValue()))
                .andExpect(jsonPath("$[0].sum").value(deal.getSum()))
                .andExpect(jsonPath("$[0].contractors").isArray());
    }

}
