CREATE TABLE training_execution
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(55) NOT NULL,
    notes            VARCHAR(1024) NULL,
    realization_date DATE        NOT NULL,
    plan_training_id INT,

    FOREIGN KEY (plan_training_id) REFERENCES training (id)
);

CREATE TABLE training_exercise_execution
(
    id                        SERIAL PRIMARY KEY,
    name                      VARCHAR(55) NOT NULL,
    notes                     VARCHAR(1024) NULL,
    training_execution_id     INT         NOT NULL,
    plan_training_exercise_id INT,

    FOREIGN KEY (training_execution_id) REFERENCES training_execution (id),
    FOREIGN KEY (plan_training_exercise_id) REFERENCES training_exercise (id)
);

CREATE TABLE training_set_execution
(
    id                             SERIAL PRIMARY KEY,
    repetitions                    INT           NOT NULL,
    weight                         DECIMAL(6, 2) NOT NULL,
    rpe                            INT           NOT NULL CHECK (rpe >= 1 AND rpe <= 10),
    sequence                       INT           NOT NULL,
    training_exercise_execution_id INT           NOT NULL,
    plan_training_set_id           INT,

    FOREIGN KEY (training_exercise_execution_id) REFERENCES training_exercise_execution (id),
    FOREIGN KEY (plan_training_set_id) REFERENCES training_set (id)
);
