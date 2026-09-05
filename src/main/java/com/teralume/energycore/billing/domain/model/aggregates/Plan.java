package com.teralume.energycore.billing.domain.model.aggregates;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import com.teralume.energycore.shared.domain.valueobjects.Money;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plans")
public class Plan extends AuditableEntity {

    @Column(nullable = false, unique = true, length = 40)
    private String code;

    @Column(nullable = false, length = 80)
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_price_amount", precision = 10, scale = 2)),
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_price_currency", length = 10))
    })
    private Money monthlyPrice;

    @Column(name = "max_devices")
    private Integer maxDevices;

    @Column(name = "max_routines")
    private Integer maxRoutines;

    @Column(name = "max_alerts")
    private Integer maxAlerts;

    @Column(name = "report_export_enabled")
    private Boolean reportExportEnabled;
}