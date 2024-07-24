package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.help.ContractorRoleEnum;

@Data
@Builder
public class ContractorRoleDTO {

    @JsonProperty("id")
    private ContractorRoleEnum id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("category")
    private String category;

}
