package edu.java.scrapper.scheduler;


import edu.java.scrapper.domain.jdbcInterface.LinkUpdater;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@EnableScheduling
public class LinkUpdaterScheduler {
    private final LinkUpdater linkUpdater;

    @Autowired
    public LinkUpdaterScheduler(LinkUpdater linkUpdater) {
        this.linkUpdater = linkUpdater;
    }

    @Scheduled(fixedDelayString = "#{@scheduler.interval.toMillis()}")
    public void update() {
        linkUpdater.update();
    }

}
