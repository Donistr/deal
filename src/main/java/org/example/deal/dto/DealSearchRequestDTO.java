package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class DealSearchRequestDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("description")
    private String description;

    @JsonProperty("agreement_number")
    private String agreementNumber;

    @JsonProperty("agreement_date_from")
    private ZonedDateTime agreementDateFrom;

    @JsonProperty("agreement_date_to")
    private ZonedDateTime agreementDateTo;

    @JsonProperty("availability_date_from")
    private ZonedDateTime availabilityDateFrom;

    @JsonProperty("availability_date_to")
    private ZonedDateTime availabilityDateTo;

    @JsonProperty("type")
    private List<String> typeIds;

    @JsonProperty("status")
    private List<String> statusIds;

    @JsonProperty("close_dt_from")
    private ZonedDateTime closeDateFrom;

    @JsonProperty("close_dt_to")
    private ZonedDateTime closeDateTo;

    @JsonProperty("search_field")
    private String searchField;

}
