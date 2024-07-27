package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.example.deal.entity.help.DealStatusEnum;
import org.example.deal.entity.help.DealTypeEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class DealSearchRequestDTO {

    @JsonProperty("id")
    @Schema(description = "id сделки (точное совпадение)", example = "01ebc64d-f477-41e7-a300-53871b4f2ada")
    private UUID id;

    @JsonProperty("description")
    @Schema(description = "Описание сделки (точное совпадение)", example = "description_test")
    private String description;

    @JsonProperty("agreement_number")
    @Schema(description = "Номер соглашения (частичное совпадение)", example = "agreement_number_test")
    private String agreementNumber;

    @JsonProperty("agreement_date_from")
    @Schema(description = "Дата договора начиная с которой следует искать сделку", example = "2024-07-27T17:14:51.659788100")
    private LocalDateTime agreementDateFrom;

    @JsonProperty("agreement_date_to")
    @Schema(description = "Дата договора до которой следует искать сделку", example = "2024-07-27T17:14:51.659788100")
    private LocalDateTime agreementDateTo;

    @JsonProperty("availability_date_from")
    @Schema(description = "Срок действия сделки начиная с которого следует искать сделку", example = "2024-07-27T17:14:51.659788100")
    private LocalDateTime availabilityDateFrom;

    @JsonProperty("availability_date_to")
    @Schema(description = "Срок действия сделки до которого следует искать сделку", example = "2024-07-27T17:14:51.659788100")
    private LocalDateTime availabilityDateTo;

    @JsonProperty("type")
    @Schema(description = "Список id типов сделок, которые следует искать")
    private List<DealTypeEnum> typeIds;

    @JsonProperty("status")
    @Schema(description = "Список id статусов сделок, которые следует искать")
    private List<DealStatusEnum> statusIds;

    @JsonProperty("close_dt_from")
    @Schema(description = "Дата и время закрытия сделки начиная с которой следует искать сделку", example = "2024-07-27T17:14:51.659788100")
    private LocalDateTime closeDateFrom;

    @JsonProperty("close_dt_to")
    @Schema(description = "Дата и время закрытия сделки до которой следует искать сделку", example = "2024-07-27T17:14:51.659788100")
    private LocalDateTime closeDateTo;

    @JsonProperty("search_field")
    @Schema(description = "Частичное совпадение с одним из полей контрагента: id, name, inn", example = "some_string")
    private String searchField;

}
