package org.example.deal.repository;

import org.example.deal.entity.Deal;
import org.example.deal.entity.Contractor;
import org.example.deal.entity.help.DealStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ContractorRepository extends JpaRepository<Contractor, UUID> {

    Optional<Contractor> findFirstByDealAndContractorId(Deal deal, String contractorId);

    @Query("SELECT COUNT(d) from Deal d " +
            "JOIN Contractor c ON c.deal.id = d.id " +
            "WHERE c.isActive = TRUE " +
            "AND c.id = :id " +
            "AND c.main = TRUE " +
            "AND d.status.id = :dealStatusId")
    long countAllDealsWithStatusWhereContractorMainBorrower(UUID id, DealStatusEnum dealStatusId);

}
