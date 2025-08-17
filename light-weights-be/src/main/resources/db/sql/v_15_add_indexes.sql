CREATE INDEX idx_training_plan_category_id
    ON training_plan (category_id);
CREATE INDEX idx_training_plan_user_id
    ON training_plan (user_id);
CREATE INDEX idx_training_plan_author_id
    ON training_plan (author_id);

CREATE INDEX idx_training_block_training_plan_id
    ON training_block (training_plan_id);

CREATE INDEX idx_training_training_block_id
    ON training (training_block_id);

CREATE INDEX idx_training_exercise_training_id
    ON training_exercise (training_id);
CREATE INDEX idx_training_exercise_exercise_id
    ON training_exercise (exercise_id);

CREATE INDEX idx_training_set_training_exercise_id
    ON training_set (training_exercise_id);

CREATE INDEX idx_training_execution_plan_training_id
    ON training_execution (plan_training_id);
CREATE INDEX idx_training_execution_user_id
    ON training_execution (user_id);

CREATE INDEX idx_tee_training_execution_id
    ON training_exercise_execution (training_execution_id);
CREATE INDEX idx_tee_plan_training_exercise_id
    ON training_exercise_execution (plan_training_exercise_id);
CREATE INDEX idx_tee_exercise_id
    ON training_exercise_execution (exercise_id);

CREATE INDEX idx_tse_training_exercise_execution_id
    ON training_set_execution (training_exercise_execution_id);
CREATE INDEX idx_tse_plan_training_set_id
    ON training_set_execution (plan_training_set_id);

CREATE INDEX idx_user_role_user_id
    ON user_role (user_id);

CREATE INDEX idx_exercise_training_plan_id
    ON exercise (training_plan_id);

CREATE INDEX idx_plan_download_plan_id_user_id
    ON plan_download (plan_publication_id, user_id);

CREATE INDEX idx_plan_opinion_plan_id_user_id
    ON plan_opinion (plan_publication_id, user_id);

CREATE INDEX idx_todo_user_id
    ON todo (user_id);
