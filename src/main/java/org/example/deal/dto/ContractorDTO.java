package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ContractorDTO {

    @JsonProperty("id")
    @Schema(description = "id контрагента", example = "01ebc64d-f477-41e7-a300-53871b4f2ada")
    private UUID id;

    @JsonProperty("contractor_id")
    @Schema(description = "id контрагента", example = "id_test")
    private String contractorId;

    @JsonProperty("name")
    @Schema(description = "Название контрагента", example = "name_test")
    private String name;

    @JsonProperty("inn")
    @Schema(description = "ИНН контрагента", example = "inn_test")
    private String inn;

    @JsonProperty("main")
    @Schema(description = "Признак наличия сделок, где контрагент является основным заемщиком", example = "true")
    private Boolean main;

    @JsonProperty("roles")
    @Schema(description = "Список ролей контрагента")
    private List<ContractorRoleDTO> roles;

}
