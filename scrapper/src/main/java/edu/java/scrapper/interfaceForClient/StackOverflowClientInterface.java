package edu.java.scrapper.interfaceForClient;

import edu.java.scrapper.dto.clintsDto.StackOverflowResponseDTO;

public interface StackOverflowClientInterface {
    StackOverflowResponseDTO fetchQuestionInfo(int questionId);
}
