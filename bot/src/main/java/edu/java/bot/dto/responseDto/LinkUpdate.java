package edu.java.bot.dto.responseDto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkUpdate {
    @NonNull
    private long id;
    @NotEmpty
    private String url;
    private String description;
    private List<Long> tgChatIds;
}
