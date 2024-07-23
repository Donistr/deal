package org.example.deal.repository;

import org.example.deal.entity.Deal;
import org.example.deal.entity.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractorRepository extends JpaRepository<Contractor, UUID> {

    List<Contractor> findAllByDeal(Deal deal);

    Optional<Contractor> findFirstByDealAndContractorId(Deal deal, String contractorId);

}
