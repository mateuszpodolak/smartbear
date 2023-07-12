package com.podolak.smartbear.dao;

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

    public AuditLog(String logMsg) {
        this.createdAt = new Date().getTime();
        this.logMsg = logMsg;
    }
}
