package org.example.deal.mapper;

import org.example.deal.dto.DealStatusDTO;
import org.example.deal.entity.DealStatus;

/**
 * Класс предназначенный для преобразования {@link DealStatus} в {@link DealStatusDTO} и наоборот
 */
public interface DealStatusMapper {

    /**
     * Преобразует {@link DealStatusDTO} в {@link DealStatus}
     * @param dealStatusDTO {@link DealStatusDTO}
     * @return {@link DealStatus}
     */
    DealStatus map(DealStatusDTO dealStatusDTO);

    /**
     * Преобразует {@link DealStatus} в {@link DealStatusDTO}
     * @param dealStatus {@link DealStatus}
     * @return {@link DealStatusDTO}
     */
    DealStatusDTO map(DealStatus dealStatus);

}
