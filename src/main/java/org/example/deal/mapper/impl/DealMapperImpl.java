package org.example.deal.mapper.impl;

import org.example.deal.dto.ContractorDTO;
import org.example.deal.dto.DealDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.entity.Deal;
import org.example.deal.mapper.ContractorMapper;
import org.example.deal.mapper.DealMapper;
import org.example.deal.mapper.DealStatusMapper;
import org.example.deal.mapper.DealTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        List<Contractor> contractors = new ArrayList<>();
        if (dealDTO.getContractors() != null) {
            contractors = dealDTO.getContractors().stream()
                    .map(contractorMapper::map)
                    .toList();
        }

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
                .contractors(contractors)
                .build();
    }

    @Override
    public DealDTO map(Deal deal) {
        List<ContractorDTO> contractors = new ArrayList<>();
        if (deal.getContractors() != null) {
            contractors = deal.getContractors().stream()
                    .map(contractorMapper::map)
                    .toList();
        }

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
                .contractors(contractors)
                .build();
    }

}
