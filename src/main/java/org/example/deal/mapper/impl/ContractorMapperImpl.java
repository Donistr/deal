package org.example.deal.mapper.impl;

import org.example.deal.dto.ContractorDTO;
import org.example.deal.dto.ContractorRoleDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.mapper.ContractorMapper;
import org.example.deal.mapper.ContractorRoleMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContractorMapperImpl implements ContractorMapper {

    private final ContractorRoleMapper contractorRoleMapper;

    public ContractorMapperImpl(ContractorRoleMapper contractorRoleMapper) {
        this.contractorRoleMapper = contractorRoleMapper;
    }

    @Override
    public Contractor map(ContractorDTO contractorDTO) {
        return Contractor.builder()
                .id(contractorDTO.getId())
                .contractorId(contractorDTO.getContractorId())
                .name(contractorDTO.getName())
                .inn(contractorDTO.getInn())
                .main(contractorDTO.getMain())
                .build();
    }

    @Override
    public ContractorDTO map(Contractor contractor) {
        List<ContractorRoleDTO> roles = new ArrayList<>();
        if (contractor.getRoles() != null) {
            roles = contractor.getRoles().stream()
                    .map(role -> contractorRoleMapper.map(role.getId().getContractorRole()))
                    .toList();
        }

        return ContractorDTO.builder()
                .id(contractor.getId())
                .contractorId(contractor.getContractorId())
                .name(contractor.getName())
                .inn(contractor.getInn())
                .main(contractor.getMain())
                .roles(roles)
                .build();
    }

}
