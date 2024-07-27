package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.help.DealTypeEnum;

@Data
@Builder
public class DealTypeDTO {

    @JsonProperty("id")
    @Schema(description = "id типа сделки", example = "CREDIT")
    private DealTypeEnum id;

    @JsonProperty("name")
    @Schema(description = "Название типа сделки", example = "name_test")
    private String name;

}
