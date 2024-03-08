package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitHubRepositoryDTO {
    private String name;
    private String description;
    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;
}
