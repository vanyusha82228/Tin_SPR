package edu.java.scrapper.domain.jpaRepository;

import edu.java.scrapper.domain.modelJpa.Link;
import edu.java.scrapper.domain.modelJpa.User;
import edu.java.scrapper.domain.modelJpa.UserLink;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserLinkRepository extends JpaRepository<UserLink, Long> {
    void deleteByUserAndLink(User user, Link link);

    Collection<UserLink> findByUser(User user);
}
