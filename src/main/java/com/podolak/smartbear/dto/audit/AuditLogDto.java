package com.podolak.smartbear.dto.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuditLogDto {
    private Long id;
    private Long createdAt;
    private String logMessage;
}
