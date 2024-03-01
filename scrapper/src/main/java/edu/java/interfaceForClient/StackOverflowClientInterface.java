package edu.java.interfaceForClient;

import edu.java.dto.StackOverflowQuestionDTO;

public interface StackOverflowClientInterface {
    StackOverflowQuestionDTO fetchQuestionInfo(int questionId);
}
