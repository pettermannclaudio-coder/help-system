package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {

    private static final String URL =
            "jdbc:mysql://localhost:3306/helpdesk";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static ConnectionFactory getConnection() {
        try {

            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco:", e);
        }

    }

}