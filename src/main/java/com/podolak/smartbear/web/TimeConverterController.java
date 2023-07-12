package com.podolak.smartbear.web;

import com.podolak.smartbear.dto.converter.TimeConverterResponseDto;
import com.podolak.smartbear.service.AuditLogService;
import com.podolak.smartbear.service.TimeConverterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/time-converter")
public class TimeConverterController {

    private final AuditLogService auditLogService;
    private final TimeConverterService timeConverterService;

    @Autowired
    public TimeConverterController(AuditLogService auditLogService, TimeConverterService timeConverterService) {
        this.auditLogService = auditLogService;
        this.timeConverterService = timeConverterService;
    }

    @GetMapping("/to-spoken")
    public ResponseEntity<TimeConverterResponseDto> convertNumericalTimeToSpoken(@RequestParam String numericalTime) {
        log.info("Received request to convert numerical time {} to spoken.", numericalTime);
        String convertedTime = timeConverterService.numericalToSpoken(numericalTime);
        auditLogService.saveAuditLog("Successfully converted numerical time: '%s' into British spoken: '%s'".formatted(numericalTime, convertedTime));
        return ResponseEntity.ok(
                new TimeConverterResponseDto(numericalTime, convertedTime)
        );
    }
}
