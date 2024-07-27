package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.help.ContractorRoleEnum;

import java.util.UUID;

@Data
@Builder
public class ContractorChangeRoleDTO {

    @JsonProperty("contractor_id")
    @Schema(description = "id контрагента", example = "01ebc64d-f477-41e7-a300-53871b4f2ada")
    private UUID contractorId;

    @JsonProperty("role_id")
    @Schema(description = "id роли", example = "BORROWER")
    private ContractorRoleEnum roleId;

}
