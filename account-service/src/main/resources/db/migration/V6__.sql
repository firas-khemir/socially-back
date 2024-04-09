ALTER TABLE user
    DROP COLUMN display_name;

ALTER TABLE user
    ADD display_name VARCHAR(36) DEFAULT 'John Doe' NOT NULL;

ALTER TABLE user
    MODIFY display_name VARCHAR(36) NOT NULL;

ALTER TABLE user
    ALTER display_name SET DEFAULT 'John Doe';
