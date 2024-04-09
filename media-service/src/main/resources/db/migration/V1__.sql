CREATE TABLE base_image
(
    dtype              VARCHAR(31)  NOT NULL,
    id                 VARCHAR(36)  NOT NULL,
    created_date       datetime     NULL,
    hq_url             VARCHAR(255) NOT NULL,
    last_modified_date datetime     NULL,
    lq_url             VARCHAR(255) NOT NULL,
    mq_url             VARCHAR(255) NOT NULL,
    version            BIGINT       NULL,
    event_id           VARCHAR(36)  NULL,
    post_id            VARCHAR(36)  NULL,
    CONSTRAINT PK_BASE_IMAGE PRIMARY KEY (id)
);

CREATE TABLE category
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_date       datetime              NULL,
    last_modified_date datetime              NULL,
    specific_type      VARCHAR(255)          NULL,
    type               VARCHAR(255)          NOT NULL,
    version            BIGINT                NULL,
    CONSTRAINT PK_CATEGORY PRIMARY KEY (id)
);

CREATE TABLE event
(
    id                 VARCHAR(36)   NOT NULL,
    capacity           INT           NULL,
    created_date       datetime      NULL,
    details            VARCHAR(2500) NULL,
    duration           INT           NULL,
    last_modified_date datetime      NULL,
    max_delay          INT           NULL,
    name               VARCHAR(250)  NULL,
    recurrence_type    SMALLINT      NULL,
    start_date         timestamp     NULL,
    type               SMALLINT      NULL,
    version            BIGINT        NULL,
    creator_id         VARCHAR(36)   NULL,
    location_id        VARCHAR(36)   NULL,
    CONSTRAINT PK_EVENT PRIMARY KEY (id)
);

CREATE TABLE event_attendant
(
    user_id  VARCHAR(36) NOT NULL,
    event_id VARCHAR(36) NOT NULL,
    CONSTRAINT PK_EVENT_ATTENDANT PRIMARY KEY (user_id, event_id)
);

CREATE TABLE event_category
(
    event_id    VARCHAR(36) NOT NULL,
    category_id BIGINT      NOT NULL,
    CONSTRAINT PK_EVENT_CATEGORY PRIMARY KEY (event_id, category_id)
);

CREATE TABLE event_participant
(
    user_id  VARCHAR(36) NOT NULL,
    event_id VARCHAR(36) NOT NULL,
    CONSTRAINT PK_EVENT_PARTICIPANT PRIMARY KEY (user_id, event_id)
);

CREATE TABLE image_tag
(
    tag_id   VARCHAR(36) NOT NULL,
    image_id VARCHAR(36) NOT NULL,
    CONSTRAINT PK_IMAGE_TAG PRIMARY KEY (tag_id, image_id)
);

CREATE TABLE location
(
    id          VARCHAR(36)  NOT NULL,
    address     VARCHAR(255) NULL,
    city        VARCHAR(255) NULL,
    country     VARCHAR(255) NULL,
    county      VARCHAR(255) NULL,
    latitude    DOUBLE       NULL,
    longitude   DOUBLE       NULL,
    name        VARCHAR(255) NULL,
    postal_code VARCHAR(255) NULL,
    state       VARCHAR(255) NULL,
    CONSTRAINT PK_LOCATION PRIMARY KEY (id)
);

CREATE TABLE post
(
    id                 VARCHAR(36)  NOT NULL,
    content            VARCHAR(500) NULL,
    created_date       datetime     NULL,
    last_modified_date datetime     NULL,
    version            BIGINT       NULL,
    creator_id         VARCHAR(36)  NULL,
    event_id           VARCHAR(36)  NULL,
    CONSTRAINT PK_POST PRIMARY KEY (id)
);

CREATE TABLE story
(
    id                 VARCHAR(36)  NOT NULL,
    created_date       datetime     NULL,
    event_id           VARCHAR(255) NULL,
    image_url          VARCHAR(255) NULL,
    last_modified_date datetime     NULL,
    low_res_image_url  VARCHAR(255) NULL,
    version            BIGINT       NULL,
    CONSTRAINT PK_STORY PRIMARY KEY (id)
);

