package org.example.deal.service;

import org.example.deal.dto.ContractorCreateOrUpdateDTO;
import org.example.deal.dto.ContractorDTO;

import java.util.UUID;

/**
 * Класс предоставляющий методы работы с контрагентами
 */
public interface ContractorService {

    /**
     * Создаёт/изменяет контрагента
     * @param contractorDTO запрос
     * @return контрагент
     */
    ContractorDTO createOrUpdate(ContractorCreateOrUpdateDTO contractorDTO);

    /**
     * Удаляет контрагента
     * @param id id контрагента
     */
    void delete(UUID id);

}
