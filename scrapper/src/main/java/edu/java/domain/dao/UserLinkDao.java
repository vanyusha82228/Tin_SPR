package edu.java.domain.dao;

import edu.java.domain.model.UserLink;
import java.util.Collections;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class UserLinkDao implements GenericDao<UserLink> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserLinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(UserLink userLink) {
        try {
            jdbcTemplate.update(
                "INSERT INTO user_link (user_id, link_id) VALUES (?, ?)",
                userLink.getUserId(), userLink.getLinkId()
            );
        } catch (DataAccessException e) {
            log.error(e);
        }
    }

    @Override
    public void remove(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM user_link WHERE id = ?", id);
        } catch (DataAccessException e) {
            log.error(e);
        }
    }

    @Override
    public List<UserLink> findAll() {
        try {
            return jdbcTemplate.query(
                "SELECT * FROM user_link",
                (rs, rowNum) -> {
                    UserLink userLink = new UserLink();
                    userLink.setUserId(rs.getLong("user_id"));
                    userLink.setLinkId(rs.getLong("link_id"));
                    return userLink;
                }
            );
        } catch (DataAccessException e) {
            log.error(e);
            return Collections.emptyList();

        }
    }

}
