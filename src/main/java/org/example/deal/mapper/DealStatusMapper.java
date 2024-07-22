package org.example.deal.mapper;

import org.example.deal.dto.DealStatusDTO;
import org.example.deal.entity.DealStatus;

public interface DealStatusMapper {

    DealStatus map(DealStatusDTO dealStatusDTO);

    DealStatusDTO map(DealStatus dealStatus);

}
