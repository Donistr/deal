package org.example.deal.repository;

import org.example.deal.entity.ContractorRole;
import org.example.deal.entity.help.ContractorRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractorRoleRepository extends JpaRepository<ContractorRole, ContractorRoleEnum> {
}
