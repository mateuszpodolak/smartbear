package com.podolak.smartbear.service;

import com.podolak.smartbear.dto.converter.TimeConverterResponseDto;
import com.podolak.smartbear.enums.EnglishNumberLiteral;
import com.podolak.smartbear.enums.TimePreposition;
import com.podolak.smartbear.exception.InvalidTimeInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class TimeConverterService {

    private static final String MIDNIGHT = "midnight";
    private static final String MINUTE = "minute";
    private static final String NOON = "noon";
    private static final String TIME_24H_REGEX = "([01]?\\d|2[0-3]):[0-5]\\d";

    private final AuditLogService auditLogService;

    @Autowired
    public TimeConverterService(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    /**
     * Converts numerical time represented in format HH:MM (24-hour clock) to spoken British time.
     *
     * @param numericalTime time represented in format HH:NN.
     * @return TimeConverterResponseDto containing requested time to convert and converted time represented
     * in spoken British, e.g. half past ten.
     */
    public TimeConverterResponseDto convertNumericalToSpoken(String numericalTime) {
        if (!validateNumericalTime(numericalTime)) {
            log.warn("Numerical time validation failed.");
            auditLogService.saveErrorAuditLog("Parsing failure. Provided invalid numerical time: '%s'".formatted(numericalTime));
            throw new InvalidTimeInputException();
        }

        String[] timeElements = numericalTime.split(":");
        int hours = Integer.parseInt(timeElements[0]);
        int minutes = Integer.parseInt(timeElements[1]);

        String convertedTime = convertTimeToSpoken(hours, minutes);
        auditLogService.saveInfoAuditLog("Successfully converted numerical time: '%s' into British spoken: '%s'".formatted(numericalTime, convertedTime));
        return new TimeConverterResponseDto(numericalTime, convertedTime);
    }

    private String convertTimeToSpoken(int hours, int minutes) {
        TimePreposition timePreposition = TimePreposition.evaluateTimePreposition(minutes);
        int hoursToConvert = timePreposition == TimePreposition.TO ? hours + 1 : hours;
        if (timePreposition == TimePreposition.O_CLOCK) {
            return convertHourlyTimes(hours);
        } else {
            return convertMinutesToSpoken(minutes, timePreposition) + " " + convertHoursToWord(hoursToConvert);
        }
    }

    /**
     * Hourly times are special cases - they do not contain info regarding value of minutes, they only add 'o'clock'
     * preposition after hour values. There are also two extra special cases for midnight and noon in which this
     * preposition is skipped.
     *
     * @param hours numerical hours [0-24].
     * @return spoken hourly time extended with 'o'clock' preposition.
     */
    private String convertHourlyTimes(int hours) {
        String hoursLiteral = convertHoursToWord(hours);
        if (hours != 0 && hours != 12) {
            return hoursLiteral + convertMinutesToSpoken(0, TimePreposition.O_CLOCK);
        }
        return hoursLiteral;
    }

    /**
     * Converts numerical value of minutes into spoken representation.
     * In spoken British if minutes will be greater than 30 we should be announcing how many minutes are left to
     * the upcoming hour, hence the minutes value is subtracted from 60, e.g. if we want to convert 10:50 into spoken
     * British the outcome will be: 'ten to eleven'.
     * <br>
     * Two extra cases:
     * <ul>
     *     <li>0 minutes value is skipped and then only o'clock time preposition is added.</li>
     *     <li>10:01 time is 'minute past ten' instead of 'one past ten'.</li>
     * </ul>
     *
     * @param minutes         numerical minutes [0-59].
     * @param timePreposition enum of given time preposition that should be included in the outcome.
     * @return spoken minutes outcome with correct time preposition attached to it.
     */
    private String convertMinutesToSpoken(int minutes, TimePreposition timePreposition) {
        int minutesToConvert = timePreposition == TimePreposition.TO ? 60 - minutes : minutes;
        String minutesLiteral = switch (minutesToConvert) {
            case 0 -> "";
            case 1 -> MINUTE;
            default -> EnglishNumberLiteral.convertNumericalToSpokenValue(minutesToConvert);
        };
        return minutesLiteral + " " + timePreposition.getValue();
    }

    /**
     * Converts numerical value of hours into spoken representation.
     * In spoken British only 12-hour clock is used, e.g. if we want to convert 14:30 into spoken British the outcome
     * will be: 'half past two'.
     *
     * @param hours numerical hour [0-24].
     * @return word representation of hours in spoken British.
     */
    private String convertHoursToWord(int hours) {
        return switch (hours) {
            case 0, 24 -> MIDNIGHT;
            case 12 -> NOON;
            default -> {
                int hoursToConvert = hours > 12 ? hours - 12 : hours;
                yield EnglishNumberLiteral.convertNumericalToSpokenValue(hoursToConvert);
            }
        };
    }

    /**
     * Validates numerical time provided as a String param. Expected format is HH:MM (24-hour clock).
     *
     * @param timeToValidate time parameter to validate.
     * @return boolean resul of validation.
     */
    private boolean validateNumericalTime(String timeToValidate) {
        Pattern expectedTimePattern = Pattern.compile(TIME_24H_REGEX);
        Matcher matcher = expectedTimePattern.matcher(timeToValidate);
        return matcher.matches();
    }

}
