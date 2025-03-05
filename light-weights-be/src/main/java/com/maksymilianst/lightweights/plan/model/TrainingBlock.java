package com.maksymilianst.lightweights.plan.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "training_block")
@Data
@NoArgsConstructor
public class TrainingBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @Column(name = "start_date")
    private LocalDate start;

    @Column(name = "end_date")
    private LocalDate end;

    @ManyToOne
    @JoinColumn(name = "training_plan_id")
    private TrainingPlan plan;

    @OneToMany(mappedBy = "block")
    Set<Training> trainings = new HashSet<>();

}
