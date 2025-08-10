package com.maksymilianst.lightweights.publication.model;

import com.maksymilianst.lightweights.plan.model.TrainingPlan;
import com.maksymilianst.lightweights.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.Clock;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PUBLICATION")
@Where(clause = "author_id IS NOT NULL")
@Getter
@Setter
@NoArgsConstructor
public class TrainingPlanPublication extends TrainingPlan {

    @ManyToOne()
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    public LocalDateTime getPublicationDate() {
        return getAuditInfo().getCreatedAt();
    }

    @PrePersist
    public void prePersist() {
        super.auditInfo.setCreatedAt(LocalDateTime.now(Clock.systemUTC()));
    }

    @PreUpdate
    public void preUpdate() {
        super.auditInfo.setCreatedAt(LocalDateTime.now(Clock.systemUTC()));
    }
}
