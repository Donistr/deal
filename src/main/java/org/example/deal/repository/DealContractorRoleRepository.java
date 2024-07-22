package org.example.deal.repository;

import org.example.deal.entity.DealContractor;
import org.example.deal.entity.DealContractorRole;
import org.example.deal.entity.help.DealContractorRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealContractorRoleRepository extends JpaRepository<DealContractorRole, DealContractorRoleId> {

    List<DealContractorRole> findAllByDealContractor(DealContractor dealContractor);

}
