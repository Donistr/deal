package org.example.deal.service.impl;

import org.example.deal.dto.ContractorCreateOrUpdateDTO;
import org.example.deal.dto.ContractorDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.entity.Deal;
import org.example.deal.entity.DealContractorRole;
import org.example.deal.entity.help.DealStatusEnum;
import org.example.deal.exception.DealNotFoundException;
import org.example.deal.mapper.ContractorMapper;
import org.example.deal.repository.ContractorRepository;
import org.example.deal.repository.DealContractorRoleRepository;
import org.example.deal.repository.DealRepository;
import org.example.deal.service.ContractorService;
import org.example.deal.service.SetMainBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Класс реализует интерфейс {@link ContractorService}
 */
@Service
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository contractorRepository;

    private final DealRepository dealRepository;

    private final DealContractorRoleRepository dealContractorRoleRepository;

    private final ContractorMapper contractorMapper;

    private final SetMainBorrowerService setMainBorrowerService;

    @Autowired
    public ContractorServiceImpl(ContractorRepository contractorRepository, DealRepository dealRepository,
                                 DealContractorRoleRepository dealContractorRoleRepository,
                                 ContractorMapper contractorMapper, SetMainBorrowerService setMainBorrowerService) {
        this.contractorRepository = contractorRepository;
        this.dealRepository = dealRepository;
        this.dealContractorRoleRepository = dealContractorRoleRepository;
        this.contractorMapper = contractorMapper;
        this.setMainBorrowerService = setMainBorrowerService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContractorDTO createOrUpdate(ContractorCreateOrUpdateDTO contractorDTO) {
        if (contractorDTO.getDealId() == null) {
            throw new DealNotFoundException("id сделки не указано");
        }
        Deal deal = dealRepository.findByIdAndIsActiveTrue(contractorDTO.getDealId())
                .orElseThrow(() -> new DealNotFoundException("не найдена сделка с id " +
                        contractorDTO.getDealId()));
        Contractor contractor = Contractor.builder()
                .id(contractorDTO.getId())
                .deal(deal)
                .contractorId(contractorDTO.getContractorId())
                .name(contractorDTO.getName())
                .inn(contractorDTO.getInn())
                .main(contractorDTO.getMain())
                .build();

        if (contractor.getId() == null) {
            return createNewContractor(contractor);
        }
        Optional<Contractor> fromDatabaseOptional = contractorRepository.findByIdAndIsActiveTrue(contractor.getId());
        if (fromDatabaseOptional.isEmpty()) {
            return createNewContractor(contractor);
        }

        Contractor fromDatabase = fromDatabaseOptional.get();
        if (contractor.getDeal() != null) {
            fromDatabase.setDeal(contractor.getDeal());
        }
        if (contractor.getContractorId() != null) {
            fromDatabase.setContractorId(contractor.getContractorId());
        }
        if (contractor.getName() != null) {
            fromDatabase.setName(contractor.getName());
        }
        if (contractor.getInn() != null) {
            fromDatabase.setInn(contractor.getInn());
        }
        if (contractor.getMain() != null) {
            fromDatabase.setMain(contractorDTO.getMain());
        }

        fromDatabase = contractorRepository.saveAndFlush(fromDatabase);
        fromDatabase.setRoles(fromDatabase.getRoles().stream()
                .filter(DealContractorRole::getIsActive)
                .toList());
        return contractorMapper.map(fromDatabase);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(UUID id) {
        Optional<Contractor> fromDatabaseOptional = contractorRepository.findByIdAndIsActiveTrue(id);
        if (fromDatabaseOptional.isPresent()) {
            Contractor fromDatabase = fromDatabaseOptional.get();
            fromDatabase.setIsActive(false);
            fromDatabase.getRoles().forEach(role -> role.setIsActive(false));
            dealContractorRoleRepository.saveAll(fromDatabase.getRoles());
            contractorRepository.saveAndFlush(fromDatabase);

            if (contractorRepository.countAllDealsWithStatusWhereContractorMainBorrower(id, DealStatusEnum.ACTIVE) == 0) {
                setMainBorrowerService.setMainBorrower(fromDatabase, false);
            }
        }
    }

    private ContractorDTO createNewContractor(Contractor contractor) {
        if (contractor.getMain() == null) {
            contractor.setMain(false);
        }
        return contractorMapper.map(contractorRepository.saveAndFlush(contractor));
    }

}
