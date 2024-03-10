package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StackOverflowQuestionDTO {
    @JsonProperty("question_id")
    private int questionId;
    private String title;
    @JsonProperty("creation_date")
    private OffsetDateTime creationDate;
}
