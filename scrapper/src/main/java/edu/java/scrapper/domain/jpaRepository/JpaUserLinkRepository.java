package edu.java.scrapper.domain.jpaRepository;

import edu.java.scrapper.domain.model.User;
import edu.java.scrapper.domain.model.UserLink;
import java.net.URI;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaUserLinkRepository extends JpaRepository<UserLink, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM UserLink ul WHERE ul.userId.chatId = :tgChatId AND ul.link.url = :url")
    void deleteByChatIdAndUrl(@Param("tgChatId") long tgChatId, @Param("url") URI url);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_link (user_id, link_id) VALUES (:chatId, (SELECT id FROM link WHERE uri = :url))",
           nativeQuery = true)
    UserLink addByChatIdAndUrl(@Param("chatId") long chatId, @Param("url") URI url);

    @Query("SELECT ul FROM UserLink ul WHERE ul.userId = :user")
    Collection<UserLink> findByUser(@Param("user") User user);
}
