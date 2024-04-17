package edu.java.domain.dao;

import edu.java.domain.model.Chat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChatDao implements GenericDao<Chat> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ChatDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Chat chat) {
        jdbcTemplate.update(
            "INSERT INTO chat (id, created_at) VALUES (?, ?)",
            chat.getId(), chat.getCreatedAt()
        );
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update("DELETE FROM chat WHERE id = ?", id);
    }

    @Override
    public List<Chat> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM chat",
            (rs, rowNum) -> {
                Chat chat = new Chat();
                chat.setId(rs.getLong("id"));
                chat.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return chat;
            }
        );
    }
}
