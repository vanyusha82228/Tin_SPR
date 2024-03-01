package edu.java.bot.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {
    private final Map<Long, String> users = new HashMap<>();
    private final List<String> trackedLinks = new ArrayList<>();

    public Map<Long, String> getUsers() {
        return users;
    }

    public List<String> getTrackedLinks() {
        return trackedLinks;
    }

}
