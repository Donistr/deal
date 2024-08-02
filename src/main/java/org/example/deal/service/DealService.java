package org.example.deal.service;

import org.example.deal.dto.DealChangeStatusDTO;
import org.example.deal.dto.DealCreateOrUpdateDTO;
import org.example.deal.dto.DealDTO;
import org.example.deal.dto.DealSearchRequestDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Класс предоставляющий методы работы со сделками
 */
public interface DealService {

    /**
     * Создаёт/изменяет сделку
     * @param dealCreateOrUpdateDTO запрос
     * @return сделка
     */
    DealDTO createOrUpdate(DealCreateOrUpdateDTO dealCreateOrUpdateDTO);

    /**
     * Изменяет статус сделки
     * @param dealChangeStatusDTO запрос
     * @return сделка
     */
    DealDTO changeStatus(DealChangeStatusDTO dealChangeStatusDTO);

    /**
     * Получается сделку по id
     * @param id id сделки
     * @return сделка
     */
    DealDTO getDealWithContractors(UUID id);

    /**
     * Получает список всех сделок, удовлетворяющих запросу
     * @param dealSearchRequestDTO запросу
     * @param pageable пагинация
     * @return список сделок
     */
    List<DealDTO> getDeals(DealSearchRequestDTO dealSearchRequestDTO, Pageable pageable);

}
