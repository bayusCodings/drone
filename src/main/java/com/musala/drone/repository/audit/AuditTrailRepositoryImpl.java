package com.musala.drone.repository.audit;

import com.musala.drone.model.AuditTrail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuditTrailRepositoryImpl implements AuditTrailRepository {
    private final JpaAuditTrailRepository repository;

    @Override
    public AuditTrail save(AuditTrail auditTrail) {
        return repository.save(auditTrail);
    }
}
