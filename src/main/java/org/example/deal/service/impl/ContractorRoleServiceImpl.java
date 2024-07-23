package org.example.deal.service.impl;

import org.example.deal.dto.ContractorChangeRoleDTO;
import org.example.deal.dto.DealContractorDTO;
import org.example.deal.dto.DealContractorRoleDTO;
import org.example.deal.entity.ContractorRole;
import org.example.deal.entity.Deal;
import org.example.deal.entity.DealContractor;
import org.example.deal.entity.DealContractorRole;
import org.example.deal.entity.help.DealContractorRoleId;
import org.example.deal.exception.ContractorRoleNotFoundException;
import org.example.deal.exception.DealContractorNotFoundException;
import org.example.deal.exception.DealNotFoundException;
import org.example.deal.mapper.ContractorRoleMapper;
import org.example.deal.mapper.DealMapper;
import org.example.deal.repository.ContractorRoleRepository;
import org.example.deal.repository.DealContractorRepository;
import org.example.deal.repository.DealContractorRoleRepository;
import org.example.deal.repository.DealRepository;
import org.example.deal.service.ContractorRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractorRoleServiceImpl implements ContractorRoleService {

    private final DealContractorRoleRepository repository;

    private final DealContractorRepository dealContractorRepository;

    private final ContractorRoleRepository contractorRoleRepository;

    private final DealRepository dealRepository;

    private final ContractorRoleMapper contractorRoleMapper;

    private final DealMapper dealMapper;

    public ContractorRoleServiceImpl(DealContractorRoleRepository repository, DealContractorRepository dealContractorRepository, ContractorRoleRepository contractorRoleRepository, DealRepository dealRepository, ContractorRoleMapper contractorRoleMapper, DealMapper dealMapper) {
        this.repository = repository;
        this.dealContractorRepository = dealContractorRepository;
        this.contractorRoleRepository = contractorRoleRepository;
        this.dealRepository = dealRepository;
        this.contractorRoleMapper = contractorRoleMapper;
        this.dealMapper = dealMapper;
    }

    @Override
    public DealContractorRoleDTO createOrUpdate(ContractorChangeRoleDTO contractorChangeRoleDTO) {
        if (contractorChangeRoleDTO.getDealId() == null) {
            throw new DealNotFoundException("id сделки не задано");
        }
        if (contractorChangeRoleDTO.getDealContractorId() == null) {
            throw new DealContractorNotFoundException("id контрагента сделки не задано");
        }
        if (contractorChangeRoleDTO.getRoleId() == null) {
            throw new ContractorRoleNotFoundException("id роли не задано");
        }

        ContractorRole contractorRole = contractorRoleRepository.findById(contractorChangeRoleDTO.getRoleId())
                .orElseThrow(() -> new ContractorRoleNotFoundException("не найдена роль с id " +
                        contractorChangeRoleDTO.getRoleId()));
        Deal deal = dealRepository.findById(contractorChangeRoleDTO.getDealId())
                .orElseThrow(() -> new DealNotFoundException("не найдена сделка с id " +
                        contractorChangeRoleDTO.getRoleId()));
        DealContractor dealContractor = dealContractorRepository
                .findFirstByDealAndContractorId(deal, contractorChangeRoleDTO.getDealContractorId())
                .orElseThrow(() -> new DealContractorNotFoundException("не найден контрагент с id " +
                        contractorChangeRoleDTO.getDealContractorId()));

        DealContractorRole role = DealContractorRole.builder()
                .id(DealContractorRoleId.builder()
                        .contractorRole(contractorRole)
                        .dealContractor(dealContractor)
                        .build())
                .build();
        role = repository.saveAndFlush(role);
        dealContractor = role.getId().getDealContractor();

        return DealContractorRoleDTO.builder()
                .dealContractor(DealContractorDTO.builder()
                        .id(dealContractor.getId())
                        .deal(dealMapper.map(dealContractor.getDeal()))
                        .contractorId(dealContractor.getContractorId())
                        .name(dealContractor.getName())
                        .inn(dealContractor.getName())
                        .main(dealContractor.getMain())
                        .build())
                .contractorRole(contractorRoleMapper.map(role.getId().getContractorRole()))
                .build();
    }

    @Override
    public void delete(ContractorChangeRoleDTO contractorChangeRoleDTO) {
        if (contractorChangeRoleDTO.getDealId() == null) {
            throw new DealNotFoundException("id сделки не задано");
        }
        if (contractorChangeRoleDTO.getDealContractorId() == null) {
            throw new DealContractorNotFoundException("id контрагента сделки не задано");
        }
        if (contractorChangeRoleDTO.getRoleId() == null) {
            throw new ContractorRoleNotFoundException("id роли не задано");
        }

        ContractorRole contractorRole = contractorRoleRepository.findById(contractorChangeRoleDTO.getRoleId())
                .orElseThrow(() -> new ContractorRoleNotFoundException("не найдена роль с id " +
                        contractorChangeRoleDTO.getRoleId()));
        Deal deal = dealRepository.findById(contractorChangeRoleDTO.getDealId())
                .orElseThrow(() -> new DealNotFoundException("не найдена сделка с id " +
                        contractorChangeRoleDTO.getRoleId()));
        DealContractor dealContractor = dealContractorRepository
                .findFirstByDealAndContractorId(deal, contractorChangeRoleDTO.getDealContractorId())
                .orElseThrow(() -> new DealContractorNotFoundException("не найден контрагент с id " +
                        contractorChangeRoleDTO.getDealContractorId()));

        List<DealContractorRole> roles = repository.findAllByDealContractor(dealContractor);
        for (DealContractorRole role : roles) {
            if (role.getId().getContractorRole().equals(contractorRole)) {
                role.setIsActive(false);
                repository.saveAndFlush(role);
            }
        }
    }

}
