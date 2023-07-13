package com.podolak.smartbear.service;

import com.podolak.smartbear.dto.converter.TimeConverterResponseDto;
import com.podolak.smartbear.exception.InvalidTimeInputException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TimeConverterServiceTest {

    @Autowired
    private TimeConverterService timeConverterService;
    @MockBean
    private AuditLogService auditLogService;

    @Test
    public void shouldConvertSpecialHourlyTime() {
        TimeConverterResponseDto midnight1 = timeConverterService.convertNumericalToSpoken("00:00");
        TimeConverterResponseDto midnight2 = timeConverterService.convertNumericalToSpoken("0:00");
        TimeConverterResponseDto noon = timeConverterService.convertNumericalToSpoken("12:00");

        assertEquals("midnight", midnight1.getConvertedTime());
        assertEquals("midnight", midnight2.getConvertedTime());
        assertEquals("noon", noon.getConvertedTime());

        Mockito.verify(auditLogService, Mockito.times(3)).saveInfoAuditLog(Mockito.anyString());
        Mockito.verify(auditLogService, Mockito.never()).saveErrorAuditLog(Mockito.anyString());
    }

    @Test
    public void shouldConvertHourlyTimeBeforeNoon() {
        TimeConverterResponseDto five = timeConverterService.convertNumericalToSpoken("05:00");
        TimeConverterResponseDto three = timeConverterService.convertNumericalToSpoken("3:00");
        TimeConverterResponseDto eleven = timeConverterService.convertNumericalToSpoken("11:00");

        assertEquals("five o'clock", five.getConvertedTime());
        assertEquals("three o'clock", three.getConvertedTime());
        assertEquals("eleven o'clock", eleven.getConvertedTime());

        Mockito.verify(auditLogService, Mockito.times(3)).saveInfoAuditLog(Mockito.anyString());
        Mockito.verify(auditLogService, Mockito.never()).saveErrorAuditLog(Mockito.anyString());
    }

    @Test
    public void shouldConvertHourlyTimeAfterNoon() {
        TimeConverterResponseDto one = timeConverterService.convertNumericalToSpoken("13:00");
        TimeConverterResponseDto three = timeConverterService.convertNumericalToSpoken("15:00");
        TimeConverterResponseDto eleven = timeConverterService.convertNumericalToSpoken("23:00");

        assertEquals("one o'clock", one.getConvertedTime());
        assertEquals("three o'clock", three.getConvertedTime());
        assertEquals("eleven o'clock", eleven.getConvertedTime());

        Mockito.verify(auditLogService, Mockito.times(3)).saveInfoAuditLog(Mockito.anyString());
        Mockito.verify(auditLogService, Mockito.never()).saveErrorAuditLog(Mockito.anyString());
    }

    @Test
    public void shouldConvertTimeMinutesBeforeHalfHour() {
        TimeConverterResponseDto minutePastMidnight = timeConverterService.convertNumericalToSpoken("00:01");
        TimeConverterResponseDto quarterPastNoon = timeConverterService.convertNumericalToSpoken("12:15");
        TimeConverterResponseDto twentySevenPastFive = timeConverterService.convertNumericalToSpoken("5:27");
        TimeConverterResponseDto halfPastTen = timeConverterService.convertNumericalToSpoken("10:30");
        TimeConverterResponseDto fourteenPastThree = timeConverterService.convertNumericalToSpoken("15:14");
        TimeConverterResponseDto sixteenPastEleven = timeConverterService.convertNumericalToSpoken("23:16");
        TimeConverterResponseDto twentyNinePastEight = timeConverterService.convertNumericalToSpoken("20:29");

        assertEquals("minute past midnight", minutePastMidnight.getConvertedTime());
        assertEquals("quarter past noon", quarterPastNoon.getConvertedTime());
        assertEquals("twenty-seven past five", twentySevenPastFive.getConvertedTime());
        assertEquals("half past ten", halfPastTen.getConvertedTime());
        assertEquals("fourteen past three", fourteenPastThree.getConvertedTime());
        assertEquals("sixteen past eleven", sixteenPastEleven.getConvertedTime());
        assertEquals("twenty-nine past eight", twentyNinePastEight.getConvertedTime());

        Mockito.verify(auditLogService, Mockito.times(7)).saveInfoAuditLog(Mockito.anyString());
        Mockito.verify(auditLogService, Mockito.never()).saveErrorAuditLog(Mockito.anyString());
    }

    @Test
    public void shouldConvertTimeMinutesBeyondHalfHour() {
        TimeConverterResponseDto twentyNineToOne = timeConverterService.convertNumericalToSpoken("00:31");
        TimeConverterResponseDto quarterToOne = timeConverterService.convertNumericalToSpoken("12:45");
        TimeConverterResponseDto tenToMidnight = timeConverterService.convertNumericalToSpoken("23:50");
        TimeConverterResponseDto minuteToNoon = timeConverterService.convertNumericalToSpoken("11:59");
        TimeConverterResponseDto fiveToSix = timeConverterService.convertNumericalToSpoken("5:55");
        TimeConverterResponseDto sixteenToEleven = timeConverterService.convertNumericalToSpoken("22:44");
        TimeConverterResponseDto fourteenToEight = timeConverterService.convertNumericalToSpoken("19:46");

        assertEquals("twenty-nine to one", twentyNineToOne.getConvertedTime());
        assertEquals("quarter to one", quarterToOne.getConvertedTime());
        assertEquals("ten to midnight", tenToMidnight.getConvertedTime());
        assertEquals("minute to noon", minuteToNoon.getConvertedTime());
        assertEquals("five to six", fiveToSix.getConvertedTime());
        assertEquals("sixteen to eleven", sixteenToEleven.getConvertedTime());
        assertEquals("fourteen to eight", fourteenToEight.getConvertedTime());

        Mockito.verify(auditLogService, Mockito.times(7)).saveInfoAuditLog(Mockito.anyString());
        Mockito.verify(auditLogService, Mockito.never()).saveErrorAuditLog(Mockito.anyString());
    }

    @Test
    public void shouldThrowExceptionWhenInvalidTimeProvided() {
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.convertNumericalToSpoken("-10:00"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.convertNumericalToSpoken("10:-30"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.convertNumericalToSpoken("24:00"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.convertNumericalToSpoken("34:00"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.convertNumericalToSpoken("10:60"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.convertNumericalToSpoken("10:305"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.convertNumericalToSpoken("105:30"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.convertNumericalToSpoken("1030"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.convertNumericalToSpoken("10-30"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.convertNumericalToSpoken("10,30"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.convertNumericalToSpoken("10;30"));
        assertThrows(InvalidTimeInputException.class, () -> timeConverterService.convertNumericalToSpoken("10 30"));
        InvalidTimeInputException thrownException = assertThrows(InvalidTimeInputException.class,
                () -> timeConverterService.convertNumericalToSpoken("10:4"));

        //Exception message is hardcoded in the InvalidTimeInputException constructor, no need to check message all the time
        assertEquals("Provided invalid time as a parameter.", thrownException.getMessage());

        Mockito.verify(auditLogService, Mockito.never()).saveInfoAuditLog(Mockito.anyString());
        Mockito.verify(auditLogService, Mockito.times(13)).saveErrorAuditLog(Mockito.anyString());
    }
}
