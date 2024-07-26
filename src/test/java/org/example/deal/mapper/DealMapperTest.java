package org.example.deal.mapper;

import org.example.deal.dto.*;
import org.example.deal.entity.*;
import org.example.deal.entity.help.ContractorRoleEnum;
import org.example.deal.entity.help.DealContractorRoleId;
import org.example.deal.entity.help.DealStatusEnum;
import org.example.deal.entity.help.DealTypeEnum;
import org.example.deal.mapper.impl.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringJUnitConfig
public class DealMapperTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ContractorRoleMapper contractorRoleMapper() {
            return new ContractorRoleMapperImpl();
        }

        @Bean
        public ContractorMapper contractorMapper() {
            return new ContractorMapperImpl(contractorRoleMapper());
        }

        @Bean
        public DealTypeMapper dealTypeMapper() {
            return new DealTypeMapperImpl();
        }

        @Bean
        public DealStatusMapper dealStatusMapper() {
            return new DealStatusMapperImpl();
        }

        @Bean
        public DealMapper dealMapper() {
            return new DealMapperImpl(dealTypeMapper(), dealStatusMapper(), contractorMapper());
        }
    }

    @Autowired
    private DealMapper dealMapper;

    @Test
    public void mapDealToDTOTest() {
        Contractor contractor = Contractor.builder()
                .id(UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e"))
                .contractorId("asd123")
                .name("name123")
                .inn("inn123")
                .main(false)
                .roles(List.of(DealContractorRole.builder()
                        .id(DealContractorRoleId.builder()
                                .contractorRole(ContractorRole.builder()
                                        .id(ContractorRoleEnum.BORROWER)
                                        .build())
                                .build())
                        .build()))
                .build();

        Deal deal = Deal.builder()
                .id(UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e"))
                .description("description")
                .agreementNumber("agreementNumber")
                .agreementDate(LocalDateTime.now())
                .agreementStartDate(LocalDateTime.now())
                .availabilityDate(LocalDateTime.now())
                .type(DealType.builder()
                        .id(DealTypeEnum.CREDIT)
                        .build())
                .status(DealStatus.builder()
                        .id(DealStatusEnum.DRAFT)
                        .build())
                .sum(123.4)
                .closeDate(LocalDateTime.now())
                .contractors(List.of(contractor))
                .build();

        DealDTO dealDTO = dealMapper.map(deal);

        Assertions.assertEquals(deal.getId(), dealDTO.getId());
        Assertions.assertEquals(deal.getDescription(), dealDTO.getDescription());
        Assertions.assertEquals(deal.getAgreementNumber(), dealDTO.getAgreementNumber());
        Assertions.assertEquals(deal.getAgreementDate(), dealDTO.getAgreementDate());
        Assertions.assertEquals(deal.getAgreementStartDate(), dealDTO.getAgreementStartDate());
        Assertions.assertEquals(deal.getAvailabilityDate(), dealDTO.getAvailabilityDate());
        Assertions.assertEquals(deal.getType().getId(), dealDTO.getType().getId());
        Assertions.assertEquals(deal.getStatus().getId(), dealDTO.getStatus().getId());
        Assertions.assertEquals(deal.getSum(), dealDTO.getSum());
        Assertions.assertEquals(deal.getCloseDate(), dealDTO.getCloseDate());
        Assertions.assertEquals(deal.getContractors().get(0).getId(), dealDTO.getContractors().get(0).getId());
        Assertions.assertEquals(deal.getContractors().get(0).getContractorId(), dealDTO.getContractors().get(0).getContractorId());
        Assertions.assertEquals(deal.getContractors().get(0).getName(), dealDTO.getContractors().get(0).getName());
        Assertions.assertEquals(deal.getContractors().get(0).getInn(), dealDTO.getContractors().get(0).getInn());
        Assertions.assertEquals(deal.getContractors().get(0).getMain(), dealDTO.getContractors().get(0).getMain());
    }

    @Test
    public void mapDTOToDealTest() {
        ContractorDTO contractorDTO = ContractorDTO.builder()
                .id(UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e"))
                .contractorId("asd123")
                .name("name123")
                .inn("inn123")
                .main(false)
                .roles(List.of(ContractorRoleDTO.builder()
                        .id(ContractorRoleEnum.BORROWER)
                        .build()))
                .build();

        DealDTO dealDTO = DealDTO.builder()
                .id(UUID.fromString("e669b707-e162-4ae6-8555-c8a68006535e"))
                .description("description")
                .agreementNumber("agreementNumber")
                .agreementDate(LocalDateTime.now())
                .agreementStartDate(LocalDateTime.now())
                .availabilityDate(LocalDateTime.now())
                .type(DealTypeDTO.builder()
                        .id(DealTypeEnum.CREDIT)
                        .build())
                .status(DealStatusDTO.builder()
                        .id(DealStatusEnum.DRAFT)
                        .build())
                .sum(123.4)
                .closeDate(LocalDateTime.now())
                .contractors(List.of(contractorDTO))
                .build();

        Deal deal = dealMapper.map(dealDTO);

        Assertions.assertEquals(dealDTO.getId(), deal.getId());
        Assertions.assertEquals(dealDTO.getDescription(), deal.getDescription());
        Assertions.assertEquals(dealDTO.getAgreementNumber(), deal.getAgreementNumber());
        Assertions.assertEquals(dealDTO.getAgreementDate(), deal.getAgreementDate());
        Assertions.assertEquals(dealDTO.getAgreementStartDate(), deal.getAgreementStartDate());
        Assertions.assertEquals(dealDTO.getAvailabilityDate(), deal.getAvailabilityDate());
        Assertions.assertEquals(dealDTO.getType().getId(), deal.getType().getId());
        Assertions.assertEquals(dealDTO.getStatus().getId(), deal.getStatus().getId());
        Assertions.assertEquals(dealDTO.getSum(), deal.getSum());
        Assertions.assertEquals(dealDTO.getCloseDate(), deal.getCloseDate());
        Assertions.assertEquals(dealDTO.getContractors().get(0).getId(), deal.getContractors().get(0).getId());
        Assertions.assertEquals(dealDTO.getContractors().get(0).getContractorId(), deal.getContractors().get(0).getContractorId());
        Assertions.assertEquals(dealDTO.getContractors().get(0).getName(), deal.getContractors().get(0).getName());
        Assertions.assertEquals(dealDTO.getContractors().get(0).getInn(), deal.getContractors().get(0).getInn());
        Assertions.assertEquals(dealDTO.getContractors().get(0).getMain(), deal.getContractors().get(0).getMain());
    }

}
