package com.podolak.smartbear.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDto {
    private String errorMessage;
    private int statusCode;
}
