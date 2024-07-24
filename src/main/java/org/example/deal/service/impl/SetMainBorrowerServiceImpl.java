package org.example.deal.service.impl;

import org.example.auditlib.methodlog.AuditLog;
import org.example.deal.dto.SetMainBorrowerDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.entity.SetMainBorrowerMessage;
import org.example.deal.quartz.ContractorServiceClient;
import org.example.deal.repository.SetMainBorrowerMessageRepository;
import org.example.deal.service.SetMainBorrowerService;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SetMainBorrowerServiceImpl implements SetMainBorrowerService {

    private final ContractorServiceClient contractorServiceClient;

    private final SetMainBorrowerMessageRepository setMainBorrowerMessageRepository;

    @Autowired
    public SetMainBorrowerServiceImpl(ContractorServiceClient contractorServiceClient, SetMainBorrowerMessageRepository setMainBorrowerMessageRepository) {
        this.contractorServiceClient = contractorServiceClient;
        this.setMainBorrowerMessageRepository = setMainBorrowerMessageRepository;
    }

    @Override
    public void setMainBorrower(Contractor contractor, boolean isMainBorrower) {
        SetMainBorrowerMessage setMainBorrowerMessage = SetMainBorrowerMessage.builder()
                .contractorId(contractor.getContractorId())
                .isActiveMainBorrower(isMainBorrower)
                .isSuccess(false)
                .build();
        setMainBorrowerMessageRepository.saveAndFlush(setMainBorrowerMessage);
    }

    @Override
    public void sendMessages() {
        List<SetMainBorrowerMessage> messages = setMainBorrowerMessageRepository.findAllByIsSuccessFalse();

        Map<String, List<SetMainBorrowerMessage>> messagesMap = new HashMap<>();
        messages.forEach(message -> {
            if (!messagesMap.containsKey(message.getContractorId())) {
                messagesMap.put(message.getContractorId(), new ArrayList<>());
            }

            messagesMap.get(message.getContractorId()).add(message);
        });

        messagesMap.forEach((contractorId, messagesForContractor) -> {
            SetMainBorrowerMessage message = messagesForContractor.stream()
                    .max(Comparator.comparing(SetMainBorrowerMessage::getCreateDate))
                    .orElse(null);

            if (message != null) {
                if (sendMessage(message.getContractorId(), message.getIsActiveMainBorrower())) {
                    messagesForContractor.forEach(curMessage -> {
                        curMessage.setIsSuccess(true);
                        setMainBorrowerMessageRepository.saveAndFlush(curMessage);
                    });
                }
            }
        });
    }

    @AuditLog(logLevel = Level.INFO)
    protected boolean sendMessage(String contractorId, boolean isMainBorrower) {
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
