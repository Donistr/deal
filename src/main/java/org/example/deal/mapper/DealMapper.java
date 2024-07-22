package org.example.deal.mapper;

import org.example.deal.dto.DealDTO;
import org.example.deal.entity.Deal;

public interface DealMapper {

    Deal map(DealDTO dealDTO);

    DealDTO map(Deal deal);

}
