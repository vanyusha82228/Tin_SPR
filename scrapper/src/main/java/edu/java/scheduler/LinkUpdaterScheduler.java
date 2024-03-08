package edu.java.scheduler;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@EnableScheduling
public class LinkUpdaterScheduler {


    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {

        log.info("Обновление ссылок...");
    }

}
