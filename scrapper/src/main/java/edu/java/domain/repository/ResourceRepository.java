package edu.java.domain.repository;

import edu.java.domain.model.Resource;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class ResourceRepository implements GenericDao<Resource> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResourceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Resource resource) {
        try {
            jdbcTemplate.update(
                "INSERT INTO resource (name) VALUES (?)",
                resource.getName()
            );
        } catch (DataAccessException e) {
            log.error(e);
        }
    }

    @Override
    public void remove(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM resource WHERE id = ?", id);
        } catch (DataAccessException e) {
            log.error(e);
        }
    }

    @Override
    public List<Resource> findAll() {
        return jdbcTemplate.query(
            "SELECT id, name FROM resource",
            (rs, rowNum) -> {
                Resource resource = new Resource();
                resource.setId(rs.getLong("id"));
                resource.setName(rs.getString("name"));
                return resource;
            }
        );
    }
}
