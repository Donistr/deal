package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.help.DealTypeEnum;

@Data
@Builder
public class DealTypeDTO {

    @JsonProperty("id")
    private DealTypeEnum id;

    @JsonProperty("name")
    private String name;

}
