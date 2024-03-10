package edu.java.interfaceForClient;

import edu.java.dto.clintsDto.GitHubRepositoryDTO;

public interface GitHubClientInterface {
    GitHubRepositoryDTO fetchRepositoryInfo(String owner, String repositoryName);
}
