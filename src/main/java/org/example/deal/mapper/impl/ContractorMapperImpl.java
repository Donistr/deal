package org.example.deal.mapper.impl;

import org.example.deal.dto.ContractorDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.mapper.ContractorMapper;
import org.example.deal.mapper.DealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractorMapperImpl implements ContractorMapper {

    private final DealMapper dealMapper;

    @Autowired
    public ContractorMapperImpl(DealMapper dealMapper) {
        this.dealMapper = dealMapper;
    }

    @Override
    public Contractor map(ContractorDTO contractorDTO) {
        return Contractor.builder()
                .id(contractorDTO.getId())
                .deal(dealMapper.map(contractorDTO.getDeal()))
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
                .deal(dealMapper.map(contractor.getDeal()))
                .contractorId(contractor.getContractorId())
                .name(contractor.getName())
                .inn(contractor.getInn())
                .main(contractor.getMain())
                .build();
    }

}
