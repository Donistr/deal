package org.example.deal.service;

import org.example.deal.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface DealService {

    DealDTO createOrUpdate(DealCreateOrUpdateDTO dealCreateOrUpdateDTO);

    DealDTO changeStatus(DealChangeStatusDTO dealChangeStatusDTO);

    DealDTO getDealWithContractors(UUID id);

    List<DealDTO> getDeals(DealSearchRequestDTO dealSearchRequestDTO, Pageable pageable);

}
