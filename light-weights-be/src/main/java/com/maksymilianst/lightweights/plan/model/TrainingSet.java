package com.maksymilianst.lightweights.plan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "training_set")
@Data
@NoArgsConstructor
public class TrainingSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer repetitions;

    private BigDecimal weight;

    @NotNull
    @Min(value = 0)
    private Integer sequence;

    @ManyToOne
    @JoinColumn(name = "training_exercise_id")
    private TrainingExercise trainingExercise;
}
