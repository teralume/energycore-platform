package com.teralume.energycore.reporting.domain.model.aggregates;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "consumption_reports")
public class ConsumptionReport extends AuditableEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalWatts;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal averageWatts;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal highestWatts;

    private LocalDate startDate;
    private LocalDate endDate;

    public void refreshMetrics(
            BigDecimal totalWatts,
            BigDecimal averageWatts,
            BigDecimal highestWatts
    ) {
        this.totalWatts = totalWatts;
        this.averageWatts = averageWatts;
        this.highestWatts = highestWatts;
    }
}
