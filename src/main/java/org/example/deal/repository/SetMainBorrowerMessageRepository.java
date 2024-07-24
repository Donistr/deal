package org.example.deal.repository;

import org.example.deal.entity.SetMainBorrowerMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetMainBorrowerMessageRepository extends JpaRepository<SetMainBorrowerMessage, Long> {

    List<SetMainBorrowerMessage> findAllByIsSuccessFalse();

}
