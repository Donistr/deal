package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class DealWithContractorsDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("description")
    private String description;

    @JsonProperty("agreement_number")
    private String agreementNumber;

    @JsonProperty("agreement_date")
    private ZonedDateTime agreementDate;

    @JsonProperty("agreement_start_dt")
    private ZonedDateTime agreementStartDate;

    @JsonProperty("availability_date")
    private ZonedDateTime availabilityDate;

    @JsonProperty("type")
    private DealTypeDTO type;

    @JsonProperty("status")
    private DealStatusDTO status;

    @JsonProperty("sum")
    private Double sum;

    @JsonProperty("close_dt")
    private ZonedDateTime closeDate;

    @JsonProperty("contractors")
    private List<ContractorWithRolesDTO> contractors;

}
