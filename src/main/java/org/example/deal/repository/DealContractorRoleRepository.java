package org.example.deal.repository;

import org.example.deal.entity.DealContractor;
import org.example.deal.entity.DealContractorRole;
import org.example.deal.entity.help.DealContractorRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DealContractorRoleRepository extends JpaRepository<DealContractorRole, DealContractorRoleId> {

    @Query("SELECT d FROM DealContractorRole d WHERE d.id.dealContractor = :dealContractor")
    List<DealContractorRole> findAllByDealContractor(DealContractor dealContractor);

}
