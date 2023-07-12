package com.podolak.smartbear.service;

import com.podolak.smartbear.exception.InvalidTimeInputException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TimeConverterServiceTest {

    @Autowired
    private TimeConverterService timeConverterService;

    @Test
    public void shouldConvertSpecialHourlyTime() {
        String midnight1 = timeConverterService.numericalToSpoken("00:00");
        String midnight2 = timeConverterService.numericalToSpoken("0:00");
        String noon = timeConverterService.numericalToSpoken("12:00");

        assertEquals("midnight", midnight1);
        assertEquals("midnight", midnight2);
        assertEquals("noon", noon);
    }

    @Test
    public void shouldConvertHourlyTimeBeforeNoon() {
        String five = timeConverterService.numericalToSpoken("05:00");
        String three = timeConverterService.numericalToSpoken("3:00");
        String eleven = timeConverterService.numericalToSpoken("11:00");

        assertEquals("five o'clock", five);
        assertEquals("three o'clock", three);
        assertEquals("eleven o'clock", eleven);
    }

    @Test
    public void shouldConvertHourlyTimeAfterNoon() {
        String one = timeConverterService.numericalToSpoken("13:00");
        String three = timeConverterService.numericalToSpoken("15:00");
        String eleven = timeConverterService.numericalToSpoken("23:00");

        assertEquals("one o'clock", one);
        assertEquals("three o'clock", three);
        assertEquals("eleven o'clock", eleven);
    }

    @Test
    public void shouldConvertTimeMinutesBeforeHalfHour() {
        String minutePastMidnight = timeConverterService.numericalToSpoken("00:01");
        String quarterPastNoon = timeConverterService.numericalToSpoken("12:15");
        String twentySevenPastFive = timeConverterService.numericalToSpoken("5:27");
        String halfPastTen = timeConverterService.numericalToSpoken("10:30");
        String fourteenPastThree = timeConverterService.numericalToSpoken("15:14");
        String sixteenPastEleven = timeConverterService.numericalToSpoken("23:16");
        String twentyNinePastEight = timeConverterService.numericalToSpoken("20:29");

        assertEquals("minute past midnight", minutePastMidnight);
        assertEquals("quarter past noon", quarterPastNoon);
        assertEquals("twenty-seven past five", twentySevenPastFive);
        assertEquals("half past ten", halfPastTen);
        assertEquals("fourteen past three", fourteenPastThree);
        assertEquals("sixteen past eleven", sixteenPastEleven);
        assertEquals("twenty-nine past eight", twentyNinePastEight);
    }

    @Test
    public void shouldConvertTimeMinutesBeyondHalfHour() {
        String twentyNineToOne = timeConverterService.numericalToSpoken("00:31");
        String quarterToOne = timeConverterService.numericalToSpoken("12:45");
        String tenToMidnight = timeConverterService.numericalToSpoken("23:50");
        String minuteToNoon = timeConverterService.numericalToSpoken("11:59");
        String fiveToSix = timeConverterService.numericalToSpoken("5:55");
        String sixteenToEleven = timeConverterService.numericalToSpoken("22:44");
        String fourteenToEight = timeConverterService.numericalToSpoken("19:46");

        assertEquals("twenty-nine to one", twentyNineToOne);
        assertEquals("quarter to one", quarterToOne);
        assertEquals("ten to midnight", tenToMidnight);
        assertEquals("minute to noon", minuteToNoon);
        assertEquals("five to six", fiveToSix);
        assertEquals("sixteen to eleven", sixteenToEleven);
        assertEquals("fourteen to eight", fourteenToEight);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidTimeProvided() {
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.numericalToSpoken("-10:00"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.numericalToSpoken("10:-30"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.numericalToSpoken("24:00"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.numericalToSpoken("34:00"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.numericalToSpoken("10:60"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.numericalToSpoken("10:305"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.numericalToSpoken("105:30"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.numericalToSpoken("1030"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.numericalToSpoken("10-30"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.numericalToSpoken("10,30"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.numericalToSpoken("10;30"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.numericalToSpoken("10 30"));
        InvalidTimeInputException thrownException = assertThrows(InvalidTimeInputException.class,
                () -> timeConverterService.numericalToSpoken("10:4"));

        //Exception message is hardcoded in the InvalidTimeInputException constructor, no need to check message all the time
        assertEquals("Provided invalid time as a parameter.", thrownException.getMessage());
    }
}
