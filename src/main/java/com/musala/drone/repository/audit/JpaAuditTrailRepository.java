package com.musala.drone.repository.audit;

import com.musala.drone.model.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuditTrailRepository extends JpaRepository<AuditTrail, Long> {
}
