package org.example.deal.entity.help;

import lombok.Getter;

@Getter
public enum DealTypeEnum {

    CREDIT("CREDIT"),
    OVERDRAFT("OVERDRAFT"),
    OTHER("OTHER");

    private final String value;

    DealTypeEnum(String value) {
        this.value = value;
    }

}
