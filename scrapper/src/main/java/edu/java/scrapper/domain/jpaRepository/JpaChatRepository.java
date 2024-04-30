package edu.java.scrapper.domain.jpaRepository;

import edu.java.scrapper.domain.modelJpa.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatRepository extends JpaRepository<Chat, Long> {
}
