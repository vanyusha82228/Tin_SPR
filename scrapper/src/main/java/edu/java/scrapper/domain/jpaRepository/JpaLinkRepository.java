package edu.java.scrapper.domain.jpaRepository;

import edu.java.scrapper.domain.model.Link;
import java.net.URI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    Link findByUrl(URI url);
}
