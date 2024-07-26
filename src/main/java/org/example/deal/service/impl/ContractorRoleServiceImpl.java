package org.example.deal.service.impl;

import org.example.deal.dto.ContractorChangeRoleDTO;
import org.example.deal.dto.ContractorDTO;
import org.example.deal.dto.DealContractorRoleDTO;
import org.example.deal.entity.ContractorRole;
import org.example.deal.entity.Deal;
import org.example.deal.entity.Contractor;
import org.example.deal.entity.DealContractorRole;
import org.example.deal.entity.help.DealContractorRoleId;
import org.example.deal.exception.ContractorRoleNotFoundException;
import org.example.deal.exception.ContractorNotFoundException;
import org.example.deal.exception.DealNotFoundException;
import org.example.deal.mapper.ContractorRoleMapper;
import org.example.deal.repository.ContractorRoleRepository;
import org.example.deal.repository.ContractorRepository;
import org.example.deal.repository.DealContractorRoleRepository;
import org.example.deal.repository.DealRepository;
import org.example.deal.service.ContractorRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractorRoleServiceImpl implements ContractorRoleService {

    private final DealContractorRoleRepository dealContractorRoleRepository;

    private final ContractorRepository contractorRepository;

    private final ContractorRoleRepository contractorRoleRepository;

    private final DealRepository dealRepository;

    private final ContractorRoleMapper contractorRoleMapper;

    @Autowired
    public ContractorRoleServiceImpl(DealContractorRoleRepository dealContractorRoleRepository,
                                     ContractorRepository contractorRepository,
                                     ContractorRoleRepository contractorRoleRepository,
                                     DealRepository dealRepository,
                                     ContractorRoleMapper contractorRoleMapper) {
        this.dealContractorRoleRepository = dealContractorRoleRepository;
        this.contractorRepository = contractorRepository;
        this.contractorRoleRepository = contractorRoleRepository;
        this.dealRepository = dealRepository;
        this.contractorRoleMapper = contractorRoleMapper;
    }

    @Override
    public DealContractorRoleDTO createOrUpdate(ContractorChangeRoleDTO contractorChangeRoleDTO) {
        if (contractorChangeRoleDTO.getDealId() == null) {
            throw new DealNotFoundException("id сделки не задано");
        }
        if (contractorChangeRoleDTO.getContractorId() == null) {
            throw new ContractorNotFoundException("id контрагента сделки не задано");
        }
        if (contractorChangeRoleDTO.getRoleId() == null) {
            throw new ContractorRoleNotFoundException("id роли не задано");
        }

        ContractorRole contractorRole = contractorRoleRepository.findByIdAndIsActiveTrue(contractorChangeRoleDTO.getRoleId())
                .orElseThrow(() -> new ContractorRoleNotFoundException("не найдена роль с id " +
                        contractorChangeRoleDTO.getRoleId()));
        Deal deal = dealRepository.findByIdAndIsActiveTrue(contractorChangeRoleDTO.getDealId())
                .orElseThrow(() -> new DealNotFoundException("не найдена сделка с id " +
                        contractorChangeRoleDTO.getDealId()));
        Contractor contractor = contractorRepository
                .findFirstByIdAndDealAndIsActiveTrue(contractorChangeRoleDTO.getContractorId(), deal)
                .orElseThrow(() -> new ContractorNotFoundException("не найден контрагент с id " +
                        contractorChangeRoleDTO.getContractorId()));

        DealContractorRole role = DealContractorRole.builder()
                .id(DealContractorRoleId.builder()
                        .contractorRole(contractorRole)
                        .contractor(contractor)
                        .build())
                .isActive(true)
                .build();
        role = dealContractorRoleRepository.saveAndFlush(role);
        contractor = role.getId().getContractor();

        return DealContractorRoleDTO.builder()
                .contractor(ContractorDTO.builder()
                        .id(contractor.getId())
                        .contractorId(contractor.getContractorId())
                        .name(contractor.getName())
                        .inn(contractor.getName())
                        .main(contractor.getMain())
                        .build())
                .contractorRole(contractorRoleMapper.map(role.getId().getContractorRole()))
                .build();
    }

    @Override
    public void delete(ContractorChangeRoleDTO contractorChangeRoleDTO) {
        if (contractorChangeRoleDTO.getDealId() == null) {
            throw new DealNotFoundException("id сделки не задано");
        }
        if (contractorChangeRoleDTO.getContractorId() == null) {
            throw new ContractorNotFoundException("id контрагента сделки не задано");
        }
        if (contractorChangeRoleDTO.getRoleId() == null) {
            throw new ContractorRoleNotFoundException("id роли не задано");
        }

        ContractorRole contractorRole = contractorRoleRepository.findByIdAndIsActiveTrue(contractorChangeRoleDTO.getRoleId())
                .orElseThrow(() -> new ContractorRoleNotFoundException("не найдена роль с id " +
                        contractorChangeRoleDTO.getRoleId()));
        Deal deal = dealRepository.findByIdAndIsActiveTrue(contractorChangeRoleDTO.getDealId())
                .orElseThrow(() -> new DealNotFoundException("не найдена сделка с id " +
                        contractorChangeRoleDTO.getRoleId()));
        Contractor contractor = contractorRepository
                .findFirstByIdAndDealAndIsActiveTrue(contractorChangeRoleDTO.getContractorId(), deal)
                .orElseThrow(() -> new ContractorNotFoundException("не найден контрагент с id " +
                        contractorChangeRoleDTO.getContractorId()));

        contractor.getRoles().forEach(role -> {
            if (role.getId().getContractorRole().equals(contractorRole)) {
                role.setIsActive(false);
                dealContractorRoleRepository.saveAndFlush(role);
            }
        });
    }

}
