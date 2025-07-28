ALTER TABLE training_execution
    RENAME COLUMN realization_date TO execution_start_date;

ALTER TABLE training_execution
    ALTER COLUMN execution_start_date TYPE TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE training_execution
    ALTER COLUMN execution_start_date SET NOT NULL;

ALTER TABLE training_execution
    ADD COLUMN execution_finish_date TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE training_execution
DROP COLUMN done;

ALTER TABLE training_set_execution
    DROP COLUMN done;

ALTER TABLE training_set_execution
    ADD COLUMN executed_at TIMESTAMP WITHOUT TIME ZONE;
