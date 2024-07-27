package org.example.deal.mapper.impl;

import org.example.deal.dto.DealTypeDTO;
import org.example.deal.entity.DealType;
import org.example.deal.mapper.DealTypeMapper;
import org.springframework.stereotype.Component;

/**
 * Реализация интерфейса {@link DealTypeMapper}
 */
@Component
public class DealTypeMapperImpl implements DealTypeMapper {

    /**
     * {@inheritDoc}
     */
    @Override
    public DealType map(DealTypeDTO dealTypeDTO) {
        return DealType.builder()
                .id(dealTypeDTO.getId())
                .name(dealTypeDTO.getName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DealTypeDTO map(DealType dealType) {
        return DealTypeDTO.builder()
                .id(dealType.getId())
                .name(dealType.getName())
                .build();
    }

}
