package org.example.deal.repository;

import org.example.deal.entity.Deal;
import org.example.deal.entity.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractorRepository extends JpaRepository<Contractor, UUID> {

    List<Contractor> findAllByDeal(Deal deal);

    Optional<Contractor> findFirstByDealAndContractorId(Deal deal, String contractorId);

    @Query("SELECT COUNT(d) from Deal d " +
            "JOIN Contractor c ON c.deal.id = d.id " +
            "WHERE c.isActive = TRUE " +
            "AND c.contractorId = :id " +
            "AND c.main = TRUE " +
            "AND d.status.id = :dealStatusId")
    long countAllDealsWithStatusWhereContractorMainBorrower(UUID id, String dealStatusId);

}
