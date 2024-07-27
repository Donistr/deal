package org.example.deal.entity.help;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Этот енам представляет возможные статусы сделок
 */
@Getter
@AllArgsConstructor
public enum DealStatusEnum {

    DRAFT("DRAFT"),
    ACTIVE("ACTIVE"),
    CLOSED("CLOSED");

    private final String value;

}
