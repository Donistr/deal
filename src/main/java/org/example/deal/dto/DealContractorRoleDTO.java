package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DealContractorRoleDTO {

    @JsonProperty("deal_contractor")
    private DealContractorDTO dealContractor;

    @JsonProperty("role")
    private ContractorRoleDTO contractorRole;

}
