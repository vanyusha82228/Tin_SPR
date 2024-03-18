package edu.java.interfaceForClient;

import edu.java.dto.clintsDto.StackOverflowResponseDTO;

public interface StackOverflowClientInterface {
    StackOverflowResponseDTO fetchQuestionInfo(int questionId);
}
