package org.example.deal.mapper.impl;

import org.example.deal.dto.ContractorRoleDTO;
import org.example.deal.entity.ContractorRole;
import org.example.deal.mapper.ContractorRoleMapper;
import org.springframework.stereotype.Component;

@Component
public class ContractorRoleMapperImpl implements ContractorRoleMapper {

    @Override
    public ContractorRole map(ContractorRoleDTO contractorRoleDTO) {
        return ContractorRole.builder()
                .id(contractorRoleDTO.getId())
                .name(contractorRoleDTO.getName())
                .category(contractorRoleDTO.getCategory())
                .build();
    }

    @Override
    public ContractorRoleDTO map(ContractorRole contractorRole) {
        return ContractorRoleDTO.builder()
                .id(contractorRole.getId())
                .name(contractorRole.getName())
                .category(contractorRole.getCategory())
                .build();
    }

}
