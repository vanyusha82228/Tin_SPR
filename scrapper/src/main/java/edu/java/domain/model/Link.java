package edu.java.domain.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Link {
    private Long id;
    private String uri;
    private LocalDateTime updatedAt;
    private Long resourceId;
}
