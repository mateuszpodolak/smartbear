package com.podolak.smartbear.dto.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TimeConverterResponseDto {
    private String requestedTimeToConvert;
    private String convertedTime;
}
