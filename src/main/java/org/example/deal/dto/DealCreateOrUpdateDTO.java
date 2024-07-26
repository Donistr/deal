package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.help.DealTypeEnum;

import java.time.LocalDateTime;
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
    private LocalDateTime agreementDate;

    @JsonProperty("agreement_start_dt")
    private LocalDateTime agreementStartDate;

    @JsonProperty("availability_date")
    private LocalDateTime availabilityDate;

    @JsonProperty("type_id")
    private DealTypeEnum typeId;

    @JsonProperty("sum")
    private Double sum;

    @JsonProperty("close_dt")
    private LocalDateTime closeDate;

}
