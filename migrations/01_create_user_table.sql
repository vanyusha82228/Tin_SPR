CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    telegram_id BIGINT NOT NULL UNIQUE,
    username TEXT,
    chat_id BIGINT NOT NULL,
    CONSTRAINT fk_chat FOREIGN KEY (chat_id) REFERENCES chat (id)
);
