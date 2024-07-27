package org.example.deal.mapper;

import org.example.deal.dto.DealTypeDTO;
import org.example.deal.entity.DealType;

/**
 * Класс предназначенный для преобразования {@link DealType} в {@link DealTypeDTO} и наоборот
 */
public interface DealTypeMapper {

    /**
     * Преобразует {@link DealTypeDTO} в {@link DealType}
     * @param dealTypeDTO {@link DealTypeDTO}
     * @return {@link DealType}
     */
    DealType map(DealTypeDTO dealTypeDTO);

    /**
     * Преобразует {@link DealType} в {@link DealTypeDTO}
     * @param dealType {@link DealType}
     * @return {@link DealTypeDTO}
     */
    DealTypeDTO map(DealType dealType);

}
