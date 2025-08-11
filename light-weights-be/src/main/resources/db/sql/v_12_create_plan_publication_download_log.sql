CREATE TABLE plan_download
(
    id                  SERIAL PRIMARY KEY,
    download_at         TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
    user_id             INT  NOT NULL,
    plan_publication_id INT  NOT NULL,

    FOREIGN KEY (user_id) REFERENCES app_user (id) ON DELETE CASCADE,
    FOREIGN KEY (plan_publication_id) REFERENCES training_plan (id) ON DELETE CASCADE
);
