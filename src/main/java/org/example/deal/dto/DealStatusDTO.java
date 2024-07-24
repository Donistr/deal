package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.help.DealStatusEnum;

@Data
@Builder
public class DealStatusDTO {

    @JsonProperty("id")
    private DealStatusEnum id;

    @JsonProperty("name")
    private String name;

}
