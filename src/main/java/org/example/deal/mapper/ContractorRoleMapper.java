package org.example.deal.mapper;

import org.example.deal.dto.ContractorRoleDTO;
import org.example.deal.entity.ContractorRole;

public interface ContractorRoleMapper {

    ContractorRole map(ContractorRoleDTO contractorRoleDTO);

    ContractorRoleDTO map(ContractorRole contractorRole);

}
