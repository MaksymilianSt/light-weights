CREATE TABLE exercise
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(55) NOT NULL,
    training_plan_id INT         NOT NULL,

    FOREIGN KEY (training_plan_id) REFERENCES training_plan (id)
);

ALTER TABLE training_exercise
    ADD COLUMN exercise_id INT REFERENCES exercise (id);

ALTER TABLE training_exercise_execution
    ADD COLUMN exercise_id INT REFERENCES exercise (id);