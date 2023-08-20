package database;

import org.h2.tools.Server;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2config {

    private static final String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "sa";
    private static final String INIT_SCRIPT = "backend/src/main/resources/init.sql";

    public H2config() {
        try {
            Server.createWebServer("-trace", "-webAllowOthers").start();
            initDatabase();
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException("Error initializing database: " + e.getMessage(), e);
        }
    }

    private void initDatabase() throws SQLException, FileNotFoundException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            RunScript.execute(connection, new FileReader(INIT_SCRIPT));
        }
    }
}