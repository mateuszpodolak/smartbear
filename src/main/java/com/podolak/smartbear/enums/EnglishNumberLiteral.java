package com.podolak.smartbear.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * All English number literals used in spoken British required to represent time. Corner cases like midnight or noon
 * are not represented here - this must be universal for both hours and minutes representations.
 */
@Getter
@AllArgsConstructor
public enum EnglishNumberLiteral {
    ONE(1, "one"),
    TWO(2, "two"),
    THREE(3, "three"),
    FOUR(4, "four"),
    FIVE(5, "five"),
    SIX(6, "six"),
    SEVEN(7, "seven"),
    EIGHT(8, "eight"),
    NINE(9, "nine"),
    TEN(10, "ten"),
    ELEVEN(11, "eleven"),
    TWELVE(12, "twelve"),
    THIRTEEN(13, "thirteen"),
    FOURTEEN(14, "fourteen"),
    QUARTER(15, "quarter"),
    SIXTEEN(16, "sixteen"),
    SEVENTEEN(17, "seventeen"),
    EIGHTEEN(18, "eighteen"),
    NINETEEN(19, "nineteen"),
    TWENTY(20, "twenty"),
    TWENTY_ONE(21, "twenty-one"),
    TWENTY_TWO(22, "twenty-two"),
    TWENTY_THREE(23, "twenty-three"),
    TWENTY_FOUR(24, "twenty-four"),
    TWENTY_FIVE(25, "twenty-five"),
    TWENTY_SIX(26, "twenty-six"),
    TWENTY_SEVEN(27, "twenty-seven"),
    TWENTY_EIGHT(28, "twenty-eight"),
    TWENTY_NINE(29, "twenty-nine"),
    HALF(30, "half");

    public static final Map<Integer, String> LITERALS_MAP;

    static {
        Map<Integer, String> map = new HashMap<>();
        for (EnglishNumberLiteral literal : EnglishNumberLiteral.values()) {
            map.put(literal.getNumeral(), literal.getSpokenValue());
        }
        LITERALS_MAP = Collections.unmodifiableMap(map);
    }

    private final int numeral;
    private final String spokenValue;

    public static String convertNumericalToSpokenValue(int numerical) {
        return LITERALS_MAP.get(numerical);
    }
}
