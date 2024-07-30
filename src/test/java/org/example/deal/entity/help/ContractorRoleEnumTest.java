package org.example.deal.entity.help;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@SpringJUnitConfig
public class ContractorRoleEnumTest {

    @Test
    public void contractorRoleEnumTest() {
        Set<ContractorRoleEnum> values = Arrays.stream(ContractorRoleEnum.values()).collect(Collectors.toSet());

        Assertions.assertEquals(6, values.size());
        Assertions.assertTrue(values.contains(ContractorRoleEnum.valueOf("BORROWER")));
        Assertions.assertTrue(values.contains(ContractorRoleEnum.valueOf("DRAWER")));
        Assertions.assertTrue(values.contains(ContractorRoleEnum.valueOf("ISSUER")));
        Assertions.assertTrue(values.contains(ContractorRoleEnum.valueOf("WARRANTY")));
        Assertions.assertTrue(values.contains(ContractorRoleEnum.valueOf("GARANT")));
        Assertions.assertTrue(values.contains(ContractorRoleEnum.valueOf("PLEDGER")));
    }

}
