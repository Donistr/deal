package org.example.deal.repository;

import org.example.deal.entity.DealStatus;
import org.example.deal.entity.help.DealStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealStatusRepository extends JpaRepository<DealStatus, DealStatusEnum> {
}
