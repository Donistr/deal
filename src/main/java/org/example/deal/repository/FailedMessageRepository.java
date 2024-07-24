package org.example.deal.repository;

import org.example.deal.entity.FailedMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedMessageRepository extends JpaRepository<FailedMessage, Long> {
}
