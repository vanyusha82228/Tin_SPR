package edu.java.scrapper.repositoryTest;

import edu.java.domain.repository.LinkRepository;
import edu.java.domain.model.Link;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class LinkRepositoryTest extends IntegrationTest {

    @Autowired
    private LinkRepository linkRepository;

    @Test
    @Rollback
    @Transactional
    void addLinkTest() {
        Link link = new Link();
        link.setUri("https://example.com");
        link.setUpdatedAt(LocalDateTime.now());
        link.setResourceId(1L);
        linkRepository.add(link);
        List<Link> links = linkRepository.findAll();
        assertThat(links.contains(link)).isTrue();
    }

    @Test
    @Rollback
    @Transactional
    void removeLinkTest() {
        Link link = new Link();
        link.setUri("https://example.com");
        link.setUpdatedAt(LocalDateTime.now());
        link.setResourceId(1L);
        linkRepository.add(link);
        linkRepository.remove(link.getId());
        List<Link> links = linkRepository.findAll();
        assertThat(links.contains(link)).isFalse();
    }

}
