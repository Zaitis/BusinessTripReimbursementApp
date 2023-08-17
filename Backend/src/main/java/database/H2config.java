package database;

import org.h2.tools.RunScript;
import org.h2.tools.Server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class H2config {

    public H2config() throws SQLException, FileNotFoundException {
        Server.createWebServer("-trace").start();
        Connection connection = DriverManager.getConnection(
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "sa"
        );
        RunScript.execute(connection, new FileReader("backend/src/main/resources/init.sql"));
        connection.close();
    }
}

