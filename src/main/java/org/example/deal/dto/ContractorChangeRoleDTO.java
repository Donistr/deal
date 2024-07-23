package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ContractorChangeRoleDTO {

    @JsonProperty("deal_contractor_id")
    private UUID dealContractorId;

    @JsonProperty("role_id")
    private String roleId;

}