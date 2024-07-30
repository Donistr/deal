package org.example.deal.entity.help;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@SpringJUnitConfig
class DealTypeEnumTest {

    @Test
    public void dealTypeEnumTest() {
        Set<DealTypeEnum> values = Arrays.stream(DealTypeEnum.values()).collect(Collectors.toSet());

        Assertions.assertEquals(3, values.size());
        Assertions.assertTrue(values.contains(DealTypeEnum.valueOf("CREDIT")));
        Assertions.assertTrue(values.contains(DealTypeEnum.valueOf("OVERDRAFT")));
        Assertions.assertTrue(values.contains(DealTypeEnum.valueOf("OTHER")));
    }

}
