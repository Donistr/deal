package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.help.DealStatusEnum;
import org.example.deal.entity.help.DealTypeEnum;

import java.time.LocalDateTime;
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
    private LocalDateTime agreementDateFrom;

    @JsonProperty("agreement_date_to")
    private LocalDateTime agreementDateTo;

    @JsonProperty("availability_date_from")
    private LocalDateTime availabilityDateFrom;

    @JsonProperty("availability_date_to")
    private LocalDateTime availabilityDateTo;

    @JsonProperty("type")
    private List<DealTypeEnum> typeIds;

    @JsonProperty("status")
    private List<DealStatusEnum> statusIds;

    @JsonProperty("close_dt_from")
    private LocalDateTime closeDateFrom;

    @JsonProperty("close_dt_to")
    private LocalDateTime closeDateTo;

    @JsonProperty("search_field")
    private String searchField;

}
