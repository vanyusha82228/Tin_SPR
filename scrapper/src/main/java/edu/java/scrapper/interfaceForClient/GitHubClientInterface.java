package edu.java.scrapper.interfaceForClient;

import edu.java.scrapper.dto.clintsDto.GitHubRepositoryDTO;

public interface GitHubClientInterface {
    GitHubRepositoryDTO fetchRepositoryInfo(String owner, String repositoryName);
}
