package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.help.ContractorRoleEnum;

@Data
@Builder
public class ContractorRoleDTO {

    @JsonProperty("id")
    @Schema(description = "id роли", example = "BORROWER")
    private ContractorRoleEnum id;

    @JsonProperty("name")
    @Schema(description = "Название роли", example = "Заемщик")
    private String name;

    @JsonProperty("category")
    @Schema(description = "Категория", example = "BORROWER")
    private String category;

}
