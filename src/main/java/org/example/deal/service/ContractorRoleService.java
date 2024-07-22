package org.example.deal.service;

import org.example.deal.dto.ContractorChangeRoleDTO;
import org.example.deal.entity.ContractorRole;
import org.example.deal.entity.DealContractor;
import org.example.deal.entity.DealContractorRole;
import org.example.deal.entity.help.DealContractorRoleId;
import org.example.deal.exception.ContractorRoleNotFoundException;
import org.example.deal.exception.DealContractorNotFoundException;
import org.example.deal.repository.ContractorRoleRepository;
import org.example.deal.repository.DealContractorRepository;
import org.example.deal.repository.DealContractorRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContractorRoleService {

    private final DealContractorRoleRepository repository;

    private final DealContractorRepository dealContractorRepository;

    private final ContractorRoleRepository contractorRoleRepository;

    @Autowired
    public ContractorRoleService(DealContractorRoleRepository repository, DealContractorRepository dealContractorRepository, ContractorRoleRepository contractorRoleRepository) {
        this.repository = repository;
        this.dealContractorRepository = dealContractorRepository;
        this.contractorRoleRepository = contractorRoleRepository;
    }

    public ContractorChangeRoleDTO createOrUpdate(ContractorChangeRoleDTO contractorChangeRoleDTO) {
        ContractorRole contractorRole = contractorRoleRepository.findById(contractorChangeRoleDTO.getRoleId())
                .orElseThrow(() -> new ContractorRoleNotFoundException("не найдена роль с id " +
                        contractorChangeRoleDTO.getRoleId()));
        DealContractor dealContractor = dealContractorRepository.findById(contractorChangeRoleDTO.getDealContractorId())
                .orElseThrow(() -> new DealContractorNotFoundException("не найден контрагент с id " +
                        contractorChangeRoleDTO.getDealContractorId()));

        DealContractorRole role = DealContractorRole.builder()
                .id(DealContractorRoleId.builder()
                        .contractorRole(contractorRole)
                        .dealContractor(dealContractor)
                        .build())
                .build();
        role = repository.saveAndFlush(role);
        return ContractorChangeRoleDTO.builder()
                .dealContractorId(role.getId().getDealContractor().getId())
                .roleId(role.getId().getContractorRole().getId())
                .build();
    }

    public void delete(ContractorChangeRoleDTO contractorChangeRoleDTO) {
        ContractorRole contractorRole = contractorRoleRepository.findById(contractorChangeRoleDTO.getRoleId())
                .orElseThrow(() -> new ContractorRoleNotFoundException("не найдена роль с id " +
                        contractorChangeRoleDTO.getRoleId()));
        DealContractor dealContractor = dealContractorRepository.findById(contractorChangeRoleDTO.getDealContractorId())
                .orElseThrow(() -> new DealContractorNotFoundException("не найден контрагент с id " +
                        contractorChangeRoleDTO.getDealContractorId()));

        Optional<DealContractorRole> roleOptional = repository.findAllByDealContractor(dealContractor).stream()
                .filter(role -> role.getIsActive() &&
                        role.getId().getDealContractor().getId().equals(contractorChangeRoleDTO.getDealContractorId()) &&
                        role.getId().getContractorRole().equals(contractorRole))
                .findFirst();
        if (roleOptional.isPresent()) {
            DealContractorRole role = roleOptional.get();
            role.setIsActive(false);
            repository.saveAndFlush(role);
        }
    }

}
