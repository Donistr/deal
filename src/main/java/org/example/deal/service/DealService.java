package org.example.deal.service;

import org.example.deal.dto.DealChangeStatusDTO;
import org.example.deal.dto.DealCreateOrUpdateDTO;
import org.example.deal.dto.DealDTO;
import org.example.deal.dto.DealWithContractorsDTO;

import java.util.UUID;

public interface DealService {

    DealDTO save(DealCreateOrUpdateDTO dealCreateOrUpdateDTO);

    DealDTO changeStatus(DealChangeStatusDTO dealChangeStatusDTO);

    DealWithContractorsDTO getDealWithContractors(UUID id);

}
