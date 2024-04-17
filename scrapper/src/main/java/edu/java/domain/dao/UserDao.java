package edu.java.domain.dao;

import edu.java.domain.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao implements GenericDao<User> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(User user) {
        jdbcTemplate.update(
            "INSERT INTO user (telegram_id, username, chat_id) VALUES (?, ?, ?)",
            user.getTelegramId(), user.getUsername(), user.getChatId()
        );
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update("DELETE FROM user WHERE id = ?", id);
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
