package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.help.DealStatusEnum;

import java.util.UUID;

@Data
@Builder
public class DealChangeStatusDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("status_id")
    private DealStatusEnum dealStatusId;

}
