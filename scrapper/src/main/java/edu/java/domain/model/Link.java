package edu.java.domain.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Link {
    private Long id;
    private String uri;
    private LocalDateTime updatedAt;
    private Long resourceId;
}
