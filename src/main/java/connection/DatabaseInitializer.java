package connection;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseInitializer {

    private static final String SQLITE_SCHEMA = "/database/sqlite-schema.sql";

    private DatabaseInitializer() {
        throw new IllegalStateException("Classe utilitária não pode ser instanciada.");
    }

    public static void initialize() {
        if (!ConnectionFactory.isSqlite()) {
            return;
        }

        String schema = carregarSchema();

        try (
                Connection connection = ConnectionFactory.getConnection();
                Statement statement = connection.createStatement()
        ) {
            for (String comando : schema.split(";")) {
                if (!comando.isBlank()) {
                    statement.execute(comando.trim());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar o banco SQLite.", e);
        }
    }

    private static String carregarSchema() {
        try (InputStream input = DatabaseInitializer.class
                .getResourceAsStream(SQLITE_SCHEMA)) {
            if (input == null) {
                throw new IllegalStateException(
                        "Schema SQLite não encontrado: " + SQLITE_SCHEMA
                );
            }

            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o schema SQLite.", e);
        }
    }
}
