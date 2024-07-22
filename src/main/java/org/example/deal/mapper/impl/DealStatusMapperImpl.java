package org.example.deal.mapper.impl;

import org.example.deal.dto.DealStatusDTO;
import org.example.deal.entity.DealStatus;
import org.example.deal.mapper.DealStatusMapper;
import org.springframework.stereotype.Component;

@Component
public class DealStatusMapperImpl implements DealStatusMapper {

    @Override
    public DealStatus map(DealStatusDTO dealStatusDTO) {
        return DealStatus.builder()
                .id(dealStatusDTO.getId())
                .name(dealStatusDTO.getName())
                .build();
    }

    @Override
    public DealStatusDTO map(DealStatus dealStatus) {
        return DealStatusDTO.builder()
                .id(dealStatus.getId())
                .name(dealStatus.getName())
                .build();
    }
}
