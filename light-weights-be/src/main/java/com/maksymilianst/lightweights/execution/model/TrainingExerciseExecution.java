package com.maksymilianst.lightweights.execution.model;

import com.maksymilianst.lightweights.plan.model.Exercise;
import com.maksymilianst.lightweights.plan.model.TrainingExercise;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "training_exercise_execution")
@Getter
@Setter
@NoArgsConstructor
public class TrainingExerciseExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 1024)
    private String notes;

    @Column(nullable = false)
    private boolean done;

    @Min(value = 0)
    @Column(nullable = false)
    private Integer sequence;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_training_exercise_id", nullable = false)
    private TrainingExercise referencedExercise;

    @ManyToOne
    @JoinColumn(name = "training_execution_id", nullable = false)
    private TrainingExecution trainingExecution;

    @OneToMany(mappedBy = "exerciseExecution", cascade = CascadeType.ALL, orphanRemoval = true )
    private Set<TrainingSetExecution> trainingSetExecutions = new HashSet<>();

}
