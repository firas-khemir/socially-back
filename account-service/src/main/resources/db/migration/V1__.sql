CREATE TABLE user
(
    id                 VARCHAR(36)  NOT NULL,
    uid                VARCHAR(255) NOT NULL,
    username           VARCHAR(255) NOT NULL,
    full_name           VARCHAR(255) NOT NULL,
    email              VARCHAR(255) NOT NULL,
    phone              VARCHAR(255) NOT NULL,
    photo              VARCHAR(255) NULL,
    birthdate          date         NULL,
    is_verified        BIT(1)       NULL,
    is_hotshot         BIT(1)       NULL,
    created_date       datetime     NULL,
    last_modified_date datetime     NULL,
    deletion_date      datetime     NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_follower
(
    follower_id VARCHAR(36) NOT NULL,
    user_id     VARCHAR(36) NOT NULL,
    CONSTRAINT pk_user_follower PRIMARY KEY (follower_id, user_id)
);

CREATE TABLE user_following
(
    following_id VARCHAR(36) NOT NULL,
    user_id      VARCHAR(36) NOT NULL,
    CONSTRAINT pk_user_following PRIMARY KEY (following_id, user_id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE user
    ADD CONSTRAINT uc_user_phone UNIQUE (phone);

ALTER TABLE user
    ADD CONSTRAINT uc_user_uid UNIQUE (uid);

ALTER TABLE user_follower
    ADD CONSTRAINT fk_usefol_on_follower FOREIGN KEY (follower_id) REFERENCES user (id);

ALTER TABLE user_following
    ADD CONSTRAINT fk_usefol_on_following FOREIGN KEY (following_id) REFERENCES user (id);

ALTER TABLE user_following
    ADD CONSTRAINT fk_usefol_on_user FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user_follower
    ADD CONSTRAINT fk_usefol_on_userNbvyv6 FOREIGN KEY (user_id) REFERENCES user (id);
