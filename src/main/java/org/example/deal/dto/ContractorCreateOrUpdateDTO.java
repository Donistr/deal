package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ContractorCreateOrUpdateDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("deal_id")
    private UUID dealId;

    @JsonProperty("contractor_id")
    private String contractorId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("inn")
    private String inn;

    @JsonProperty("main")
    private Boolean main;

}
