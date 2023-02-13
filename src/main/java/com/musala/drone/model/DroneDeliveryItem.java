package com.musala.drone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "drone_delivery_items",
    indexes = {
        @Index(name = "drone_delivery_items_drone_id_index", columnList = "drone_id"),
        @Index(name = "drone_delivery_items_medication_id_index", columnList = "medication_id"),
        @Index(name = "drone_delivery_items_drone_delivery_id_index", columnList = "drone_delivery_id")
    }
)
@EntityListeners(AuditingEntityListener.class)
public class DroneDeliveryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="drone_id", nullable = false)
    @JsonIgnore
    private Drone drone;

    @ManyToOne
    @JoinColumn(name="medication_id", nullable = false)
    private Medication medication;

    @ManyToOne
    @JoinColumn(name="drone_delivery_id", nullable = false)
    @JsonIgnore
    private DroneDelivery droneDelivery;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date updatedAt;
}