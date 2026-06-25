package edu.java.scrapper.domain.repository;

import edu.java.scrapper.domain.model.Chat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class ChatRepository implements GenericDao<Chat> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Chat chat) {
        try {
            jdbcTemplate.update(
                "INSERT INTO chat (id, created_at) VALUES (?, ?)",
                chat.getId(), chat.getCreatedAt()
            );
        } catch (DataAccessException e) {
            log.error(e);
        }
    }

    @Override
    public void remove(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM chat WHERE id = ?", id);
        } catch (DataAccessException e) {
            log.error(e);
        }

    }

    @Override
    public List<Chat> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM chat",
            (rs, rowNum) -> mapToChat(rs)
        );
    }

    public Chat findById(long tgChatId) {
        String query = "SELECT id, created_at FROM chat WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> mapToChat(rs), tgChatId);
        } catch (DataAccessException exception) {
            log.info(exception.getMessage());
        }
        return null;
    }

    private Chat mapToChat(ResultSet rs) throws SQLException {
        Chat chat = new Chat();
        chat.setId(rs.getLong("id"));
        chat.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return chat;
    }
}
