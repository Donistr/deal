package org.example.deal.service.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.auth.role.RoleEnum;
import org.example.deal.dto.DealChangeStatusDTO;
import org.example.deal.dto.DealCreateOrUpdateDTO;
import org.example.deal.dto.DealDTO;
import org.example.deal.dto.DealSearchRequestDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.entity.Deal;
import org.example.deal.entity.DealContractorRole;
import org.example.deal.entity.DealStatus;
import org.example.deal.entity.DealType;
import org.example.deal.entity.help.DealStatusEnum;
import org.example.deal.entity.help.DealTypeEnum;
import org.example.deal.exception.DealNotFoundException;
import org.example.deal.exception.DealStatusNotFoundException;
import org.example.deal.exception.DealTypeNotFoundException;
import org.example.deal.mapper.DealMapper;
import org.example.deal.repository.ContractorRepository;
import org.example.deal.repository.DealRepository;
import org.example.deal.repository.DealStatusRepository;
import org.example.deal.repository.DealTypeRepository;
import org.example.deal.service.DealService;
import org.example.deal.service.SetMainBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Класс реализует интерфейс {@link DealService}
 */
@Service
public class DealServiceImpl implements DealService {

    private static final GrantedAuthority SUPERUSER_AUTHORITY = new SimpleGrantedAuthority(RoleEnum.SUPERUSER.getValue());

    private static final GrantedAuthority DEAL_SUPERUSER_AUTHORITY = new SimpleGrantedAuthority(RoleEnum.DEAL_SUPERUSER.getValue());

    private static final GrantedAuthority CREDIT_USER_AUTHORITY = new SimpleGrantedAuthority(RoleEnum.CREDIT_USER.getValue());

    private static final GrantedAuthority OVERDRAFT_USER_AUTHORITY = new SimpleGrantedAuthority(RoleEnum.OVERDRAFT_USER.getValue());

    private final DealRepository dealRepository;

    private final DealStatusRepository dealStatusRepository;

    private final ContractorRepository contractorRepository;

    private final DealTypeRepository dealTypeRepository;

    private final DealMapper dealMapper;

    private final SetMainBorrowerService setMainBorrowerService;

