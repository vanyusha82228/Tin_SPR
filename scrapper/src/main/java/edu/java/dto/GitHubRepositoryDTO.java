package edu.java.dto;

<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonProperty;
>>>>>>> main
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
<<<<<<< HEAD
=======
    @JsonProperty("updated_at")
>>>>>>> main
    private OffsetDateTime updatedAt;
}
