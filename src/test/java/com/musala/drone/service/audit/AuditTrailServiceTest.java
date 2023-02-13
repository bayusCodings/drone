package com.musala.drone.service.audit;

import com.musala.drone.model.AuditTrail;
import com.musala.drone.repository.audit.AuditTrailRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class AuditTrailServiceTest {
    private AuditTrailService service;
    private AuditTrailRepository auditTrailRepository;

    @BeforeEach
    public void setUp() {
        auditTrailRepository = mock(AuditTrailRepository.class);
        service = new AuditTrailServiceImpl(auditTrailRepository);
    }

    @Test
    void validateAuditTrailCreation() {
        String mockLogEntry = "test log message";
        AuditTrail auditTrail = AuditTrail.builder().log(mockLogEntry).build();

        doReturn(auditTrail).when(auditTrailRepository).save(any());
        AuditTrail createdAuditLog = service.createAuditTrail(anyString());

        Assertions.assertThat(createdAuditLog.getLog()).isEqualTo(mockLogEntry);
    }
}
