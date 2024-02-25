package edu.java.dto;

import java.time.OffsetDateTime;

public class GitHubRepositoryDTO {
    private String name;
    private String description;
    private OffsetDateTime updatedAt;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
