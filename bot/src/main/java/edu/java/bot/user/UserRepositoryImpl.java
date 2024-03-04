package edu.java.bot.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.java.bot.interfaceForProject.UserRepository;
import io.swagger.v3.oas.models.links.Link;
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
        List<String> links = userLinks.getOrDefault(userId,new ArrayList<>());
        links.add(link);
        userLinks.put(userId,links);
    }

    @Override
    public void removeUserLink(Long userId, String link) {
        List<String> links = userLinks.getOrDefault(userId, new ArrayList<>());
        links.remove(link);
        userLinks.put(userId, links);
    }
    
}
