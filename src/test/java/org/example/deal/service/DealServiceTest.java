package org.example.deal.service;

import org.example.deal.dto.DealChangeStatusDTO;
import org.example.deal.dto.DealCreateOrUpdateDTO;
import org.example.deal.dto.DealDTO;
import org.example.deal.dto.DealSearchRequestDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.entity.Deal;
import org.example.deal.entity.help.DealStatusEnum;
import org.example.deal.entity.help.DealTypeEnum;
import org.example.deal.repository.DealRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class DealServiceTest {

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
    private DealService dealService;

    @Autowired
    private DealRepository dealRepository;

    @MockBean
    private SetMainBorrowerService setMainBorrowerService;

    @Test
    @Transactional
    @Rollback
    public void createDealTest() {
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

        DealDTO response = dealService.createOrUpdate(request);
        Assertions.assertEquals(request.getDescription(), response.getDescription());
        Assertions.assertEquals(request.getAgreementNumber(), response.getAgreementNumber());
        Assertions.assertEquals(request.getAgreementDate(), response.getAgreementDate());
        Assertions.assertEquals(request.getAgreementStartDate(), response.getAgreementStartDate());
        Assertions.assertEquals(request.getAvailabilityDate(), response.getAvailabilityDate());
        Assertions.assertEquals(request.getTypeId(), response.getType().getId());
        Assertions.assertEquals(request.getSum(), response.getSum());
        Assertions.assertEquals(request.getCloseDate(), response.getCloseDate());

        Optional<Deal> findOptional = dealRepository.findByIdAndIsActiveTrue(response.getId());
        Assertions.assertTrue(findOptional.isPresent());
        Deal find = findOptional.get();
        Assertions.assertEquals(response.getId(), find.getId());
        Assertions.assertEquals(request.getDescription(), find.getDescription());
        Assertions.assertEquals(request.getAgreementNumber(), find.getAgreementNumber());
        Assertions.assertEquals(request.getAgreementDate(), find.getAgreementDate());
        Assertions.assertEquals(request.getAgreementStartDate(), find.getAgreementStartDate());
        Assertions.assertEquals(request.getAvailabilityDate(), find.getAvailabilityDate());
        Assertions.assertEquals(request.getTypeId(), find.getType().getId());
        Assertions.assertEquals(request.getSum(), find.getSum());
        Assertions.assertEquals(request.getCloseDate(), find.getCloseDate());
    }

    @Test
    @Transactional
    @Rollback
    public void changeDealTest() {
        UUID id = UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e");
        Optional<Deal> findOptional = dealRepository.findByIdAndIsActiveTrue(id);
        Assertions.assertTrue(findOptional.isPresent());

        DealCreateOrUpdateDTO request = DealCreateOrUpdateDTO.builder()
                .id(id)
                .description("description_test")
                .agreementNumber("agreement_number_test")
                .agreementDate(LocalDateTime.now())
                .agreementStartDate(LocalDateTime.now())
                .availabilityDate(LocalDateTime.now())
                .typeId(DealTypeEnum.CREDIT)
                .sum(123.4)
                .closeDate(LocalDateTime.now())
                .build();

        DealDTO response = dealService.createOrUpdate(request);
        Assertions.assertEquals(request.getId(), response.getId());
        Assertions.assertEquals(request.getDescription(), response.getDescription());
        Assertions.assertEquals(request.getAgreementNumber(), response.getAgreementNumber());
        Assertions.assertEquals(request.getAgreementDate(), response.getAgreementDate());
        Assertions.assertEquals(request.getAgreementStartDate(), response.getAgreementStartDate());
        Assertions.assertEquals(request.getAvailabilityDate(), response.getAvailabilityDate());
        Assertions.assertEquals(request.getTypeId(), response.getType().getId());
        Assertions.assertEquals(request.getSum(), response.getSum());
        Assertions.assertEquals(request.getCloseDate(), response.getCloseDate());

        findOptional = dealRepository.findByIdAndIsActiveTrue(id);
        Assertions.assertTrue(findOptional.isPresent());
        Deal find = findOptional.get();
        Assertions.assertEquals(response.getId(), find.getId());
        Assertions.assertEquals(request.getDescription(), find.getDescription());
        Assertions.assertEquals(request.getAgreementNumber(), find.getAgreementNumber());
        Assertions.assertEquals(request.getAgreementDate(), find.getAgreementDate());
        Assertions.assertEquals(request.getAgreementStartDate(), find.getAgreementStartDate());
        Assertions.assertEquals(request.getAvailabilityDate(), find.getAvailabilityDate());
        Assertions.assertEquals(request.getTypeId(), find.getType().getId());
        Assertions.assertEquals(request.getSum(), find.getSum());
        Assertions.assertEquals(request.getCloseDate(), find.getCloseDate());
    }

    @Test
    @Transactional
    @Rollback
    public void changeDealStatusDraftToActiveTest() {
        UUID id = UUID.fromString("e15d8ba7-1ff1-42c3-a951-740fb36b958a");
        Deal find = dealRepository.findByIdAndIsActiveTrue(id).get();
        Assertions.assertEquals(find.getStatus().getId(), DealStatusEnum.DRAFT);

        DealChangeStatusDTO request = DealChangeStatusDTO.builder()
                .id(find.getId())
                .dealStatusId(DealStatusEnum.ACTIVE)
                .build();

        dealService.changeStatus(request);
        find = dealRepository.findByIdAndIsActiveTrue(id).get();
        Assertions.assertEquals(find.getStatus().getId(), DealStatusEnum.ACTIVE);

        verify(setMainBorrowerService, times(1)).setMainBorrower(any(Contractor.class), eq(true));
    }

    @Test
    @Transactional
    @Rollback
    public void changeDealStatusActiveToDraftTest() {
        UUID id = UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e");
        Deal find = dealRepository.findByIdAndIsActiveTrue(id).get();
        Assertions.assertEquals(find.getStatus().getId(), DealStatusEnum.ACTIVE);

        DealChangeStatusDTO request = DealChangeStatusDTO.builder()
                .id(find.getId())
                .dealStatusId(DealStatusEnum.DRAFT)
                .build();

        dealService.changeStatus(request);
        find = dealRepository.findByIdAndIsActiveTrue(id).get();
        Assertions.assertEquals(find.getStatus().getId(), DealStatusEnum.DRAFT);

        verify(setMainBorrowerService, times(1)).setMainBorrower(any(Contractor.class), eq(false));
    }

    @Test
    @Transactional
    @Rollback
    public void getDealTest() {
        UUID id = UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e");

        Deal find = dealRepository.findByIdAndIsActiveTrue(id).get();

        DealDTO response = dealService.getDealWithContractors(id);

        Assertions.assertEquals(find.getId(), response.getId());
        Assertions.assertEquals(find.getDescription(), response.getDescription());
        Assertions.assertEquals(find.getAgreementNumber(), response.getAgreementNumber());
        Assertions.assertEquals(find.getAgreementDate(), response.getAgreementDate());
        Assertions.assertEquals(find.getAgreementStartDate(), response.getAgreementStartDate());
        Assertions.assertEquals(find.getAvailabilityDate(), response.getAvailabilityDate());
        Assertions.assertEquals(find.getType().getId(), response.getType().getId());
        Assertions.assertEquals(find.getStatus().getId(), response.getStatus().getId());
        Assertions.assertEquals(find.getSum(), response.getSum());
        Assertions.assertEquals(find.getCloseDate(), response.getCloseDate());
        Assertions.assertEquals(find.getContractors().size(), response.getContractors().size());
        Assertions.assertEquals(find.getContractors().get(0).getId(), response.getContractors().get(0).getId());
    }

    @Test
    @Transactional
    @Rollback
    public void getDealsSearchIdTest() {
        UUID id = UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e");

        DealSearchRequestDTO request = DealSearchRequestDTO.builder()
                .id(id)
                .build();

        List<DealDTO> response = dealService.getDeals(request, Pageable.ofSize(10));
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(id, response.get(0).getId());
    }

    @Test
    @Transactional
    @Rollback
    public void getDealsSearchAgreementNumberTest() {
        String agreementNumberSubString = "nt_nu";

        DealSearchRequestDTO request = DealSearchRequestDTO.builder()
                .agreementNumber(agreementNumberSubString)
                .build();

        List<DealDTO> response = dealService.getDeals(request, Pageable.ofSize(10));
        Assertions.assertEquals(2, response.size());
    }

    @Test
    @Transactional
    @Rollback
    public void getDealsSearchAgreementDateFromToTest() {
        LocalDateTime from = LocalDateTime.of(2019, 12, 12, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2021, 12, 12, 0, 0, 0);
        DealSearchRequestDTO request = DealSearchRequestDTO.builder()
                .agreementDateFrom(from)
                .agreementDateTo(to)
                .build();
        List<DealDTO> response = dealService.getDeals(request, Pageable.ofSize(10));
        Assertions.assertEquals(2, response.size());

        from = LocalDateTime.of(2019, 12, 12, 0, 0, 0);
        to = LocalDateTime.of(2019, 12, 18, 0, 0, 0);
        request = DealSearchRequestDTO.builder()
                .agreementDateFrom(from)
                .agreementDateTo(to)
                .build();
        response = dealService.getDeals(request, Pageable.ofSize(10));
        Assertions.assertEquals(0, response.size());
    }

    @Test
    @Transactional
    @Rollback
    public void getDealsSearchTypesTest() {
        DealSearchRequestDTO request = DealSearchRequestDTO.builder()
                .typeIds(List.of(DealTypeEnum.CREDIT, DealTypeEnum.OVERDRAFT))
                .build();
        List<DealDTO> response = dealService.getDeals(request, Pageable.ofSize(10));
        Assertions.assertEquals(2, response.size());

        request = DealSearchRequestDTO.builder()
                .typeIds(List.of(DealTypeEnum.OTHER))
                .build();
        response = dealService.getDeals(request, Pageable.ofSize(10));
        Assertions.assertEquals(0, response.size());
    }

    @Test
    @Transactional
    @Rollback
    public void getDealsSearchSearchFieldTest() {
        DealSearchRequestDTO request = DealSearchRequestDTO.builder()
                .searchField("name")
                .build();
        List<DealDTO> response = dealService.getDeals(request, Pageable.ofSize(10));
        System.out.println(response);
        Assertions.assertEquals(1, response.size());

        request = DealSearchRequestDTO.builder()
                .searchField("zzz")
                .build();
        response = dealService.getDeals(request, Pageable.ofSize(10));
        Assertions.assertEquals(0, response.size());
    }

}
