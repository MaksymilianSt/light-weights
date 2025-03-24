package com.maksymilianst.lightweights.execution.model;

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

    @Column(nullable = false, length = 55)
    private String name;

    @Column(length = 1024)
    private String notes;


    @OneToOne
    @JoinColumn(name = "plan_training_exercise_id")
    private TrainingExercise referencedExercise;

    @ManyToOne
    @JoinColumn(name = "training_execution_id", nullable = false)
    private TrainingExecution trainingExecution;

    @OneToMany(mappedBy = "exerciseExecution", cascade = CascadeType.ALL, orphanRemoval = true )
    private Set<TrainingSetExecution> trainingSetExecutions = new HashSet<>();

}
