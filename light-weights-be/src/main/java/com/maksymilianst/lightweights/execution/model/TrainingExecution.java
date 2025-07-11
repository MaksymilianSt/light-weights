package com.maksymilianst.lightweights.execution.model;

import com.maksymilianst.lightweights.plan.model.Training;
import com.maksymilianst.lightweights.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "training_execution")
@Data
@NoArgsConstructor
public class TrainingExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 1024)
    private String notes;

    @Column(name = "realization_date", nullable = false)
    private LocalDate realizationDate;

    @Column(nullable = false)
    private boolean done;


    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_training_id", nullable = false)
    private Training referencedTraining;

    @OneToMany(mappedBy = "trainingExecution", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<TrainingExerciseExecution> trainingExerciseExecutions = new HashSet<>();

}
