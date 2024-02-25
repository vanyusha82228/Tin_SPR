package edu.java.interfaceForClient;

import edu.java.dto.GitHubRepositoryDTO;

public interface GitHubClientInterface {
    GitHubRepositoryDTO fetchRepositoryInfo(String owner, String repositoryName);
}
