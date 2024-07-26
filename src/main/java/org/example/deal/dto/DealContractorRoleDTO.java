package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DealContractorRoleDTO {

    @JsonProperty("contractor")
    private ContractorDTO contractor;

    @JsonProperty("role")
    private ContractorRoleDTO contractorRole;

}
