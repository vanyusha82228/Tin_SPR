package edu.java.domain.dao;

import edu.java.domain.model.Link;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LinkDao implements GenericDao<Link> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Link link) {
        jdbcTemplate.update(
            "INSERT INTO link (uri, updated_at, resource_id) VALUES (?, ?, ?)",
            link.getUri(), link.getUpdatedAt(), link.getResourceId()
        );
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update("DELETE FROM link WHERE id = ?", id);
    }

    @Override
    public List<Link> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM link",
            (rs, rowNum) -> {
                Link link = new Link();
                link.setId(rs.getLong("id"));
                link.setUri(rs.getString("uri"));
                link.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                link.setResourceId(rs.getLong("resource_id"));
                return link;
            }
        );
    }
}
