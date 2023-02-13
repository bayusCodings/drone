package com.musala.drone.service.audit;

import com.musala.drone.model.AuditTrail;
import com.musala.drone.repository.audit.AuditTrailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditTrailServiceImpl implements AuditTrailService {
    private final AuditTrailRepository auditTrailRepository;

    @Override
    public AuditTrail createAuditTrail(String logEntry) {
        log.info("creating audit trail entry");
        AuditTrail auditTrail = AuditTrail.builder().log(logEntry).build();
        return auditTrailRepository.save(auditTrail);
    }
}
