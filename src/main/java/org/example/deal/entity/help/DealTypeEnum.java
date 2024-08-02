package org.example.deal.entity.help;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Этот енам представляет возможные типы сделок
 */
@Getter
@AllArgsConstructor
public enum DealTypeEnum {

    CREDIT("CREDIT"),
    OVERDRAFT("OVERDRAFT"),
    OTHER("OTHER");

    private final String value;

}
