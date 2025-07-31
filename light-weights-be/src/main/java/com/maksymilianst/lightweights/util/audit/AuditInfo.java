package com.maksymilianst.lightweights.util.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
public class AuditInfo {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(Clock.systemUTC());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now(Clock.systemUTC());
    }
}
