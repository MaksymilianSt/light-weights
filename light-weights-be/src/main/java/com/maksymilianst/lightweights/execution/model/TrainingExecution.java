package com.maksymilianst.lightweights.execution.model;

import com.maksymilianst.lightweights.plan.model.Training;
import com.maksymilianst.lightweights.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "training_execution")
@Getter
@Setter
@NoArgsConstructor
public class TrainingExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 1024)
    private String notes;

    @Column(name = "execution_start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "execution_finish_date")
    private LocalDateTime finishDate;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_training_id", nullable = false)
    private Training referencedTraining;

    @OneToMany(mappedBy = "trainingExecution", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<TrainingExerciseExecution> trainingExerciseExecutions = new HashSet<>();


    @PrePersist
    protected void onCreate() {
        startDate = LocalDateTime.now();
    }

}
