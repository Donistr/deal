package org.example.deal.service.impl;

import org.example.deal.dto.*;
import org.example.deal.entity.Deal;
import org.example.deal.entity.DealContractor;
import org.example.deal.entity.DealStatus;
import org.example.deal.exception.DealNotFoundException;
import org.example.deal.exception.DealStatusNotFoundException;
import org.example.deal.mapper.*;
import org.example.deal.repository.DealContractorRepository;
import org.example.deal.repository.DealContractorRoleRepository;
import org.example.deal.repository.DealRepository;
import org.example.deal.repository.DealStatusRepository;
import org.example.deal.service.DealService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DealServiceImpl implements DealService {

    private static final String CREATE_DEAL_STATUS = "DRAFT";

    private final DealRepository dealRepository;

    private final DealStatusRepository dealStatusRepository;

    private final DealContractorRepository dealContractorRepository;

    private final DealContractorRoleRepository dealContractorRoleRepository;

    private final DealCreateOrUpdateMapper dealCreateOrUpdateMapper;

    private final DealMapper dealMapper;

    private final ContractorMapper contractorMapper;

    private final ContractorRoleMapper contractorRoleMapper;

    private final DealTypeMapper dealTypeMapper;

    private final DealStatusMapper dealStatusMapper;

    public DealServiceImpl(DealRepository dealRepository, DealStatusRepository dealStatusRepository, DealContractorRepository dealContractorRepository, DealContractorRoleRepository dealContractorRoleRepository, DealCreateOrUpdateMapper dealCreateOrUpdateMapper, DealMapper dealMapper, ContractorMapper contractorMapper, ContractorRoleMapper contractorRoleMapper, DealTypeMapper dealTypeMapper, DealStatusMapper dealStatusMapper) {
        this.dealRepository = dealRepository;
        this.dealStatusRepository = dealStatusRepository;
        this.dealContractorRepository = dealContractorRepository;
        this.dealContractorRoleRepository = dealContractorRoleRepository;
        this.dealCreateOrUpdateMapper = dealCreateOrUpdateMapper;
        this.dealMapper = dealMapper;
        this.contractorMapper = contractorMapper;
        this.contractorRoleMapper = contractorRoleMapper;
        this.dealTypeMapper = dealTypeMapper;
        this.dealStatusMapper = dealStatusMapper;
    }

    @Override
    public DealDTO save(DealCreateOrUpdateDTO dealCreateOrUpdateDTO) {
        Deal deal = dealCreateOrUpdateMapper.map(dealCreateOrUpdateDTO);

        if (deal.getId() == null) {
            return createNewDeal(deal);
        }
        Optional<Deal> fromDatabaseOptional = dealRepository.findById(deal.getId());
        if (fromDatabaseOptional.isEmpty()) {
            return createNewDeal(deal);
        }

        Deal fromDatabase = fromDatabaseOptional.get();
        fromDatabase.setId(deal.getId());
        fromDatabase.setDescription(deal.getDescription());
        fromDatabase.setAgreementNumber(deal.getAgreementNumber());
        fromDatabase.setAgreementDate(deal.getAgreementDate());
        fromDatabase.setAgreementStartDate(deal.getAgreementStartDate());
        fromDatabase.setAvailabilityDate(deal.getAvailabilityDate());
        fromDatabase.setType(deal.getType());
        fromDatabase.setSum(deal.getSum());
        fromDatabase.setCloseDate(deal.getCloseDate());

        return dealMapper.map(dealRepository.saveAndFlush(fromDatabase));
    }

    @Override
    public DealDTO changeStatus(DealChangeStatusDTO dealChangeStatusDTO) {
        DealStatus dealStatus = dealStatusRepository.findById(dealChangeStatusDTO.getDealStatus().getId())
                .orElseThrow(() -> new DealStatusNotFoundException("не найден статус с id " +
                        dealChangeStatusDTO.getDealStatus().getId()));
        Deal deal = dealRepository.findById(dealChangeStatusDTO.getId())
                .orElseThrow(() -> new DealNotFoundException("не найдена сделка с id " + dealChangeStatusDTO.getId()));

        deal.setStatus(dealStatus);
        return dealMapper.map(dealRepository.saveAndFlush(deal));
    }

    @Override
    public DealWithContractorsDTO getDealWithContractors(UUID id) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new DealStatusNotFoundException("не найдена сделка с id " + id));

        List<ContractorDTO> contractorDTOS = dealContractorRepository.findAllByDeal(deal).stream()
                .filter(DealContractor::getIsActive)
                .map(dealContractor -> {
                    ContractorDTO contractorDTO = contractorMapper.map(dealContractor);
                    dealContractorRoleRepository.findAllByDealContractor(dealContractor).stream()
                            .map(dealContractorRole -> contractorRoleMapper.map(dealContractorRole.getContractorRole()))
                            .forEach(contractorDTO.getRoles()::add);

                    return contractorDTO;
                })
                .toList();

        return DealWithContractorsDTO.builder()
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
                .contractors(contractorDTOS)
                .build();
    }

    private DealDTO createNewDeal(Deal deal) {
        deal.setStatus(dealStatusRepository.findById(CREATE_DEAL_STATUS)
                .orElseThrow(() -> new DealStatusNotFoundException("не найден статус " + CREATE_DEAL_STATUS)));
        return dealMapper.map(dealRepository.saveAndFlush(deal));
    }

}
