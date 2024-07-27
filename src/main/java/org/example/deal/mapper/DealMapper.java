package org.example.deal.mapper;

import org.example.deal.dto.DealDTO;
import org.example.deal.entity.Deal;

/**
 * Класс предназначенный для преобразования {@link Deal} в {@link DealDTO} и наоборот
 */
public interface DealMapper {

    /**
     * Преобразует {@link DealDTO} в {@link Deal}
     * @param dealDTO {@link DealDTO}
     * @return {@link Deal}
     */
    Deal map(DealDTO dealDTO);

    /**
     * Преобразует {@link Deal} в {@link DealDTO}
     * @param deal {@link Deal}
     * @return {@link DealDTO}
     */
    DealDTO map(Deal deal);

}
