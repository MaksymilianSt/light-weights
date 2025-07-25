package com.maksymilianst.lightweights.plan.model;

import com.maksymilianst.lightweights.execution.model.TrainingSetExecution;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "training_set")
@Getter
@Setter
@NoArgsConstructor
public class TrainingSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer repetitions;

    @Column(nullable = false)
    private BigDecimal weight;

    @Column(length = 7)
    private String tempo;

    @Min(value = 0)
    @Column(nullable = false)
    private Integer sequence;

    @ManyToOne
    @JoinColumn(name = "training_exercise_id", nullable = false)
    private TrainingExercise exercise;

    @OneToOne(mappedBy = "referencedSet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    TrainingSetExecution execution;
}
