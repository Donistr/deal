package org.example.deal.service;

import org.example.deal.dto.ContractorCreateOrUpdateDTO;
import org.example.deal.dto.ContractorDTO;

import java.util.UUID;

public interface ContractorService {

    ContractorDTO createOrUpdate(ContractorCreateOrUpdateDTO contractorDTO);

    void delete(UUID id);

}
