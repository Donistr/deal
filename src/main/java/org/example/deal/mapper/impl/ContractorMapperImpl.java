package org.example.deal.mapper.impl;

import org.example.deal.dto.ContractorDTO;
import org.example.deal.entity.DealContractor;
import org.example.deal.mapper.ContractorMapper;
import org.springframework.stereotype.Component;

@Component
public class ContractorMapperImpl implements ContractorMapper {

    @Override
    public ContractorDTO map(DealContractor dealContractor) {
        return ContractorDTO.builder()
                .id(dealContractor.getId())
                .contractorId(dealContractor.getContractorId())
                .name(dealContractor.getName())
                .main(dealContractor.getMain())
                .build();
    }
}
