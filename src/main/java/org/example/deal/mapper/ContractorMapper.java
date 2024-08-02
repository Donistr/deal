package org.example.deal.mapper;

import org.example.deal.dto.ContractorDTO;
import org.example.deal.entity.Contractor;

/**
 * Класс предназначенный для преобразования {@link Contractor} в {@link ContractorDTO} и наоборот
 */
public interface ContractorMapper {

    /**
     * Преобразует {@link ContractorDTO} в {@link Contractor}
     * @param contractorDTO {@link ContractorDTO}
     * @return {@link Contractor}
     */
    Contractor map(ContractorDTO contractorDTO);

    /**
     * Преобразует {@link Contractor} в {@link ContractorDTO}
     * @param contractor {@link Contractor}
     * @return {@link ContractorDTO}
     */
    ContractorDTO map(Contractor contractor);

}
