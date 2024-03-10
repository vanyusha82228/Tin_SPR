package edu.java.dto;

<<<<<<< HEAD
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class StackOverflowQuestionDTO {
    private int questionId;
    private String title;
=======
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
>>>>>>> main
    private OffsetDateTime creationDate;
}
