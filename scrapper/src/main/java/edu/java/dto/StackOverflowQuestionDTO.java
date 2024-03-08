package edu.java.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StackOverflowQuestionDTO {
    private int questionId;
    private String title;
    private OffsetDateTime creationDate;
}
