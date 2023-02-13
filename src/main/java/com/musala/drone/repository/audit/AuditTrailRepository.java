package com.musala.drone.repository.audit;

import com.musala.drone.model.AuditTrail;

public interface AuditTrailRepository {
    AuditTrail save(AuditTrail auditTrail);
}
