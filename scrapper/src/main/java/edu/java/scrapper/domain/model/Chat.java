package edu.java.scrapper.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "chat")
public class Chat {
    @Id
    private Long id;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
