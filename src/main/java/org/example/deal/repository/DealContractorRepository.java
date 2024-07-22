package org.example.deal.repository;

import org.example.deal.entity.DealContractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DealContractorRepository extends JpaRepository<DealContractor, UUID> {
}
