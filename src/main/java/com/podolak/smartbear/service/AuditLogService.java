package com.podolak.smartbear.service;

import com.podolak.smartbear.dao.AuditLog;
import com.podolak.smartbear.dto.audit.AuditLogDto;
import com.podolak.smartbear.dto.PageResponseDto;
import com.podolak.smartbear.enums.AuditLogLevel;
import com.podolak.smartbear.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * Saves new record in AuditLog DB.
     */
    public AuditLog saveInfoAuditLog(String logMsg) {
        return auditLogRepository.save(new AuditLog(logMsg, AuditLogLevel.INFO));
    }

    /**
     * Saves new record in AuditLog DB.
     */
    public AuditLog saveErrorAuditLog(String logMsg) {
        return auditLogRepository.save(new AuditLog(logMsg, AuditLogLevel.ERROR));
    }

    /**
     * Retrieves audit logs from DB, paginated.
     */
    public PageResponseDto<AuditLogDto> getAuditLogsPaginated(int page, int size) {
        Page<AuditLog> auditLogPage = auditLogRepository.findAll(PageRequest.of(page, size));
        List<AuditLogDto> auditLogsDtos = auditLogPage.getContent().stream()
                .map(al -> new AuditLogDto(al.getId(), al.getCreatedAt(), al.getLogMsg(), al.getLogLevel()))
                .toList();
        return new PageResponseDto<>(auditLogsDtos, auditLogPage.getNumber(), auditLogPage.getSize(), auditLogPage.getTotalPages());
    }
}
