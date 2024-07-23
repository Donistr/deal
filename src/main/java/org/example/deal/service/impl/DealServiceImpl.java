package org.example.deal.service.impl;

import org.example.deal.dto.*;
import org.example.deal.entity.Deal;
import org.example.deal.entity.DealContractor;
import org.example.deal.entity.DealStatus;
import org.example.deal.entity.DealType;
import org.example.deal.exception.DealNotFoundException;
import org.example.deal.exception.DealStatusNotFoundException;
import org.example.deal.exception.DealTypeNotFoundException;
import org.example.deal.mapper.*;
import org.example.deal.repository.*;
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

    private final DealTypeRepository dealTypeRepository;

    private final DealMapper dealMapper;

    private final ContractorRoleMapper contractorRoleMapper;

    private final DealTypeMapper dealTypeMapper;

    private final DealStatusMapper dealStatusMapper;

    public DealServiceImpl(DealRepository dealRepository, DealStatusRepository dealStatusRepository, DealContractorRepository dealContractorRepository, DealContractorRoleRepository dealContractorRoleRepository, DealTypeRepository dealTypeRepository, DealMapper dealMapper, ContractorRoleMapper contractorRoleMapper, DealTypeMapper dealTypeMapper, DealStatusMapper dealStatusMapper) {
        this.dealRepository = dealRepository;
        this.dealStatusRepository = dealStatusRepository;
        this.dealContractorRepository = dealContractorRepository;
        this.dealContractorRoleRepository = dealContractorRoleRepository;
        this.dealTypeRepository = dealTypeRepository;
        this.dealMapper = dealMapper;
        this.contractorRoleMapper = contractorRoleMapper;
        this.dealTypeMapper = dealTypeMapper;
        this.dealStatusMapper = dealStatusMapper;
    }

    @Override
    public DealDTO save(DealCreateOrUpdateDTO dealCreateOrUpdateDTO) {
        if (dealCreateOrUpdateDTO.getTypeId() == null) {
            throw new DealTypeNotFoundException("тип сделки не задан");
        }
        DealType dealType = dealTypeRepository.findById(dealCreateOrUpdateDTO.getTypeId())
                .orElseThrow(() -> new DealTypeNotFoundException("не найден тип сделки с id " +
                        dealCreateOrUpdateDTO.getTypeId()));

        Deal deal = Deal.builder()
                .id(dealCreateOrUpdateDTO.getId())
                .description(dealCreateOrUpdateDTO.getDescription())
                .agreementNumber(dealCreateOrUpdateDTO.getAgreementNumber())
                .agreementDate(dealCreateOrUpdateDTO.getAgreementDate())
                .agreementStartDate(dealCreateOrUpdateDTO.getAgreementStartDate())
                .availabilityDate(dealCreateOrUpdateDTO.getAvailabilityDate())
                .type(dealType)
                .build();

        if (deal.getId() == null) {
            return createNewDeal(deal);
        }
        Optional<Deal> fromDatabaseOptional = dealRepository.findById(deal.getId());
        if (fromDatabaseOptional.isEmpty()) {
            return createNewDeal(deal);
        }

        Deal fromDatabase = fromDatabaseOptional.get();
        if (deal.getId() != null) {
            fromDatabase.setId(deal.getId());
        }
        if (deal.getDescription() != null) {
            fromDatabase.setDescription(deal.getDescription());
        }
        if (deal.getAgreementNumber() != null) {
            fromDatabase.setAgreementNumber(deal.getAgreementNumber());
        }
        if (deal.getAgreementDate() != null) {
            fromDatabase.setAgreementDate(deal.getAgreementDate());
        }
        if (deal.getAgreementStartDate() != null) {
            fromDatabase.setAgreementStartDate(deal.getAgreementStartDate());
        }
        if (deal.getAvailabilityDate() != null) {
            fromDatabase.setAvailabilityDate(deal.getAvailabilityDate());
        }
        if (deal.getType() != null) {
            fromDatabase.setType(deal.getType());
        }
        if (deal.getSum() != null) {
            fromDatabase.setSum(deal.getSum());
        }
        if (deal.getCloseDate() != null) {
            fromDatabase.setCloseDate(deal.getCloseDate());
        }

        return dealMapper.map(dealRepository.saveAndFlush(fromDatabase));
    }

    @Override
    public DealDTO changeStatus(DealChangeStatusDTO dealChangeStatusDTO) {
        DealStatus dealStatus = dealStatusRepository.findById(dealChangeStatusDTO.getDealStatusId())
                .orElseThrow(() -> new DealStatusNotFoundException("не найден статус с id " +
                        dealChangeStatusDTO.getDealStatusId()));
        Deal deal = dealRepository.findById(dealChangeStatusDTO.getId())
                .orElseThrow(() -> new DealNotFoundException("не найдена сделка с id " + dealChangeStatusDTO.getId()));

        deal.setStatus(dealStatus);
        return dealMapper.map(dealRepository.saveAndFlush(deal));
    }

    @Override
    public DealWithContractorsDTO getDealWithContractors(UUID id) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new DealStatusNotFoundException("не найдена сделка с id " + id));

        List<ContractorWithRolesDTO> contractorWithRolesDTOS = dealContractorRepository.findAllByDeal(deal).stream()
                .filter(DealContractor::getIsActive)
                .map(dealContractor -> {
                    ContractorWithRolesDTO contractorWithRolesDTO = ContractorWithRolesDTO.builder()
                            .id(dealContractor.getId())
                            .contractorId(dealContractor.getContractorId())
                            .name(dealContractor.getName())
                            .main(dealContractor.getMain())
                            .build();
                    List<ContractorRoleDTO> roles = dealContractorRoleRepository.findAllByDealContractor(dealContractor).stream()
                            .map(dealContractorRole -> contractorRoleMapper.map(dealContractorRole.getId().getContractorRole()))
                            .toList();
                    contractorWithRolesDTO.setRoles(roles);

                    return contractorWithRolesDTO;
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
                .contractors(contractorWithRolesDTOS)
                .build();
    }

    private DealDTO createNewDeal(Deal deal) {
        deal.setStatus(dealStatusRepository.findById(CREATE_DEAL_STATUS)
                .orElseThrow(() -> new DealStatusNotFoundException("не найден статус " + CREATE_DEAL_STATUS)));
        return dealMapper.map(dealRepository.saveAndFlush(deal));
    }

}
