package edu.java.scrapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class IntegrationTest {
    public static PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("scrapper")
            .withUsername("postgres")
            .withPassword("postgres");
        POSTGRES.start();

        runMigrations(POSTGRES);
    }

    private static void runMigrations(JdbcDatabaseContainer<?> c) {
        try (Connection conn = DriverManager.getConnection(c.getJdbcUrl(), c.getUsername(), c.getPassword())) {

            Database database =
                DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn));

            Path scrapperPath = Paths.get("").toAbsolutePath();
            Path changelogPath = scrapperPath.getParent().resolve("migrations");
            Liquibase liquibase = new Liquibase(
                "master.xml", new DirectoryResourceAccessor(changelogPath), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}
