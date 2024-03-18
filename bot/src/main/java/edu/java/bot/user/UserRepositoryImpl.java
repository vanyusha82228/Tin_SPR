package edu.java.bot.user;

import edu.java.bot.interfaceForProject.UserRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, List<String>> userLinks = new HashMap<>();

    @Override
    public List<String> listLinkByUserId(Long userId) {
        return userLinks.getOrDefault(userId, new ArrayList<>());
    }

    @Override
    public void addUserLink(Long userId, String link) {
        userLinks.computeIfAbsent(userId, k -> new ArrayList<>()).add(link);
    }

    @Override
    public void removeUserLink(Long userId, String link) {
        userLinks.computeIfPresent(userId, (k, v) -> {
            v.remove(link);
            return v;
        });
    }

}
