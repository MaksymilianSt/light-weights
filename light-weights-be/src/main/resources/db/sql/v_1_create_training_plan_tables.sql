CREATE TABLE plan_category
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE training_plan
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    description    TEXT,
    category_id    INT,
    difficulty_lvl varchar(50),
    goal           varchar(50),
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP,

    FOREIGN KEY (category_id) REFERENCES plan_category (id)
);

CREATE TABLE training_block
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(55) NOT NULL,
    description      TEXT,
    start_date       DATE        NOT NULL,
    end_date         DATE        NOT NULL,
    training_plan_id INT         NOT NULL,

    FOREIGN KEY (training_plan_id) REFERENCES training_plan (id)
);

CREATE TABLE training
(
    id                SERIAL PRIMARY KEY,
    name              VARCHAR(55) NOT NULL,
    description       TEXT,
    date              DATE        NOT NULL,
    training_block_id INT         NOT NULL,

    FOREIGN KEY (training_block_id) REFERENCES training_block (id)
);

CREATE TABLE training_exercise
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(55) NOT NULL,
    description TEXT,
    training_id INT         NOT NULL,

    FOREIGN KEY (training_id) REFERENCES training (id)
);

CREATE TABLE training_set
(
    id                   SERIAL PRIMARY KEY,
    repetitions          INT           NOT NULL,
    weight               DECIMAL(6, 2) NOT NULL,
    sequence             INT           NOT NULL,
    training_exercise_id INT           NOT NULL,

    FOREIGN KEY (training_exercise_id) REFERENCES training_exercise (id)
);
