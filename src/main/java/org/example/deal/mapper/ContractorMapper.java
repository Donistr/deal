package org.example.deal.mapper;

import org.example.deal.dto.ContractorDTO;
import org.example.deal.entity.Contractor;

public interface ContractorMapper {

    Contractor map(ContractorDTO contractorDTO);

    ContractorDTO map(Contractor contractor);

}
