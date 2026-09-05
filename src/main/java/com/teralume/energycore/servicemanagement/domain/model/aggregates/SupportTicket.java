package com.teralume.energycore.servicemanagement.domain.model.aggregates;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "support_tickets")
public class SupportTicket extends AuditableEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 160)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 40)
    private String priority;

    @Column(nullable = false, length = 40)
    private String status;

    public void resolve() {
        this.status = "CLOSED";
    }

    public boolean isOpen() {
        return status != null
                && !"CLOSED".equalsIgnoreCase(status)
                && !"COMPLETED".equalsIgnoreCase(status)
                && !"CANCELED".equalsIgnoreCase(status);
    }
}