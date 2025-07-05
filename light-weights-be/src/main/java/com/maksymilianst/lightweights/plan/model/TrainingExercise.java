package com.maksymilianst.lightweights.plan.model;

import com.maksymilianst.lightweights.execution.model.TrainingExerciseExecution;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "training_exercise")
@Getter
@Setter
@NoArgsConstructor
public class TrainingExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @OneToMany(mappedBy = "exercise")
    private Set<TrainingSet> sets = new HashSet<>();

    @OneToOne(mappedBy = "referencedExercise", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    TrainingExerciseExecution execution;
}
