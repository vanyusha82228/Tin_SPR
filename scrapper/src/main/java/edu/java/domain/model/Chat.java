package edu.java.domain.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Chat {
    private Long id;
    private LocalDateTime createdAt;
}
