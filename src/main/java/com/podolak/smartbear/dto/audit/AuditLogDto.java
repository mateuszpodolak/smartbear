package com.podolak.smartbear.dto.audit;

import com.podolak.smartbear.enums.AuditLogLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuditLogDto {
    private Long id;
    private Long createdAt;
    private String logMessage;
    private AuditLogLevel logLevel;
}
