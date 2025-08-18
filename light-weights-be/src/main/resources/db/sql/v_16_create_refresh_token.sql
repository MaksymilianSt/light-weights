CREATE TABLE refresh_token
(
    id             SERIAL PRIMARY KEY,
    token           VARCHAR(1024) NOT NULL,
    user_id    INT,
    expiry_date     TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES app_user (id)
);

CREATE INDEX idx_refresh_token_token
    ON refresh_token (token);

CREATE INDEX idx_refresh_token_user_id
    ON refresh_token (user_id);