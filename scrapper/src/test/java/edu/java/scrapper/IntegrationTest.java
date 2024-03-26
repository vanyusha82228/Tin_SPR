package edu.java.scrapper;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.FileSystemResourceAccessor;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.sql.Connection;
import java.sql.DriverManager;

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
        try {
            // Создаем подключение к базе данных
            Connection conn = DriverManager.getConnection(c.getJdbcUrl(), c.getUsername(), c.getPassword());

            // Создаем объект базы данных для Liquibase
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn));

            // Создаем объект Liquibase с указанием пути к changelog-файлу
            Liquibase liquibase = new Liquibase("F:\\TinkoffSpring\\migrations\\master.xml", new FileSystemResourceAccessor(), database);
            // Применяем миграции
            liquibase.update("");
        } catch (Exception e) {
            e.printStackTrace();
            // Обработка ошибок
        }
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}
