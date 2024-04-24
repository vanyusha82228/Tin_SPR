package edu.java.scrapper.daoTest;

import edu.java.domain.dao.LinkDao;
import edu.java.domain.model.Link;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@SpringBootTest
public class LinkDaoTest extends IntegrationTest {

    @Autowired
    private LinkDao linkDao;

    @Test
    @Rollback
    @Transactional
    void addLinkTest() {
        Link link = new Link();
        link.setUri("https://example.com");
        link.setUpdatedAt(LocalDateTime.now());
        link.setResourceId(1L);


    }

}
