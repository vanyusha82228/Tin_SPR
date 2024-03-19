package edu.java.scrapper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationEnvironmentTest extends IntegrationTest {
    @Test
    public void testContainerStartup(){
        assertTrue(POSTGRES.isRunning());
    }
}
