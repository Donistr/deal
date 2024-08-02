package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Этот класс представляет DTO для смены роли контрагента
 */
@Data
@Builder
public class DealContractorRoleDTO {

    @JsonProperty("contractor")
    @Schema(description = "Контрагент")
    private ContractorDTO contractor;

    @JsonProperty("role")
    @Schema(description = "Роль контрагента")
    private ContractorRoleDTO contractorRole;

}
