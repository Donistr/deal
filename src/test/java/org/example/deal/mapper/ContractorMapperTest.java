package org.example.deal.mapper;

import org.example.deal.dto.ContractorDTO;
import org.example.deal.dto.ContractorRoleDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.entity.ContractorRole;
import org.example.deal.entity.DealContractorRole;
import org.example.deal.entity.help.ContractorRoleEnum;
import org.example.deal.entity.help.DealContractorRoleId;
import org.example.deal.mapper.impl.ContractorMapperImpl;
import org.example.deal.mapper.impl.ContractorRoleMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

@SpringJUnitConfig
class ContractorMapperTest {

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
    }

    @Autowired
    private ContractorMapper contractorMapper;

    @Test
    public void mapContractorToDtoTest() {
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

        ContractorDTO contractorDTO = contractorMapper.map(contractor);

        Assertions.assertEquals(contractor.getId(), contractorDTO.getId());
        Assertions.assertEquals(contractor.getContractorId(), contractorDTO.getContractorId());
        Assertions.assertEquals(contractor.getName(), contractorDTO.getName());
        Assertions.assertEquals(contractor.getInn(), contractorDTO.getInn());
        Assertions.assertEquals(contractor.getMain(), contractorDTO.getMain());
        Assertions.assertEquals(contractor.getRoles().get(0).getId().getContractorRole().getId(), contractorDTO.getRoles().get(0).getId());
    }

    @Test
    public void mapDtoToContractorTest() {
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

        Contractor contractor = contractorMapper.map(contractorDTO);

        Assertions.assertEquals(contractorDTO.getId(), contractor.getId());
        Assertions.assertEquals(contractor.getContractorId(), contractorDTO.getContractorId());
        Assertions.assertEquals(contractorDTO.getName(), contractor.getName());
        Assertions.assertEquals(contractorDTO.getInn(), contractor.getInn());
        Assertions.assertEquals(contractorDTO.getMain(), contractor.getMain());
    }

}
