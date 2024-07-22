package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ContractorDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("contractor_id")
    private String contractorId;

    @JsonProperty("name")
    private String name;

    @JsonProperty(value = "main", defaultValue = "false")
    private Boolean main;

    @JsonProperty("roles")
    private List<ContractorRoleDTO> roles;

}
