package com.teralume.energycore.billing.domain.model.aggregates;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import com.teralume.energycore.shared.domain.valueobjects.Money;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice extends AuditableEntity {

    @Column(nullable = false)
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(unique = true, length = 80)
    private String invoiceNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "total_amount", precision = 10, scale = 2)),
            @AttributeOverride(name = "currency", column = @Column(name = "currency", length = 10))
    })
    private Money totalAmount;

    private LocalDate issuedAt;

    public Invoice(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Invoice payment is required.");
        }
        if (payment.getAmount() == null) {
            throw new IllegalArgumentException("Invoice total amount is required.");
        }

        this.userId = payment.getUserId();
        this.payment = payment;
        this.invoiceNumber = "INV-" + payment.getId();
        this.totalAmount = payment.getAmount();
        this.issuedAt = LocalDate.now();
    }
}