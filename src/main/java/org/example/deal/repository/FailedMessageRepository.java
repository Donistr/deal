package org.example.deal.repository;

import org.example.deal.entity.FailedMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FailedMessageRepository extends JpaRepository<FailedMessage, Long> {

    List<FailedMessage> findAllByContractorIdAndIsSuccessTrue(String contractorId);

}
