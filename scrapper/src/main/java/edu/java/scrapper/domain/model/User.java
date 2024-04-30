package edu.java.scrapper.domain.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private Long telegramId;
    private String username;
    private Long chatId;
}
