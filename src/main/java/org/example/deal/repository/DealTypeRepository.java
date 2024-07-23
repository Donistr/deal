package org.example.deal.repository;

import org.example.deal.entity.DealType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealTypeRepository extends JpaRepository<DealType, String> {
}