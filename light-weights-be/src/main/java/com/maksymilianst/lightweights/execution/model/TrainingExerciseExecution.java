package com.maksymilianst.lightweights.execution.model;

import com.maksymilianst.lightweights.plan.model.Exercise;
import com.maksymilianst.lightweights.plan.model.TrainingExercise;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "training_exercise_execution")
@Data
@NoArgsConstructor
public class TrainingExerciseExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 1024)
    private String notes;

    @Column(nullable = false)
    private boolean done;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @OneToOne
    @JoinColumn(name = "plan_training_exercise_id", nullable = false)
    private TrainingExercise referencedExercise;

    @ManyToOne
    @JoinColumn(name = "training_execution_id", nullable = false)
    private TrainingExecution trainingExecution;

    @OneToMany(mappedBy = "exerciseExecution", cascade = CascadeType.ALL, orphanRemoval = true )
    private Set<TrainingSetExecution> trainingSetExecutions = new HashSet<>();

}
