package edu.java.domain.dao;

import edu.java.domain.model.Link;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class LinkDao implements GenericDao<Link> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Link link) {
        try {
            jdbcTemplate.update(
                "INSERT INTO link (uri, updated_at, resource_id) VALUES (?, ?, ?)",
                link.getUri(), link.getUpdatedAt(), link.getResourceId()
            );
        } catch (DataAccessException e) {
            log.error(e);
        }

    }

    @Override
    public void remove(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM link WHERE id = ?", id);
        } catch (DataAccessException e) {
            log.error(e);
        }

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
