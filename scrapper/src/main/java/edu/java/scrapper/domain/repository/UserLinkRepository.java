package edu.java.scrapper.domain.repository;

import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.domain.model.User;
import edu.java.scrapper.domain.model.UserLink;
import java.net.URI;
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
    private final static String USER_ID = "user_id";
    private final static String LINK_ID = "link_id";

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

    public void remove(long tgChatId, URI url) {
        try {
            String query = """
                DELETE ul
                FROM user_link ul
                JOIN user u ON ul.user_id = u.id
                JOIN link l ON ul.link_id = l.id
                JOIN chat c ON u.chat_id = c.id
                WHERE c.telegram_id = ? AND l.uri = ?
                """;

            jdbcTemplate.update(query, tgChatId, url.toString());
        } catch (DataAccessException e) {
            log.error("Failed to remove UserLink", e);
        }
    }

    @Override
    public List<UserLink> findAll() {
        try {
            return jdbcTemplate.query(
                "SELECT user_id, link_id FROM user_link",
                (rs, rowNum) -> {
                    UserLink userLink = new UserLink();
                    // Получаем объекты User и Link по их идентификаторам
                    User user = new User();
                    user.setId(rs.getLong(USER_ID));
                    userLink.setUserId(user);

                    Link link = new Link();
                    link.setId(rs.getLong(LINK_ID));
                    userLink.setLinkId(link);

                    return userLink;
                }
            );
        } catch (DataAccessException e) {
            log.error(e);
            return Collections.emptyList();
        }
    }

    public UserLink addUserLink(long tgChatId, URI url) {
        try {
            // Создаем JOIN запрос для добавления нового UserLink
            String query = """
                INSERT INTO user_link (user_id, link_id)
                SELECT u.id, l.id
                FROM user u
                JOIN link l ON l.uri = ?
                JOIN chat c ON c.id = u.chat_id
                WHERE c.telegram_id = ? AND l.uri = ?
                """;

            jdbcTemplate.update(query, url.toString(), tgChatId, url.toString());
            return findUserLinkByChatIdAndUrl(tgChatId, url);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public UserLink findUserLinkByChatIdAndUrl(long tgChatId, URI url) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT ul.user_id AS user_id, ul.link_id AS link_id "
                    + "FROM user_link ul "
                    + "JOIN user u ON ul.user_id = u.id "
                    + "JOIN link l ON ul.link_id = l.id "
                    + "WHERE u.telegram_id = ? AND l.uri = ?",
                new Object[] {tgChatId, url.toString()},
                (rs, rowNum) -> {
                    UserLink userLink = new UserLink();
                    User user = new User();
                    user.setId(rs.getLong(USER_ID));
                    userLink.setUserId(user);

                    Link link = new Link();
                    link.setId(rs.getLong(LINK_ID));
                    userLink.setLinkId(link);

                    return userLink;
                }
            );
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
