package org.example.deal.repository;

import org.example.deal.entity.Deal;
import org.example.deal.entity.DealContractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DealContractorRepository extends JpaRepository<DealContractor, UUID> {

    List<DealContractor> findAllByDeal(Deal deal);

}
