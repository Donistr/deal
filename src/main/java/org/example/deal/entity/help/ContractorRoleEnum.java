package org.example.deal.entity.help;

import lombok.Getter;

@Getter
public enum ContractorRoleEnum {

    BORROWER("BORROWER"),
    DRAWER("DRAWER"),
    ISSUER("ISSUER"),
    WARRANTY("WARRANTY"),
    GARANT("GARANT"),
    PLEDGER("PLEDGER");

    private final String value;

    ContractorRoleEnum(String value) {
        this.value = value;
    }

}
