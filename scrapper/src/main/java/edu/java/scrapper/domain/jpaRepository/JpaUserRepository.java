package edu.java.scrapper.domain.jpaRepository;

import edu.java.scrapper.domain.modelJpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {
    User findUserByChatId(long tgChatId);
}
