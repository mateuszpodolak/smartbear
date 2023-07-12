package com.podolak.smartbear.repository;

import com.podolak.smartbear.dao.AuditLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends PagingAndSortingRepository<AuditLog, Long>, CrudRepository<AuditLog, Long> {

}
