package org.example.deal.repository;

import org.example.deal.entity.DealContractorRole;
import org.example.deal.entity.help.DealContractorRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealContractorRoleRepository extends JpaRepository<DealContractorRole, DealContractorRoleId> {
}
