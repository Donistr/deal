package org.example.deal.entity.help;

import lombok.Getter;

@Getter
public enum DealStatusEnum {

    DRAFT("DRAFT"),
    ACTIVE("ACTIVE"),
    CLOSED("CLOSED");

    private final String value;

    DealStatusEnum(String value) {
        this.value = value;
    }

}
