package edu.java.dto.clintsDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StackOverflowResponseDTO {
    private List<StackOverflowQuestionDTO> items;
    @JsonProperty("has_more")
    private boolean hasMore;
    @JsonProperty("quota_max")
    private int quotaMax;
    @JsonProperty("quota_remaining")
    private int quotaRemaining;
}
