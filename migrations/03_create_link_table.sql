CREATE TABLE link (
    id SERIAL PRIMARY KEY,
    uri TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user" (id)
);
