package com.serkankarabulut.accountservice.util;

import java.math.BigDecimal;

public class NumberUtil {

    public static boolean isEqual(BigDecimal v1, BigDecimal v2) {
        return v1.compareTo(v2) == 0;
    }

    public static boolean isNotEqual(BigDecimal v1, BigDecimal v2) {
        return !isEqual(v1, v2);
    }
}
