package edu.java.scrapper;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import static liquibase.util.Validate.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationEnvironmentTest extends IntegrationTest {
    @Test
    public void testContainerStartup(){
        assertTrue(POSTGRES.isRunning());
    }
    @Test
    public void testTablesExistence() {
        try (Connection conn = DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword())) {
            assertTrue(tableExists(conn, "link"));
            assertTrue(tableExists(conn, "resource"));
            assertTrue(tableExists(conn, "user"));
            assertTrue(tableExists(conn, "chat"));
            assertTrue(tableExists(conn, "user_link"));
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Ошибка при проверке наличия таблиц: " + e.getMessage());
        }
    }

    private boolean tableExists(Connection conn, String tableName) {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
