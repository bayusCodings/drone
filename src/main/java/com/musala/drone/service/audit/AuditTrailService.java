package com.musala.drone.service.audit;

import com.musala.drone.model.AuditTrail;

public interface AuditTrailService {
    AuditTrail createAuditTrail(String logEntry);
}
