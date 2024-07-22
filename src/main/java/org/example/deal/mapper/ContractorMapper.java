package org.example.deal.mapper;

import org.example.deal.dto.ContractorDTO;
import org.example.deal.entity.DealContractor;

public interface ContractorMapper {

    ContractorDTO map(DealContractor dealContractor);

}
