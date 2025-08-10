package com.maksymilianst.lightweights.plan.model;

import com.maksymilianst.lightweights.user.User;
import com.maksymilianst.lightweights.util.audit.AuditInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "training_plan")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("PLAN")
@Getter
@Setter
@NoArgsConstructor
public class TrainingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private PlanCategory category;

    @Column(name = "difficulty_lvl", length = 50)
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLvl;

    @Column(length = 50)
    private String goal;

    @Embedded
    protected AuditInfo auditInfo = new AuditInfo();


    @OneToMany(mappedBy = "trainingPlan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Exercise> exercises = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TrainingBlock> blocks = new HashSet<>();
}
