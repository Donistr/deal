package org.example.deal.service.impl;

import org.example.deal.dto.SetMainBorrowerDTO;
import org.example.deal.entity.Contractor;
import org.example.deal.entity.SetMainBorrowerMessage;
import org.example.deal.quartz.ContractorServiceClient;
import org.example.deal.repository.SetMainBorrowerMessageRepository;
import org.example.deal.service.SetMainBorrowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс реализует интерфейс {@link SetMainBorrowerService}
 */
@Service
public class SetMainBorrowerServiceImpl implements SetMainBorrowerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetMainBorrowerServiceImpl.class);

    private final ContractorServiceClient contractorServiceClient;

    private final SetMainBorrowerMessageRepository setMainBorrowerMessageRepository;

    @Autowired
    public SetMainBorrowerServiceImpl(ContractorServiceClient contractorServiceClient, SetMainBorrowerMessageRepository setMainBorrowerMessageRepository) {
        this.contractorServiceClient = contractorServiceClient;
        this.setMainBorrowerMessageRepository = setMainBorrowerMessageRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMainBorrower(Contractor contractor, boolean isMainBorrower) {
        SetMainBorrowerMessage setMainBorrowerMessage = SetMainBorrowerMessage.builder()
                .contractorId(contractor.getContractorId())
                .isActiveMainBorrower(isMainBorrower)
                .isSuccess(false)
                .build();
        setMainBorrowerMessageRepository.saveAndFlush(setMainBorrowerMessage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessages() {
        List<SetMainBorrowerMessage> messages = setMainBorrowerMessageRepository.findAllByIsSuccessFalse();

        Map<String, List<SetMainBorrowerMessage>> messagesMap = new HashMap<>();
        messages.forEach(message -> {
            if (messagesMap.containsKey(message.getContractorId())) {
                messagesMap.get(message.getContractorId()).add(message);
            } else {
                ArrayList<SetMainBorrowerMessage> array = new ArrayList<>();
                array.add(message);

                messagesMap.put(message.getContractorId(), array);
            }
        });

        messagesMap.forEach((contractorId, messagesForContractor) -> {
            SetMainBorrowerMessage message = messagesForContractor.stream()
                    .max(Comparator.comparing(SetMainBorrowerMessage::getCreateDate))
                    .orElse(null);

            if (message != null && sendMessage(message.getContractorId(), message.getIsActiveMainBorrower())) {
                messagesForContractor.forEach(curMessage -> {
                    curMessage.setIsSuccess(true);
                    setMainBorrowerMessageRepository.saveAndFlush(curMessage);
                });
            }
        });
    }

    /**
     * Обращается к сервису контрагентов для выставления признака наличия сделок, где контрагент является основным заемщиком
     * @param contractorId id контрагента
     * @param isMainBorrower признак наличия сделок, где контрагент является основным заемщиком
     * @return удачно или нет
     */
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
        } catch (Exception e) {
            LOGGER.warn("Сообщение isMainBorrower={} не доставлено для контрагента с id={}, ошибка: {}", isMainBorrower,
                    contractorId, e.getMessage());
        }

        return false;
    }

}
