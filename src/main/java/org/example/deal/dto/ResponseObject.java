package org.example.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Этот класс представляет ответ сервера, который содержит сообщение message
 */
@Data
@AllArgsConstructor
public class ResponseObject {

    @JsonProperty("message")
    @Schema(description = "Сообщение-ответ", example = "message_test")
    private String message;

}
