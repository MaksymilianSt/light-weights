package com.maksymilianst.lightweights.plan.model;

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

    private String name;

    private String description;


    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @OneToMany(mappedBy = "trainingExercise")
    private Set<TrainingSet> sets = new HashSet<>();
}
