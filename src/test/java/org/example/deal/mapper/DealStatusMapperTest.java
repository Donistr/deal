package org.example.deal.mapper;

import org.example.deal.dto.DealStatusDTO;
import org.example.deal.entity.DealStatus;
import org.example.deal.entity.help.DealStatusEnum;
import org.example.deal.mapper.impl.DealStatusMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class DealStatusMapperTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public DealStatusMapper dealStatusMapper() {
            return new DealStatusMapperImpl();
        }
    }

    @Autowired
    private DealStatusMapper dealStatusMapper;

    @Test
    public void mapDealStatusToDTOTest() {
        DealStatus dealStatus = DealStatus.builder()
                .id(DealStatusEnum.DRAFT)
                .name("name")
                .build();

        DealStatusDTO dealStatusDTO = dealStatusMapper.map(dealStatus);

        Assertions.assertEquals(dealStatus.getId(), dealStatusDTO.getId());
        Assertions.assertEquals(dealStatus.getName(), dealStatusDTO.getName());
    }

    @Test
    public void mapDTOToDealStatusTest() {
        DealStatusDTO dealStatusDTO = DealStatusDTO.builder()
                .id(DealStatusEnum.DRAFT)
                .name("name")
                .build();

        DealStatus dealStatus = dealStatusMapper.map(dealStatusDTO);

        Assertions.assertEquals(dealStatusDTO.getId(), dealStatus.getId());
        Assertions.assertEquals(dealStatusDTO.getName(), dealStatus.getName());
    }

}
