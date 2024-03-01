package edu.java.dto;

import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class StackOverflowQuestionDTO {
    private int questionId;
    private String title;
    private OffsetDateTime creationDate;
}
