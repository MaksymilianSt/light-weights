CREATE TABLE app_user
(
    id         SERIAL PRIMARY KEY,
    email      VARCHAR(55) NOT NULL UNIQUE,
    nickname   VARCHAR(55) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE user_role
(
    user_id INT         NOT NULL,
    role    VARCHAR(32) NOT NULL,

    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES app_user (id) ON DELETE CASCADE
);