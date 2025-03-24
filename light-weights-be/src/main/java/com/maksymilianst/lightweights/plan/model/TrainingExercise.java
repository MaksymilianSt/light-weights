package com.maksymilianst.lightweights.plan.model;

import com.maksymilianst.lightweights.execution.model.TrainingExerciseExecution;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "training_exercise")
@Data
@NoArgsConstructor
public class TrainingExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 55)
    private String name;

    private String description;


    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @OneToMany(mappedBy = "exercise")
    private Set<TrainingSet> sets = new HashSet<>();

    @OneToOne(mappedBy = "referencedExercise", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    TrainingExerciseExecution execution;
}
