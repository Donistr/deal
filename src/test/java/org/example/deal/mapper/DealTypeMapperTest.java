package org.example.deal.mapper;

import org.example.deal.dto.DealTypeDTO;
import org.example.deal.entity.DealType;
import org.example.deal.entity.help.DealTypeEnum;
import org.example.deal.mapper.impl.DealTypeMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
class DealTypeMapperTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public DealTypeMapper dealTypeMapper() {
            return new DealTypeMapperImpl();
        }
    }

    @Autowired
    private DealTypeMapper dealTypeMapper;

    @Test
    public void mapDealTypeToDTOTest() {
        DealType dealType = DealType.builder()
                .id(DealTypeEnum.CREDIT)
                .name("name")
                .build();

        DealTypeDTO dealTypeDTO = dealTypeMapper.map(dealType);

        Assertions.assertEquals(dealType.getId(), dealTypeDTO.getId());
        Assertions.assertEquals(dealType.getName(), dealTypeDTO.getName());
    }

    @Test
    public void mapDTOToDealTypeTest() {
        DealTypeDTO dealTypeDTO = DealTypeDTO.builder()
                .id(DealTypeEnum.CREDIT)
                .name("name")
                .build();

        DealType dealType = dealTypeMapper.map(dealTypeDTO);

        Assertions.assertEquals(dealTypeDTO.getId(), dealType.getId());
        Assertions.assertEquals(dealTypeDTO.getName(), dealType.getName());
    }

}
