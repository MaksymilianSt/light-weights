package com.maksymilianst.lightweights.execution.model;

import com.maksymilianst.lightweights.plan.model.TrainingSet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "training_set_execution")
@Data
@NoArgsConstructor
public class TrainingSetExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer repetitions;

    @Column(nullable = false)
    private BigDecimal weight;

    @Column(length = 7)
    private String tempo;

    @Column(nullable = false)
    @Max(10)
    @Min(1)
    private BigDecimal rpe;

    @Min(0)
    @Column(nullable = false)
    private Integer sequence;

    @Column(nullable = false)
    private boolean done;

    @OneToOne
    @JoinColumn(name = "plan_training_set_id", nullable = false)
    private TrainingSet referencedSet;

    @ManyToOne
    @JoinColumn(name = "training_exercise_execution_id", nullable = false)
    private TrainingExerciseExecution exerciseExecution;
}
