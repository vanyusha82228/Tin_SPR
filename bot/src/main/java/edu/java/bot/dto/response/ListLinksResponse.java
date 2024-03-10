package edu.java.bot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListLinksResponse {
    private List<LinkResponse> links;
    private int size;
}
