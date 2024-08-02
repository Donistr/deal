package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Этот класс представляет DTO для установки признака наличия сделок, где контрагент является основным заемщиком
 */
@Data
@Builder
public class SetMainBorrowerDTO {

    @JsonProperty("id")
    @Schema(description = "id контрагента", example = "id_test")
    private String id;

    @JsonProperty("active_main_borrower")
    @Schema(description = "признак наличия сделок, где контрагент является основным заемщиком", example = "true")
    private Boolean activeMainBorrower;

}
