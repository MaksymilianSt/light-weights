package com.maksymilianst.lightweights.publication.model;

import com.maksymilianst.lightweights.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Clock;
import java.time.LocalDateTime;

@Entity
@Table(name = "plan_download")
@Getter
@Setter
@NoArgsConstructor
public class TrainingPlanPublicationDownload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "download_at")
    private LocalDateTime downloadAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "plan_publication_id")
    private TrainingPlanPublication planPublication;


    @PrePersist
    protected void onCreate() {
        downloadAt = LocalDateTime.now(Clock.systemUTC());
    }
}
