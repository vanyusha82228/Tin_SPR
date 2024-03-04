package edu.java.bot.interfaceForProject;

import java.util.List;

public interface UserRepository {
    List<String> listLinkByUserId(Long userId);

    void addUserLink(Long userId, String link);

    void removeUserLink(Long userId, String link);
}
