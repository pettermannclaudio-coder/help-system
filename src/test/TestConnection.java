package Test;

import connection.ConnectionFactory;
import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try (Connection connection = ConnectionFactory.getConnection()) {

            if (connection != null) {
                System.out.println("Conexão realizada com sucesso!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}