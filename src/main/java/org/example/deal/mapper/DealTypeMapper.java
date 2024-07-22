package org.example.deal.mapper;

import org.example.deal.dto.DealTypeDTO;
import org.example.deal.entity.DealType;

public interface DealTypeMapper {

    DealType map(DealTypeDTO dealTypeDTO);

    DealTypeDTO map(DealType dealType);

}
