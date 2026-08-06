package connection;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseInitializer {

    private static final String SQLITE_SCHEMA = "/database/sqlite-schema.sql";
    private static final String SQLITE_DATA = "/database/sqlite-data.sql";

    private DatabaseInitializer() {
        throw new IllegalStateException("Classe utilitária não pode ser instanciada.");
    }

    public static void initialize() {
        if (!ConnectionFactory.isSqlite()) {
            return;
        }

        try (
                Connection connection = ConnectionFactory.getConnection();
                Statement statement = connection.createStatement()
        ) {
            executarArquivoSql(
                    statement,
                    SQLITE_SCHEMA
            );

            adicionarPrioridadeSeNecessario(statement);

            executarArquivoSql(
                    statement,
                    SQLITE_DATA
            );
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar o banco SQLite.", e);
        }
    }

    private static void adicionarPrioridadeSeNecessario(Statement statement)
            throws SQLException {
        boolean possuiPrioridade = false;
        try (ResultSet colunas = statement.executeQuery(
                "PRAGMA table_info(solicitacao)")) {
            while (colunas.next()) {
                if ("prioridade".equalsIgnoreCase(colunas.getString("name"))) {
                    possuiPrioridade = true;
                    break;
                }
            }
        }

        if (!possuiPrioridade) {
            statement.execute("""
                    ALTER TABLE solicitacao
                    ADD COLUMN prioridade TEXT NOT NULL DEFAULT 'MEDIA'
                    CHECK (prioridade IN ('BAIXA', 'MEDIA', 'ALTA'))
                    """);
        }
    }

    private static void executarArquivoSql(
            Statement statement,
            String arquivo
    ) {

        String sql = carregarArquivo(arquivo);


        for (String comando : sql.split(";")) {

            if (!comando.isBlank()) {

                try {

                    statement.execute(
                            comando.trim()
                    );

                } catch (SQLException e) {

                    throw new RuntimeException(
                            "Erro executando comando SQL: "
                                    + comando,
                            e
                    );

                }

            }

        }

    }


    private static String carregarArquivo(
            String caminho
    ) {

        try (
                InputStream input =
                        DatabaseInitializer.class
                                .getResourceAsStream(caminho)
        ) {


            if (input == null) {

                throw new IllegalStateException(
                        "Arquivo SQL não encontrado: "
                                + caminho
                );

            }


            return new String(
                    input.readAllBytes(),
                    StandardCharsets.UTF_8
            );


        } catch (IOException e) {

            throw new RuntimeException(
                    "Erro ao ler arquivo SQL.",
                    e
            );

        }

    }
}
