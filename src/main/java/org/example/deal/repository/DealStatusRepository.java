package org.example.deal.repository;

import org.example.deal.entity.DealStatus;
import org.example.deal.entity.help.DealStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DealStatusRepository extends JpaRepository<DealStatus, DealStatusEnum> {

    Optional<DealStatus> findByIdAndIsActiveTrue(DealStatusEnum id);

}
