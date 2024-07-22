package org.example.deal.mapper.impl;

import org.example.deal.dto.DealCreateOrUpdateDTO;
import org.example.deal.entity.Deal;
import org.example.deal.mapper.DealCreateOrUpdateMapper;
import org.example.deal.mapper.DealTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DealCreateOrUpdateMapperImpl implements DealCreateOrUpdateMapper {

    private final DealTypeMapper dealTypeMapper;

    @Autowired
    public DealCreateOrUpdateMapperImpl(DealTypeMapper dealTypeMapper) {
        this.dealTypeMapper = dealTypeMapper;
    }

    @Override
    public Deal map(DealCreateOrUpdateDTO dealCreateOrUpdateDTO) {
        return Deal.builder()
                .id(dealCreateOrUpdateDTO.getId())
                .description(dealCreateOrUpdateDTO.getDescription())
                .agreementNumber(dealCreateOrUpdateDTO.getAgreementNumber())
                .agreementDate(dealCreateOrUpdateDTO.getAgreementDate())
                .agreementStartDate(dealCreateOrUpdateDTO.getAgreementStartDate())
                .availabilityDate(dealCreateOrUpdateDTO.getAvailabilityDate())
                .type(dealTypeMapper.map(dealCreateOrUpdateDTO.getType()))
                .sum(dealCreateOrUpdateDTO.getSum())
                .closeDate(dealCreateOrUpdateDTO.getCloseDate())
                .build();
    }

    @Override
    public DealCreateOrUpdateDTO map(Deal deal) {
        return DealCreateOrUpdateDTO.builder()
                .id(deal.getId())
                .description(deal.getDescription())
                .agreementNumber(deal.getAgreementNumber())
                .agreementDate(deal.getAgreementDate())
                .agreementStartDate(deal.getAgreementStartDate())
                .availabilityDate(deal.getAvailabilityDate())
                .type(dealTypeMapper.map(deal.getType()))
                .sum(deal.getSum())
                .closeDate(deal.getCloseDate())
                .build();
    }

}
