package org.example.deal.mapper;

import org.example.deal.dto.DealCreateOrUpdateDTO;
import org.example.deal.entity.Deal;

public interface DealCreateOrUpdateMapper {

    Deal map(DealCreateOrUpdateDTO dealCreateOrUpdateDTO);

    DealCreateOrUpdateDTO map(Deal deal);

}
