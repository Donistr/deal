package org.example.deal.service.impl;

import org.example.deal.dto.ContractorDTO;
import org.example.deal.entity.Deal;
import org.example.deal.entity.DealContractor;
import org.example.deal.exception.DealStatusNotFoundException;
import org.example.deal.mapper.ContractorMapper;
import org.example.deal.repository.DealContractorRepository;
import org.example.deal.repository.DealRepository;
import org.example.deal.service.ContractorService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ContractorServiceImpl implements ContractorService {

    private final DealContractorRepository repository;

    private final DealRepository dealRepository;

    private final ContractorMapper mapper;

    public ContractorServiceImpl(DealContractorRepository repository, DealRepository dealRepository, ContractorMapper mapper) {
        this.repository = repository;
        this.dealRepository = dealRepository;
        this.mapper = mapper;
    }

    @Override
    public ContractorDTO createOrUpdate(ContractorDTO contractorDTO) {
        Deal deal = dealRepository.findById(contractorDTO.getDeal().getId())
                .orElseThrow(() -> new DealStatusNotFoundException("не найдена сделка с id " +
                        contractorDTO.getDeal().getId()));
        contractorDTO.setDeal(deal);
        DealContractor contractor = mapper.map(contractorDTO);

        if (contractor.getId() == null) {
            return createNewContractor(contractor);
        }
        Optional<DealContractor> fromDatabaseOptional = repository.findById(contractor.getId());
        if (fromDatabaseOptional.isEmpty()) {
            return createNewContractor(contractor);
        }

        DealContractor fromDatabase = fromDatabaseOptional.get();
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
        repository.deleteById(id);
    }

    private ContractorDTO createNewContractor(DealContractor contractor) {
        if (contractor.getMain() == null) {
            contractor.setMain(false);
        }
        return mapper.map(repository.saveAndFlush(contractor));
    }

}
