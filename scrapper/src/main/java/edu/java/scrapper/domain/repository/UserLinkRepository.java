package edu.java.scrapper.domain.repository;

import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.domain.model.User;
import edu.java.scrapper.domain.model.UserLink;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class UserLinkRepository implements GenericDao<UserLink> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserLinkRepository(JdbcTemplate jdbcTemplate) {
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
    }

    public void remove(User user, Link link) {
        jdbcTemplate.update(
            "delete from user_link where user_id = ? and link_id = ?",
            user.getId(),
            link.getId()
        );
    }

    public boolean existsByUserAndLink(User user, Link link) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM user_link WHERE user_id = ? AND link_id = ?",
            Integer.class,
            user.getId(),
            link.getId()
        );
        return count != null && count > 0;
    }

    public List<Link> findLinksByUser(User user) {
        try {
            return jdbcTemplate.query(
                """
                SELECT l.id, l.uri, l.updated_at, l.resource_id
                FROM link l
                JOIN user_link ul ON ul.link_id = l.id
                WHERE ul.user_id = ?
                ORDER BY l.id
                """,
                (rs, rowNum) -> {
                    Link link = new Link();
                    link.setId(rs.getLong("id"));
                    link.setUri(rs.getString("uri"));
                    link.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime().atOffset(ZoneOffset.UTC));
                    link.setResourceId(rs.getLong("resource_id"));
                    return link;
                },
                user.getId()
            );
        } catch (DataAccessException e) {
            log.error(e);
            return Collections.emptyList();
        }
    }

    public List<Long> findChatIdsByLink(Link link) {
        try {
            return jdbcTemplate.query(
                """
                SELECT u.chat_id
                FROM "user" u
                JOIN user_link ul ON ul.user_id = u.id
                WHERE ul.link_id = ?
                ORDER BY u.chat_id
                """,
                (rs, rowNum) -> rs.getLong("chat_id"),
                link.getId()
            );
        } catch (DataAccessException e) {
            log.error(e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<UserLink> findAll() {
        try {
            return jdbcTemplate.query(
                "SELECT user_id, link_id FROM user_link",
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
