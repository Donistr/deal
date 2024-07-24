package org.example.deal.service.impl;

import org.example.deal.dto.SetMainBorrowerDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.entity.FailedMessage;
import org.example.deal.quartz.ContractorServiceClient;
import org.example.deal.repository.FailedMessageRepository;
import org.example.deal.service.SetMainBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SetMainBorrowerServiceImpl implements SetMainBorrowerService {

    private final ContractorServiceClient contractorServiceClient;

    private final FailedMessageRepository failedMessageRepository;

    @Autowired
    public SetMainBorrowerServiceImpl(ContractorServiceClient contractorServiceClient, FailedMessageRepository failedMessageRepository) {
        this.contractorServiceClient = contractorServiceClient;
        this.failedMessageRepository = failedMessageRepository;
    }

    @Override
    public void setMainBorrower(Contractor contractor, boolean isMainBorrower) {
        if (sendMessage(contractor.getContractorId(), isMainBorrower)) {
            return;
        }

        FailedMessage failedMessage = FailedMessage.builder()
                .contractorId(contractor.getContractorId())
                .isActiveMainBorrower(isMainBorrower)
                .isSuccess(false)
                .build();
        failedMessageRepository.saveAndFlush(failedMessage);
    }

    @Override
    public void resendFailedMessage() {
        Set<FailedMessage> failedMessages = new HashSet<>(failedMessageRepository.findAll().stream()
                .collect(Collectors.toMap(
                        FailedMessage::getContractorId,
                        message -> message,
                        (existing, replacement) -> {
                            if (replacement.getCreateDate().isAfter(existing.getCreateDate())) {
                                return replacement;
                            }
                            return existing;
                        }
                ))
                .values());

        for (FailedMessage message : failedMessages) {
            if (message.getIsSuccess()) {
                return;
            }

            if (sendMessage(message.getContractorId(), message.getIsActiveMainBorrower())) {
                message.setIsSuccess(true);
                failedMessageRepository.saveAndFlush(message);
            }
        }
    }

    private boolean sendMessage(String contractorId, boolean isMainBorrower) {
        SetMainBorrowerDTO request = SetMainBorrowerDTO.builder()
                .id(contractorId)
                .activeMainBorrower(isMainBorrower)
                .build();
        try {
            ResponseEntity<Void> response = contractorServiceClient.setMainBorrower(request);

            if (response.getStatusCode().is2xxSuccessful()) {
                return true;
            }
        } catch (Exception ignored) {
        }

        return false;
    }

}
