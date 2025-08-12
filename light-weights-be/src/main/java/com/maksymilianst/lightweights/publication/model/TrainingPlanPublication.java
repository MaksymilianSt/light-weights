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
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "planPublication")
    private Set<TrainingPlanPublicationOpinion> opinions = new HashSet<>();

    @OneToMany(mappedBy = "planPublication")
    private Set<TrainingPlanPublicationDownload> downloads = new HashSet<>();

    public Set<TrainingPlanPublicationDownload> getUserUniqueDownloads() {
        return new HashSet<>(
                downloads.stream()
                        .collect(Collectors.toMap(
                                download -> download.getUser().getId(),
                                Function.identity(),
                                (existing, replacement) -> existing
                        ))
                        .values()
        );
    }

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
