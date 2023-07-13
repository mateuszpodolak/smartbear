package com.podolak.smartbear.dao;

import com.podolak.smartbear.enums.AuditLogLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@Table(name = "audit_log")
@NoArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(nullable = false)
    private String logMsg;

    @Column(nullable = false)
    private AuditLogLevel logLevel;

    public AuditLog(String logMsg, AuditLogLevel logLevel) {
        this.createdAt = new Date().getTime();
        this.logMsg = logMsg;
        this.logLevel = logLevel;
    }
}
