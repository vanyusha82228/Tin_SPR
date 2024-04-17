package edu.java.domain.dao;

import edu.java.domain.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ResourceDao implements GenericDao<Resource> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResourceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Resource resource) {
        jdbcTemplate.update(
            "INSERT INTO resource (name) VALUES (?)",
            resource.getName()
        );
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update("DELETE FROM resource WHERE id = ?", id);
    }

    @Override
    public List<Resource> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM resource",
            (rs, rowNum) -> {
                Resource resource = new Resource();
                resource.setId(rs.getLong("id"));
                resource.setName(rs.getString("name"));
                return resource;
            }
        );
    }
}
