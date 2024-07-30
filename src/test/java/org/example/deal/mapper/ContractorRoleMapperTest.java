package org.example.deal.mapper;

import org.example.deal.dto.ContractorRoleDTO;
import org.example.deal.entity.ContractorRole;
import org.example.deal.entity.help.ContractorRoleEnum;
import org.example.deal.mapper.impl.ContractorRoleMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
class ContractorRoleMapperTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ContractorRoleMapper contractorRoleMapper() {
            return new ContractorRoleMapperImpl();
        }
    }

    @Autowired
    private ContractorRoleMapper contractorRoleMapper;

    @Test
    public void mapContractorRoleToDTOTest() {
        ContractorRole contractorRole = ContractorRole.builder()
                .id(ContractorRoleEnum.BORROWER)
                .name("name")
                .category("category")
                .build();

        ContractorRoleDTO contractorRoleDTO = contractorRoleMapper.map(contractorRole);

        Assertions.assertEquals(contractorRole.getId(), contractorRoleDTO.getId());
        Assertions.assertEquals(contractorRole.getName(), contractorRoleDTO.getName());
        Assertions.assertEquals(contractorRole.getCategory(), contractorRoleDTO.getCategory());
    }

    @Test
    public void mapDTOToContractorRoleTest() {
        ContractorRoleDTO contractorRoleDTO = ContractorRoleDTO.builder()
                .id(ContractorRoleEnum.BORROWER)
                .name("name")
                .category("category")
                .build();

        ContractorRole contractorRole = contractorRoleMapper.map(contractorRoleDTO);

        Assertions.assertEquals(contractorRoleDTO.getId(), contractorRole.getId());
        Assertions.assertEquals(contractorRoleDTO.getName(), contractorRole.getName());
        Assertions.assertEquals(contractorRoleDTO.getCategory(), contractorRole.getCategory());
    }

}
