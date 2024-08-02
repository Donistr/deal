package org.example.deal.service;

import org.example.deal.dto.ContractorChangeRoleDTO;
import org.example.deal.dto.DealContractorRoleDTO;

/**
 * Класс предоставляющий методы работы с ролями контрагентов
 */
public interface ContractorRoleService {

    /**
     * Создаёт/изменяет роль контрагента
     * @param contractorChangeRoleDTO запрос
     * @return роль контрагента
     */
    DealContractorRoleDTO createOrUpdate(ContractorChangeRoleDTO contractorChangeRoleDTO);

    /**
     * Удаляет роль контрагенту
     * @param contractorChangeRoleDTO запрос
     */
    void delete(ContractorChangeRoleDTO contractorChangeRoleDTO);

}
