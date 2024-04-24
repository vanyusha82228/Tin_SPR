package edu.java.domain.dao;

import edu.java.domain.model.User;
import java.util.Collections;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class UserDao implements GenericDao<User> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(User user) {
        try {
            jdbcTemplate.update(
                "INSERT INTO user (telegram_id, username, chat_id) VALUES (?, ?, ?)",
                user.getTelegramId(), user.getUsername(), user.getChatId()
            );
        } catch (DataAccessException e) {
            log.error(e);
        }
    }

    @Override
    public void remove(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM user WHERE id = ?", id);
        } catch (DataAccessException e) {
            log.error(e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return jdbcTemplate.query(
                "SELECT * FROM user",
                (rs, rowNum) -> {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setTelegramId(rs.getLong("telegram_id"));
                    user.setUsername(rs.getString("username"));
                    user.setChatId(rs.getLong("chat_id"));
                    return user;
                }
            );
        } catch (DataAccessException e) {
            log.error("Failed to retrieve users", e);
            return Collections.emptyList();
        }
    }
}
