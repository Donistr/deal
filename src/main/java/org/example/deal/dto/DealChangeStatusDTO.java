package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.help.DealStatusEnum;

import java.util.UUID;

@Data
@Builder
public class DealChangeStatusDTO {

    @JsonProperty("id")
    @Schema(description = "id сделки", example = "01ebc64d-f477-41e7-a300-53871b4f2ada")
    private UUID id;

    @JsonProperty("status_id")
    @Schema(description = "id статуса сделки", example = "DRAFT")
    private DealStatusEnum dealStatusId;

}
