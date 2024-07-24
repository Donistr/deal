package org.example.deal.mapper.impl;

import org.example.deal.dto.DealDTO;
import org.example.deal.entity.Deal;
import org.example.deal.mapper.ContractorMapper;
import org.example.deal.mapper.DealMapper;
import org.example.deal.mapper.DealStatusMapper;
import org.example.deal.mapper.DealTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DealMapperImpl implements DealMapper {

    private final DealTypeMapper dealTypeMapper;

    private final DealStatusMapper dealStatusMapper;

    private final ContractorMapper contractorMapper;

    @Autowired
    public DealMapperImpl(DealTypeMapper dealTypeMapper, DealStatusMapper dealStatusMapper, ContractorMapper contractorMapper) {
        this.dealTypeMapper = dealTypeMapper;
        this.dealStatusMapper = dealStatusMapper;
        this.contractorMapper = contractorMapper;
    }

    @Override
    public Deal map(DealDTO dealDTO) {
        return Deal.builder()
                .id(dealDTO.getId())
                .description(dealDTO.getDescription())
                .agreementNumber(dealDTO.getAgreementNumber())
                .agreementDate(dealDTO.getAgreementDate())
                .agreementStartDate(dealDTO.getAgreementStartDate())
                .availabilityDate(dealDTO.getAvailabilityDate())
                .type(dealTypeMapper.map(dealDTO.getType()))
                .status(dealStatusMapper.map(dealDTO.getStatus()))
                .sum(dealDTO.getSum())
                .closeDate(dealDTO.getCloseDate())
                .contractors(dealDTO.getContractors().stream().map(contractorMapper::map).toList())
                .build();
    }

    @Override
    public DealDTO map(Deal deal) {
        return DealDTO.builder()
                .id(deal.getId())
                .description(deal.getDescription())
                .agreementNumber(deal.getAgreementNumber())
                .agreementDate(deal.getAgreementDate())
                .agreementStartDate(deal.getAgreementStartDate())
                .availabilityDate(deal.getAvailabilityDate())
                .type(dealTypeMapper.map(deal.getType()))
                .status(dealStatusMapper.map(deal.getStatus()))
                .sum(deal.getSum())
                .closeDate(deal.getCloseDate())
                .contractors(deal.getContractors().stream().map(contractorMapper::map).toList())
                .build();
    }

}
