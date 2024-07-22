package org.example.deal.mapper.impl;

import org.example.deal.dto.ContractorDTO;
import org.example.deal.entity.DealContractor;
import org.example.deal.mapper.ContractorMapper;
import org.springframework.stereotype.Component;

@Component
public class ContractorMapperImpl implements ContractorMapper {

    @Override
    public DealContractor map(ContractorDTO contractorDTO) {
        return DealContractor.builder()
                .id(contractorDTO.getId())
                .deal(contractorDTO.getDeal())
                .contractorId(contractorDTO.getContractorId())
                .name(contractorDTO.getName())
                .inn(contractorDTO.getInn())
                .main(contractorDTO.getMain())
                .build();
    }

    @Override
    public ContractorDTO map(DealContractor dealContractor) {
        return ContractorDTO.builder()
                .id(dealContractor.getId())
                .deal(dealContractor.getDeal())
                .contractorId(dealContractor.getContractorId())
                .name(dealContractor.getName())
                .inn(dealContractor.getInn())
                .main(dealContractor.getMain())
                .build();
    }

}
