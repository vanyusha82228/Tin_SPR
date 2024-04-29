package edu.java.domain.model;

import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class Link {
    private Long id;
    private String uri;
    private OffsetDateTime updatedAt;
    private Long resourceId;
}
