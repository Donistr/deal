package org.example.deal.mapper.impl;

import org.example.deal.dto.ContractorDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.mapper.ContractorMapper;
import org.springframework.stereotype.Component;

@Component
public class ContractorMapperImpl implements ContractorMapper {

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
        return ContractorDTO.builder()
                .id(contractor.getId())
                .contractorId(contractor.getContractorId())
                .name(contractor.getName())
                .inn(contractor.getInn())
                .main(contractor.getMain())
                .build();
    }

}
