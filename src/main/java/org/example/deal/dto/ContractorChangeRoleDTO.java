package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.help.ContractorRoleEnum;

import java.util.UUID;

@Data
@Builder
public class ContractorChangeRoleDTO {

    @JsonProperty("deal_id")
    private UUID dealId;

    @JsonProperty("contractor_id")
    private UUID contractorId;

    @JsonProperty("role_id")
    private ContractorRoleEnum roleId;

}
