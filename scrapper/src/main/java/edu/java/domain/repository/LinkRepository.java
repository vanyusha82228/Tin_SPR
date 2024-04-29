package edu.java.domain.repository;

import edu.java.domain.model.Link;
import java.net.URI;
import java.time.ZoneOffset;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class LinkRepository implements GenericDao<Link> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LinkRepository(JdbcTemplate jdbcTemplate) {
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
            log.error("Failed to add link", e);
        }
    }

    @Override
    public void remove(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM link WHERE id = ?", id);
        } catch (DataAccessException e) {
            log.error("Failed to remove link", e);
        }
    }

    @Override
    public List<Link> findAll() {
        return jdbcTemplate.query(
            "SELECT id, uri, updated_at, resource_id FROM link",
            (rs, rowNum) -> {
                Link link = new Link();
                link.setId(rs.getLong("id"));
                link.setUri(rs.getString("uri"));
                link.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime().atOffset(ZoneOffset.UTC));
                link.setResourceId(rs.getLong("resource_id"));
                return link;
            }
        );
    }

    public Link findByUrl(URI url) {
        String query = "SELECT url FROM links WHERE url = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{url.toString()},
                new BeanPropertyRowMapper<>(Link.class));
        } catch (DataAccessException exception) {
            log.info("Failed to retrieve link by URL", exception);
        }
        return null;
    }
}
