package edu.java.domain.dao;

import edu.java.domain.model.UserLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserLinkDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserLinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(UserLink userLink) {
        jdbcTemplate.update(
            "INSERT INTO user_link (user_id, link_id) VALUES (?, ?)",
            userLink.getUserId(), userLink.getLinkId()
        );
    }

}
