package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Этот класс представляет DTO для сделки
 */
@Data
@Builder
public class DealDTO {

    @JsonProperty("id")
    @Schema(description = "id сделки", example = "01ebc64d-f477-41e7-a300-53871b4f2ada")
    private UUID id;

    @JsonProperty("description")
    @Schema(description = "Описание сделки", example = "description_test")
    private String description;

    @JsonProperty("agreement_number")
    @Schema(description = "Номер соглашения", example = "agreement_number_test")
    private String agreementNumber;

    @JsonProperty("agreement_date")
    @Schema(description = "Дата договора", example = "2024-07-27T17:14:51.659788100")
    private LocalDateTime agreementDate;

    @JsonProperty("agreement_start_dt")
    @Schema(description = "Дата и время вступления соглашения в силу", example = "2024-07-27T17:14:51.659788100")
    private LocalDateTime agreementStartDate;

    @JsonProperty("availability_date")
    @Schema(description = "Срок действия сделки", example = "2024-07-27T17:14:51.659788100")
    private LocalDateTime availabilityDate;

    @JsonProperty("type")
    @Schema(description = "Тип сделки")
    private DealTypeDTO type;

    @JsonProperty("status")
    @Schema(description = "Статус сделки")
    private DealStatusDTO status;

    @JsonProperty("sum")
    @Schema(description = "Сумма сделки", example = "123.4")
    private Double sum;

    @JsonProperty("close_dt")
    @Schema(description = "Дата и время закрытия сделки", example = "2024-07-27T17:14:51.659788100")
    private LocalDateTime closeDate;

    @JsonProperty("contractors")
    @Schema(description = "Список контрагентов сделки")
    private List<ContractorDTO> contractors;

}
