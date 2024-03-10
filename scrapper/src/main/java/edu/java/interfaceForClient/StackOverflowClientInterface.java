package edu.java.interfaceForClient;

import edu.java.dto.StackOverflowResponseDTO;

public interface StackOverflowClientInterface {
    StackOverflowResponseDTO fetchQuestionInfo(int questionId);
}
