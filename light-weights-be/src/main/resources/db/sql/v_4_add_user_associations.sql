ALTER TABLE training_plan
    ADD COLUMN user_id INT REFERENCES app_user (id) ON DELETE CASCADE;

ALTER TABLE training_execution
    ADD COLUMN user_id INT REFERENCES app_user (id) ON DELETE CASCADE;