    @Autowired
    public DealServiceImpl(DealRepository dealRepository, DealStatusRepository dealStatusRepository,
                           ContractorRepository contractorRepository, DealTypeRepository dealTypeRepository,
                           DealMapper dealMapper, SetMainBorrowerService setMainBorrowerService) {
        this.dealRepository = dealRepository;
        this.dealStatusRepository = dealStatusRepository;
        this.contractorRepository = contractorRepository;
        this.dealTypeRepository = dealTypeRepository;
        this.dealMapper = dealMapper;
        this.setMainBorrowerService = setMainBorrowerService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DealDTO createOrUpdate(DealCreateOrUpdateDTO dealCreateOrUpdateDTO) {
        if (dealCreateOrUpdateDTO.getTypeId() == null) {
            throw new DealTypeNotFoundException("тип сделки не задан");
        }
        DealType dealType = dealTypeRepository.findByIdAndIsActiveTrue(dealCreateOrUpdateDTO.getTypeId())
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
                .sum(dealCreateOrUpdateDTO.getSum())
                .closeDate(dealCreateOrUpdateDTO.getCloseDate())
                .build();

        if (deal.getId() == null) {
            return createNewDeal(deal);
        }
        Optional<Deal> fromDatabaseOptional = dealRepository.findByIdAndIsActiveTrue(deal.getId());
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

        return filterNotActiveContractors(dealRepository.saveAndFlush(fromDatabase));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DealDTO changeStatus(DealChangeStatusDTO dealChangeStatusDTO) {
        DealStatus dealStatus = dealStatusRepository.findByIdAndIsActiveTrue(dealChangeStatusDTO.getDealStatusId())
                .orElseThrow(() -> new DealStatusNotFoundException("не найден статус с id " +
                        dealChangeStatusDTO.getDealStatusId()));
        Deal deal = dealRepository.findByIdAndIsActiveTrue(dealChangeStatusDTO.getId())
                .orElseThrow(() -> new DealNotFoundException("не найдена сделка с id " + dealChangeStatusDTO.getId()));

        DealStatus prevStatus = deal.getStatus();
        deal.setStatus(dealStatus);
        DealDTO result = filterNotActiveContractors(dealRepository.saveAndFlush(deal));
        if (prevStatus.getId() == DealStatusEnum.DRAFT && dealStatus.getId() == DealStatusEnum.ACTIVE) {
            deal.getContractors().forEach(contractor -> {
                if (contractorRepository.countAllDealsWithStatusWhereContractorMainBorrower(contractor.getId(), DealStatusEnum.ACTIVE) == 1) {
                    setMainBorrowerService.setMainBorrower(contractor, true);
                }
            });

            return result;
        }
        if (prevStatus.getId() == DealStatusEnum.ACTIVE && dealStatus.getId() == DealStatusEnum.DRAFT) {
            deal.getContractors().forEach(contractor -> {
                if (contractorRepository.countAllDealsWithStatusWhereContractorMainBorrower(contractor.getId(), DealStatusEnum.ACTIVE) == 0) {
                    setMainBorrowerService.setMainBorrower(contractor, false);
                }
            });

            return result;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DealDTO getDealWithContractors(UUID id) {
        return filterNotActiveContractors(dealRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new DealNotFoundException("не найдена сделка с id " + id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DealDTO> getDeals(DealSearchRequestDTO dealSearchRequestDTO, Pageable pageable) {
        return dealRepository.findAll(createSpecification(dealSearchRequestDTO), pageable).stream()
                .map(this::filterNotActiveContractors)
                .toList();
    }

    /**
     * Создаёт новую сделку
     * @param deal сделка
     * @return сделка
     */
    private DealDTO createNewDeal(Deal deal) {
        deal.setStatus(dealStatusRepository.findByIdAndIsActiveTrue(DealStatusEnum.DRAFT)
                .orElseThrow(() -> new DealStatusNotFoundException("не найден статус " + DealStatusEnum.DRAFT)));
        return dealMapper.map(dealRepository.saveAndFlush(deal));
    }

    /**
     * Убирает из сделки контрагентов по полю isActive
     * @param deal сделка
     * @return сделка
     */
    private DealDTO filterNotActiveContractors(Deal deal) {
        deal.setContractors(deal.getContractors().stream()
                .filter(Contractor::getIsActive)
                .toList());
        for (Contractor contractor : deal.getContractors()) {
            contractor.setRoles(contractor.getRoles().stream()
                    .filter(DealContractorRole::getIsActive)
                    .toList());
        }

        return dealMapper.map(deal);
    }

    /**
     * Создаёт спецификацию в соответствии с запросом
     * @param request запрос
     * @return спецификация
     */
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
            addContainsPredicate(predicates, criteriaBuilder, root.get("status").get("id"), request.getStatusIds());

            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            List<DealTypeEnum> types = new ArrayList<>();
            if (authorities.contains(CREDIT_USER_AUTHORITY)) {
                types.add(DealTypeEnum.CREDIT);
            }
            if (authorities.contains(OVERDRAFT_USER_AUTHORITY)) {
                types.add(DealTypeEnum.OVERDRAFT);
            }
            if (authorities.contains(DEAL_SUPERUSER_AUTHORITY) || authorities.contains(SUPERUSER_AUTHORITY)) {
                types = request.getTypeIds();
            }
            System.out.println(request.getTypeIds());
            System.out.println(types);
            addContainsPredicate(predicates, criteriaBuilder, root.get("type").get("id"), types);

            if (request.getSearchField() != null) {
                Join<Deal, Contractor> joinContractor = root.join("contractors");
                predicates.add(
                        criteriaBuilder.and(
                                criteriaBuilder.isTrue(joinContractor.get("isActive")),
                                criteriaBuilder.or(
                                        criteriaBuilder.like(joinContractor.get("contractorId"), "%" + request.getSearchField() + "%"),
                                        criteriaBuilder.like(joinContractor.get("name"), "%" + request.getSearchField() + "%"),
                                        criteriaBuilder.like(joinContractor.get("inn"), "%" + request.getSearchField() + "%")
                                )
                        )
                );

                Join<Contractor, DealContractorRole> joinRole = joinContractor.join("roles");
                predicates.add(
                        criteriaBuilder.and(
                                criteriaBuilder.isTrue(joinRole.get("isActive")),
                                criteriaBuilder.or(
                                        criteriaBuilder.equal(joinRole.get("id").get("contractorRole").get("id"), "BORROWER"),
                                        criteriaBuilder.equal(joinRole.get("id").get("contractorRole").get("id"), "WARRANTY")
                                )
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Добавляет предикат equals в запрос
     * @param predicates предикаты
     * @param root root
     * @param criteriaBuilder criteriaBuilder
     * @param field поле
     * @param value значение
     */
    private static void addEqualPredicate(List<Predicate> predicates, Root<Deal> root,
                                          CriteriaBuilder criteriaBuilder, String field, Object value) {
        if (value == null) {
            return;
        }

        predicates.add(criteriaBuilder.equal(root.get(field), value));
    }

    /**
     * Добавляет предикат like в запрос
     * @param predicates предикаты
     * @param root root
     * @param criteriaBuilder criteriaBuilder
     * @param field поле
     * @param value значение
     */
    private static void addLikePredicate(List<Predicate> predicates, Root<Deal> root,
                                         CriteriaBuilder criteriaBuilder, String field, Object value) {
        if (value == null) {
            return;
        }

        predicates.add(criteriaBuilder.like(root.get(field), "%" + value + "%"));
    }

    /**
     * Добавляет предикат greaterThan в запрос
     * @param predicates предикаты
     * @param root root
     * @param criteriaBuilder criteriaBuilder
     * @param field поле
     * @param date дата
     */
    private static void addDateAfterPredicate(List<Predicate> predicates, Root<Deal> root,
                                               CriteriaBuilder criteriaBuilder, String field, LocalDateTime date) {
        if (date == null) {
            return;
        }

        predicates.add(criteriaBuilder.greaterThan(root.get(field), date));
    }

    /**
     * Добавляет предикат lessThan в запрос
     * @param predicates предикаты
     * @param root root
     * @param criteriaBuilder criteriaBuilder
     * @param field поле
     * @param date дата
     */
    private static void addDateBeforePredicate(List<Predicate> predicates, Root<Deal> root,
                                               CriteriaBuilder criteriaBuilder, String field, LocalDateTime date) {
        if (date == null) {
            return;
        }

        predicates.add(criteriaBuilder.lessThan(root.get(field), date));
    }

    /**
     * Добавляет предикат in в запрос
     * @param predicates предикаты
     * @param criteriaBuilder criteriaBuilder
     * @param field поле
     * @param values список значений
     */
    private static void addContainsPredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder,
                                             Path<?> field, List<?> values) {
        if (values == null || values.isEmpty()) {
            return;
        }

        CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(field);
        for (Object value : values) {
            inClause.value(value);
        }
        predicates.add(inClause);
    }

}
