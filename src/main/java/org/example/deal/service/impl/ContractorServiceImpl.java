package org.example.deal.service.impl;

import org.example.deal.dto.ContractorCreateOrUpdateDTO;
import org.example.deal.dto.ContractorDTO;
import org.example.deal.entity.Deal;
import org.example.deal.entity.Contractor;
import org.example.deal.exception.DealStatusNotFoundException;
import org.example.deal.mapper.ContractorMapper;
import org.example.deal.repository.ContractorRepository;
import org.example.deal.repository.DealRepository;
import org.example.deal.service.ContractorService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository repository;

    private final DealRepository dealRepository;

    private final ContractorMapper mapper;

    public ContractorServiceImpl(ContractorRepository repository, DealRepository dealRepository, ContractorMapper mapper) {
        this.repository = repository;
        this.dealRepository = dealRepository;
        this.mapper = mapper;
    }

    @Override
    public ContractorDTO createOrUpdate(ContractorCreateOrUpdateDTO contractorDTO) {
        if (contractorDTO.getDealId() == null) {
            throw new DealStatusNotFoundException("id сделки не указано");
        }
        Deal deal = dealRepository.findById(contractorDTO.getDealId())
                .orElseThrow(() -> new DealStatusNotFoundException("не найдена сделка с id " +
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
        Optional<Contractor> fromDatabaseOptional = repository.findById(contractor.getId());
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
        return mapper.map(repository.saveAndFlush(fromDatabase));
    }

    @Override
    public void delete(UUID id) {
        Optional<Contractor> fromDatabaseOptional = repository.findById(id);
        if (fromDatabaseOptional.isPresent()) {
            Contractor fromDatabase = fromDatabaseOptional.get();
            fromDatabase.setIsActive(false);
            repository.saveAndFlush(fromDatabase);
        }
    }

    private ContractorDTO createNewContractor(Contractor contractor) {
        if (contractor.getMain() == null) {
            contractor.setMain(false);
        }
        return mapper.map(repository.saveAndFlush(contractor));
    }

}
