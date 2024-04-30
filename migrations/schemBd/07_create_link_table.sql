CREATE TABLE user_link (
    user_id BIGINT NOT NULL,
    link_id SERIAL NOT NULL,
    PRIMARY KEY (user_id, link_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT fk_link FOREIGN KEY (link_id) REFERENCES link (id)
);
