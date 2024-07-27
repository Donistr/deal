package org.example.deal.entity.help;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Этот енам представляет возможные роли контрагентов
 */
@Getter
@AllArgsConstructor
public enum ContractorRoleEnum {

    BORROWER("BORROWER"),
    DRAWER("DRAWER"),
    ISSUER("ISSUER"),
    WARRANTY("WARRANTY"),
    GARANT("GARANT"),
    PLEDGER("PLEDGER");

    private final String value;

}
