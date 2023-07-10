package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private final static String URL = "jdbc:mysql://localhost:3306/chat_room";
    private final static Properties properties = new Properties();
    private static DBConnection dbConnection;
    private final Connection connection;

    static {
        properties.setProperty("user", "root");
        properties.setProperty("password", "1234");
    }

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, properties);
    }

    public static DBConnection getInstance() throws SQLException {
        return dbConnection == null ? dbConnection = new DBConnection() : dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }
}
