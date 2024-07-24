package org.example.deal.service.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.deal.dto.*;
import org.example.deal.entity.*;
import org.example.deal.exception.DealNotFoundException;
import org.example.deal.exception.DealStatusNotFoundException;
import org.example.deal.exception.DealTypeNotFoundException;
import org.example.deal.mapper.*;
import org.example.deal.repository.*;
import org.example.deal.service.DealService;
import org.example.deal.service.SetMainBorrowerService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

@Service
public class DealServiceImpl implements DealService {

    private static final String CREATE_DEAL_STATUS = "DRAFT";

    private final DealRepository dealRepository;

    private final DealStatusRepository dealStatusRepository;

    private final ContractorRepository contractorRepository;

    private final DealContractorRoleRepository dealContractorRoleRepository;

    private final DealTypeRepository dealTypeRepository;

    private final DealMapper dealMapper;

    private final ContractorRoleMapper contractorRoleMapper;

    private final DealTypeMapper dealTypeMapper;

    private final DealStatusMapper dealStatusMapper;

    private final SetMainBorrowerService setMainBorrowerService;

    public DealServiceImpl(DealRepository dealRepository, DealStatusRepository dealStatusRepository, ContractorRepository contractorRepository, DealContractorRoleRepository dealContractorRoleRepository, DealTypeRepository dealTypeRepository, DealMapper dealMapper, ContractorRoleMapper contractorRoleMapper, DealTypeMapper dealTypeMapper, DealStatusMapper dealStatusMapper, SetMainBorrowerService setMainBorrowerService) {
        this.dealRepository = dealRepository;
        this.dealStatusRepository = dealStatusRepository;
        this.contractorRepository = contractorRepository;
        this.dealContractorRoleRepository = dealContractorRoleRepository;
        this.dealTypeRepository = dealTypeRepository;
        this.dealMapper = dealMapper;
        this.contractorRoleMapper = contractorRoleMapper;
        this.dealTypeMapper = dealTypeMapper;
        this.dealStatusMapper = dealStatusMapper;
        this.setMainBorrowerService = setMainBorrowerService;
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

        DealStatus prevStatus = deal.getStatus();
        deal.setStatus(dealStatus);
        DealDTO result = dealMapper.map(dealRepository.saveAndFlush(deal));

        if (prevStatus.getId().equals("DRAFT") && dealStatus.getId().equals("ACTIVE") ) {
            contractorRepository.findAllByDeal(deal).forEach(contractor -> {
                if (contractorRepository.countAllDealsWithStatusWhereContractorMainBorrower(contractor.getId(), "ACTIVE") == 1) {
                    setMainBorrowerService.setMainBorrower(contractor, true);
                }
            });

            return result;
        }
        if (prevStatus.getId().equals("ACTIVE") && dealStatus.getId().equals("CLOSED")) {
            contractorRepository.findAllByDeal(deal).forEach(contractor -> {
                if (contractorRepository.countAllDealsWithStatusWhereContractorMainBorrower(contractor.getId(), "ACTIVE") == 0) {
                    setMainBorrowerService.setMainBorrower(contractor, false);
                }
            });

            return result;
        }

        return result;
    }

    @Override
    public DealWithContractorsDTO getDealWithContractors(UUID id) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new DealStatusNotFoundException("не найдена сделка с id " + id));

        List<ContractorWithRolesDTO> contractorWithRolesDTOS = contractorRepository.findAllByDeal(deal).stream()
                .filter(Contractor::getIsActive)
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

    @Override
    public List<DealDTO> getDeals(DealSearchRequestDTO dealSearchRequestDTO, Pageable pageable) {
        return dealRepository.findAll(createSpecification(dealSearchRequestDTO), pageable).stream()
                .map(dealMapper::map)
                .toList();
    }

    private DealDTO createNewDeal(Deal deal) {
        deal.setStatus(dealStatusRepository.findById(CREATE_DEAL_STATUS)
                .orElseThrow(() -> new DealStatusNotFoundException("не найден статус " + CREATE_DEAL_STATUS)));
        return dealMapper.map(dealRepository.saveAndFlush(deal));
    }

    private Specification<Deal> createSpecification(DealSearchRequestDTO request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isTrue(root.get("isActive")));
            addEqualPredicate(predicates, root, criteriaBuilder, "id", request.getId());
            addEqualPredicate(predicates, root, criteriaBuilder, "description", request.getDescription());
            addLikePredicate(predicates, root, criteriaBuilder, "agreementNumber", request.getAgreementNumber());
            addDateAfterPredicate(predicates, root, criteriaBuilder, "agreementDate", request.getAgreementDateFrom());
            addDateBeforePredicate(predicates, root, criteriaBuilder, "agreementDate", request.getAgreementDateTo());
            addDateAfterPredicate(predicates, root, criteriaBuilder, "availabilityDate", request.getAvailabilityDateFrom());
            addDateBeforePredicate(predicates, root, criteriaBuilder, "availabilityDate", request.getAvailabilityDateTo());
            addDateAfterPredicate(predicates, root, criteriaBuilder, "closeDate", request.getCloseDateFrom());
            addDateBeforePredicate(predicates, root, criteriaBuilder, "closeDate", request.getCloseDateTo());

            if (request.getSearchField() != null) {
                Join<Deal, Contractor> joinContractor = root.join("contractor");
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(joinContractor.get("contractorId"), "%" + request.getSearchField() + "%"),
                        criteriaBuilder.like(joinContractor.get("name"), "%" + request.getSearchField() + "%"),
                        criteriaBuilder.like(joinContractor.get("inn"), "%" + request.getSearchField() + "%")));

                Join<Contractor, DealContractorRole> joinRole = root.join("dealContractorRole");
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(joinRole.get("id").get("contractorRole").get("id"), "BORROWER"),
                        criteriaBuilder.equal(joinRole.get("id").get("contractorRole").get("id"), "WARRANTY")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addEqualPredicate(List<Predicate> predicates, Root<Deal> root,
                                          CriteriaBuilder criteriaBuilder, String field, Object value) {
        if (value == null) {
            return;
        }

        predicates.add(criteriaBuilder.equal(root.get(field), String.valueOf(value)));
    }

    private static void addLikePredicate(List<Predicate> predicates, Root<Deal> root,
                                         CriteriaBuilder criteriaBuilder, String field, Object value) {
        if (value == null) {
            return;
        }

        predicates.add(criteriaBuilder.like(root.get(field), "%" + value + "%"));
    }

    private static void addDateAfterPredicate(List<Predicate> predicates, Root<Deal> root,
                                               CriteriaBuilder criteriaBuilder, String field, ZonedDateTime date) {
        if (date == null) {
            return;
        }

        predicates.add(criteriaBuilder.greaterThan(root.get(field), date));
    }

    private static void addDateBeforePredicate(List<Predicate> predicates, Root<Deal> root,
                                               CriteriaBuilder criteriaBuilder, String field, ZonedDateTime date) {
        if (date == null) {
            return;
        }

        predicates.add(criteriaBuilder.lessThan(root.get(field), date));
    }

}
