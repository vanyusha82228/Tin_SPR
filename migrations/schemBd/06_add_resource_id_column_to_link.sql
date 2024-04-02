ALTER TABLE link
    ADD COLUMN resource_id BIGINT NOT NULL,
    ADD CONSTRAINT fk_resource FOREIGN KEY (resource_id) REFERENCES resource(id);