CREATE TABLE tag
(
    id                 VARCHAR(36)  NOT NULL,
    created_date       datetime     NULL,
    last_modified_date datetime     NULL,
    name               VARCHAR(255) NULL,
    version            BIGINT       NULL,
    CONSTRAINT PK_TAG PRIMARY KEY (id)
);

CREATE TABLE user
(
    id                 VARCHAR(36)  NOT NULL,
    created_date       datetime     NULL,
    deletion_date      datetime     NULL,
    is_hotshot         BIT(1)       NULL,
    is_verified        BIT(1)       NULL,
    last_modified_date datetime     NULL,
    photo              VARCHAR(255) NULL,
    uid                VARCHAR(255) NOT NULL,
    username           VARCHAR(255) NOT NULL,
    CONSTRAINT PK_USER PRIMARY KEY (id),
    UNIQUE (uid)
);

CREATE INDEX FK3v4llf5c3wukq0k29f522erpe ON post (event_id);

CREATE INDEX FK5hxneasi6gucfdlc8690c1ngc ON event_participant (event_id);

CREATE INDEX FKbb6c0h5nhs5og47iem617ehrl ON event (location_id);

CREATE INDEX FKc1id70a3tmw7dsuh59u88iqrt ON event_attendant (event_id);

CREATE INDEX FKcenymva895udrn9d7knpdy43g ON base_image (event_id);

CREATE INDEX FKe6ktkrqeepa1ou9elpvugha4y ON base_image (post_id);

CREATE INDEX FKfnvc9jgli0tyfktl08q7h5rgd ON event (creator_id);

CREATE INDEX FKko69comcms0m0mb60dprlj95r ON post (creator_id);

CREATE INDEX FKpwl2b1ylc09urqr0c4n18io ON event_category (category_id);

CREATE INDEX FKrtldke894tsochj8onn1606k2 ON image_tag (image_id);

ALTER TABLE event_category
    ADD CONSTRAINT FK24ud2uucu4h8ga1ois1mnalo8 FOREIGN KEY (event_id) REFERENCES event (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE image_tag
    ADD CONSTRAINT FK28yowgjl7oksr7dc0wj7f5il FOREIGN KEY (tag_id) REFERENCES tag (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE post
    ADD CONSTRAINT FK3v4llf5c3wukq0k29f522erpe FOREIGN KEY (event_id) REFERENCES event (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE event_participant
    ADD CONSTRAINT FK5hxneasi6gucfdlc8690c1ngc FOREIGN KEY (event_id) REFERENCES event (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE event_attendant
    ADD CONSTRAINT FK8ov7as1smqd3468vxv4ajfsql FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE event
    ADD CONSTRAINT FKbb6c0h5nhs5og47iem617ehrl FOREIGN KEY (location_id) REFERENCES location (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE event_attendant
    ADD CONSTRAINT FKc1id70a3tmw7dsuh59u88iqrt FOREIGN KEY (event_id) REFERENCES event (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE base_image
    ADD CONSTRAINT FKcenymva895udrn9d7knpdy43g FOREIGN KEY (event_id) REFERENCES event (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE base_image
    ADD CONSTRAINT FKe6ktkrqeepa1ou9elpvugha4y FOREIGN KEY (post_id) REFERENCES post (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE event
    ADD CONSTRAINT FKfnvc9jgli0tyfktl08q7h5rgd FOREIGN KEY (creator_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE event_participant
    ADD CONSTRAINT FKhwcglexuoexhrbe9728pn6jqb FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE post
    ADD CONSTRAINT FKko69comcms0m0mb60dprlj95r FOREIGN KEY (creator_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE event_category
    ADD CONSTRAINT FKpwl2b1ylc09urqr0c4n18io FOREIGN KEY (category_id) REFERENCES category (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE image_tag
    ADD CONSTRAINT FKrtldke894tsochj8onn1606k2 FOREIGN KEY (image_id) REFERENCES base_image (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
