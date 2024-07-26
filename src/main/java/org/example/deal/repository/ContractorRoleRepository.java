package org.example.deal.repository;

import org.example.deal.entity.ContractorRole;
import org.example.deal.entity.help.ContractorRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContractorRoleRepository extends JpaRepository<ContractorRole, ContractorRoleEnum> {

    Optional<ContractorRole> findByIdAndIsActiveTrue(ContractorRoleEnum id);

}
