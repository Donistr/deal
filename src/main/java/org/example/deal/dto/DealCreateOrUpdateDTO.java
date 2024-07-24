package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.help.DealTypeEnum;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class DealCreateOrUpdateDTO {

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

    @JsonProperty("type_id")
    private DealTypeEnum typeId;

    @JsonProperty("sum")
    private Double sum;

    @JsonProperty("close_dt")
    private ZonedDateTime closeDate;

}
