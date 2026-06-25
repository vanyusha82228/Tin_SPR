package edu.java.scrapper.domain.repository;

import edu.java.scrapper.domain.model.Link;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
            (rs, rowNum) -> mapLink(rs)
        );
    }

    public Link findByUrl(URI url) {
        String query = "SELECT id, uri, updated_at, resource_id FROM link WHERE uri = ?";
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> mapLink(rs), url.toString());
        } catch (DataAccessException exception) {
            log.info("Failed to retrieve link by URL", exception);
        }
        return null;
    }

    public Link create(Link link) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO link (uri, updated_at, resource_id) VALUES (?, ?, ?)",
                new String[] {"id"}
            );
            statement.setString(1, link.getUri());
            statement.setObject(2, link.getUpdatedAt());
            statement.setLong(3, link.getResourceId());
            return statement;
        }, keyHolder);

        link.setId(keyHolder.getKey().longValue());
        return link;
    }

    public void updateUpdatedAt(Long linkId, java.time.OffsetDateTime updatedAt) {
        jdbcTemplate.update(
            "UPDATE link SET updated_at = ? WHERE id = ?",
            updatedAt,
            linkId
        );
    }

    private Link mapLink(ResultSet rs) throws SQLException {
        Link link = new Link();
        link.setId(rs.getLong("id"));
        link.setUri(rs.getString("uri"));
        link.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime().atOffset(ZoneOffset.UTC));
        link.setResourceId(rs.getLong("resource_id"));
        return link;
    }
}
