package org.example.deal.service;

import org.example.deal.dto.ContractorChangeRoleDTO;
import org.example.deal.dto.DealContractorRoleDTO;

public interface ContractorRoleService {

    DealContractorRoleDTO createOrUpdate(ContractorChangeRoleDTO contractorChangeRoleDTO);

    void delete(ContractorChangeRoleDTO contractorChangeRoleDTO);

}
