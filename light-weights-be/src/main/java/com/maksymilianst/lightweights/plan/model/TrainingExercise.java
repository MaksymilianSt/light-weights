package com.maksymilianst.lightweights.plan.model;

import com.maksymilianst.lightweights.execution.model.TrainingExerciseExecution;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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

    @Min(value = 0)
    @Column(nullable = false)
    private Integer sequence;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TrainingSet> sets = new HashSet<>();

    @OneToOne(mappedBy = "referencedExercise", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    TrainingExerciseExecution execution;
}
