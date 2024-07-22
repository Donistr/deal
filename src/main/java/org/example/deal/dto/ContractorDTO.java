package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.Deal;

import java.util.UUID;

@Data
@Builder
public class ContractorDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("deal")
    private Deal deal;

    @JsonProperty("contractor_id")
    private String contractorId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("inn")
    private String inn;

    @JsonProperty("main")
    private Boolean main;

}
