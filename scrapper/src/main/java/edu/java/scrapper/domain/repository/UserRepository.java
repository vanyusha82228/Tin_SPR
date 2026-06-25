package edu.java.scrapper.domain.repository;

import edu.java.scrapper.domain.model.User;
import java.util.Collections;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class UserRepository implements GenericDao<User> {
    private static final String FAILED_TO_RETRIEVE_USERS = "Failed to retrieve users";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(User user) {
        try {
            jdbcTemplate.update(
                """
                INSERT INTO "user" (telegram_id, username, chat_id)
                VALUES (?, ?, ?)
                """,
                user.getTelegramId(), user.getUsername(), user.getChatId()
            );
        } catch (DataAccessException e) {
            log.error(e);
        }
    }

    @Override
    public void remove(Long id) {
        try {
            jdbcTemplate.update(
                "DELETE FROM \"user\" WHERE id = ?",
                id
            );
        } catch (DataAccessException e) {
            log.error(e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return jdbcTemplate.query(
                """
                SELECT id, telegram_id, username, chat_id
                FROM "user"
                """,
                (rs, rowNum) -> mapToUser(rs)
            );
        } catch (DataAccessException e) {
            log.error(FAILED_TO_RETRIEVE_USERS, e);
            return Collections.emptyList();
        }
    }

    public User findUserByChatId(long chatId) {
        String query = """
                SELECT u.id, u.telegram_id, u.username, u.chat_id
                FROM "user" u
                WHERE u.chat_id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> mapToUser(rs), chatId);
        } catch (DataAccessException e) {
            log.error(FAILED_TO_RETRIEVE_USERS, e);
        }

        return null;
    }

    public void removeByChatId(long chatId) {
        try {
            jdbcTemplate.update(
                """
                DELETE FROM user_link
                WHERE user_id IN (SELECT id FROM "user" WHERE chat_id = ?)
                """,
                chatId
            );
            jdbcTemplate.update("DELETE FROM \"user\" WHERE chat_id = ?", chatId);
        } catch (DataAccessException e) {
            log.error(e);
        }
    }

    private User mapToUser(java.sql.ResultSet rs) throws java.sql.SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setTelegramId(rs.getLong("telegram_id"));
        user.setUsername(rs.getString("username"));
        user.setChatId(rs.getLong("chat_id"));
        return user;
    }
}
