package com.podolak.smartbear.web;

import com.podolak.smartbear.dto.ErrorDto;
import com.podolak.smartbear.dto.converter.TimeConverterResponseDto;
import com.podolak.smartbear.service.TimeConverterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "Time converter API")
@RequestMapping("/api/v1/time-converter")
public class TimeConverterController {

    private final TimeConverterService timeConverterService;

    @Autowired
    public TimeConverterController(TimeConverterService timeConverterService) {
        this.timeConverterService = timeConverterService;
    }

    @Operation(
            summary = "Convert numerical time into spoken British",
            description = "Convert numerical time in HH-MM format (24-hour clock) into spoken British representation"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conversion successful"),
            @ApiResponse(responseCode = "400", description = "Provided invalid input time", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @GetMapping("/to-spoken")
    public ResponseEntity<TimeConverterResponseDto> convertNumericalTimeToSpoken(
            @RequestParam @Parameter(description = "Time represented in HH:MM format, 24-hour clock", example = "15:30") String numericalTime
    ) {
        log.info("Received request to convert numerical time {} to spoken.", numericalTime);
        return ResponseEntity.ok(timeConverterService.convertNumericalToSpoken(numericalTime));
    }
}
