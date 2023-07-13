package com.podolak.smartbear.web;

import com.podolak.smartbear.dto.PageResponseDto;
import com.podolak.smartbear.dto.audit.AuditLogDto;
import com.podolak.smartbear.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Audit log API")
@RequestMapping("/api/v1/audit-log")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @Autowired
    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @Operation(summary = "Retrieve all audit logs paginated")
    @GetMapping("/all")
    public ResponseEntity<PageResponseDto<AuditLogDto>> getAllLogs(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(auditLogService.getAuditLogsPaginated(page, size));
    }
}
