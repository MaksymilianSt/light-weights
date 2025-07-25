package com.maksymilianst.lightweights.plan.model;

import com.maksymilianst.lightweights.execution.model.TrainingExecution;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "training")
@Getter
@Setter
@NoArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 55)
    private String name;

    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "training_block_id", nullable = false)
    private TrainingBlock block;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<TrainingExercise> exercises = new HashSet<>();

    @OneToOne(mappedBy = "referencedTraining", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    TrainingExecution execution;
}
