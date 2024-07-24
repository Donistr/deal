package org.example.deal.entity.help;

import lombok.Getter;

@Getter
public enum DealTypeEnum {

    CREDIT("CREDIT"),
    OVERDRAFT("OVERDRAFT"),
    OTHER("OTHER");

    private final String id;

    DealTypeEnum(String id) {
        this.id = id;
    }

}
