package edu.java.scrapper.domain.repository;

import edu.java.scrapper.domain.model.Resource;
import java.sql.PreparedStatement;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    public Resource findByName(String name) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT id, name FROM resource WHERE name = ?",
                new BeanPropertyRowMapper<>(Resource.class),
                name
            );
        } catch (DataAccessException e) {
            log.info("Failed to retrieve resource by name", e);
            return null;
        }
    }

    public Resource createIfAbsent(String name) {
        Resource resource = findByName(name);
        if (resource != null) {
            return resource;
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO resource (name) VALUES (?)",
                new String[] {"id"}
            );
            statement.setString(1, name);
            return statement;
        }, keyHolder);

        Resource createdResource = new Resource();
        createdResource.setId(keyHolder.getKey().longValue());
        createdResource.setName(name);
        return createdResource;
    }
}
