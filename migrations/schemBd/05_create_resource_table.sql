
-- Таблица для хранения информации о ресурсах.
-- в ней есть Уникальный идентификатор ресурса.
-- и Название ресурса.

CREATE TABLE resource (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);
