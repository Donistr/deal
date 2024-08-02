package org.example.deal.entity.help;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@SpringJUnitConfig
class DealStatusEnumTest {

    @Test
    public void dealStatusEnumTest() {
        Set<DealStatusEnum> values = Arrays.stream(DealStatusEnum.values()).collect(Collectors.toSet());

        Assertions.assertEquals(3, values.size());
        Assertions.assertTrue(values.contains(DealStatusEnum.valueOf("DRAFT")));
        Assertions.assertTrue(values.contains(DealStatusEnum.valueOf("ACTIVE")));
        Assertions.assertTrue(values.contains(DealStatusEnum.valueOf("CLOSED")));
    }

}
