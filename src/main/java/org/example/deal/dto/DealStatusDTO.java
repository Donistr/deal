package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.help.DealStatusEnum;

/**
 * Этот класс представляет DTO для статуса сделки
 */
@Data
@Builder
public class DealStatusDTO {

    @JsonProperty("id")
    @Schema(description = "id статуса сделки", example = "DRAFT")
    private DealStatusEnum id;

    @JsonProperty("name")
    @Schema(description = "Название статуса сделки", example = "name_test")
    private String name;

}
