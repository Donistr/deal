package org.example.deal.mapper;

import org.example.deal.dto.ContractorRoleDTO;
import org.example.deal.entity.ContractorRole;

/**
 * Класс предназначенный для преобразования {@link ContractorRole} в {@link ContractorRoleDTO} и наоборот
 */
public interface ContractorRoleMapper {

    /**
     * Преобразует {@link ContractorRoleDTO} в {@link ContractorRole}
     * @param contractorRoleDTO {@link ContractorRoleDTO}
     * @return {@link ContractorRole}
     */
    ContractorRole map(ContractorRoleDTO contractorRoleDTO);

    /**
     * Преобразует {@link ContractorRole} в {@link ContractorRoleDTO}
     * @param contractorRole {@link ContractorRole}
     * @return {@link ContractorRoleDTO}
     */
    ContractorRoleDTO map(ContractorRole contractorRole);

}
