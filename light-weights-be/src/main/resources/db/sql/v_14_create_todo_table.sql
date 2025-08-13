CREATE TABLE todo
(
    id         SERIAL PRIMARY KEY,
    note       TEXT,
    done       BOOLEAN,
    deadline   DATE,
    priority   VARCHAR(16),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    user_id    INTEGER NOT NULL,

    FOREIGN KEY (user_id) REFERENCES app_user (id) ON DELETE CASCADE
);
