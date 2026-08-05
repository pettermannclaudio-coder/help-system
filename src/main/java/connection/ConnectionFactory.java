package connection;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public final class ConnectionFactory {

    private static final String SQLITE = "SQLITE";
    private static final String MYSQL = "MYSQL";
    private static final String SQLITE_URL_PADRAO =
            "jdbc:sqlite:database/helpdesk.db";

    private static final Dotenv DOTENV = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private ConnectionFactory() {
        throw new IllegalStateException("Classe utilitária não pode ser instanciada.");
    }

    public static Connection getConnection() {
        String tipo = getDatabaseType();

        try {
            if (SQLITE.equals(tipo)) {
                Connection connection = DriverManager.getConnection(getDatabaseUrl());

                try {
                    configurarSqlite(connection);
                    return connection;
                } catch (SQLException e) {
                    connection.close();
                    throw e;
                }
            }

            return DriverManager.getConnection(
                    getDatabaseUrl(),
                    getMysqlUser(),
                    getMysqlPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao conectar ao banco " + tipo + ".",
                    e
            );
        }
    }

    public static String getDatabaseType() {
        String tipoConfigurado = obterConfiguracaoOpcional(
                "help.db.type",
                "DB_TYPE"
        );

        if (tipoConfigurado != null) {
            String tipo = tipoConfigurado.toUpperCase(Locale.ROOT);

            if (!SQLITE.equals(tipo) && !MYSQL.equals(tipo)) {
                throw new IllegalArgumentException(
                        "Banco inválido: " + tipo + ". Use SQLITE ou MYSQL."
                );
            }

            if (SQLITE.equals(tipo)) {
                return SQLITE;
            }
        }

        return hasCompleteMysqlConfiguration() ? MYSQL : SQLITE;
    }

    public static String getDatabaseUrl() {
        if (isSqlite()) {
            return obterConfiguracao(
                    "help.db.url",
                    "SQLITE_DB_URL",
                    SQLITE_URL_PADRAO
            );
        }

        return getMysqlUrl();
    }

    public static boolean isSqlite() {
        return SQLITE.equals(getDatabaseType());
    }

    public static boolean hasCompleteMysqlConfiguration() {
        return getMysqlUrl() != null
                && getMysqlUser() != null
                && getMysqlPassword() != null;
    }

    private static String getMysqlUrl() {
        return obterConfiguracaoOpcional("help.db.mysql.url", "DB_URL");
    }

    private static String getMysqlUser() {
        return obterConfiguracaoOpcional("help.db.mysql.user", "DB_USER");
    }

    private static String getMysqlPassword() {
        return obterConfiguracaoOpcional(
                "help.db.mysql.password",
                "DB_PASSWORD"
        );
    }

    private static void configurarSqlite(Connection connection)
            throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
            statement.execute("PRAGMA busy_timeout = 5000");
        }
    }

    private static String obterConfiguracao(
            String propriedade,
            String variavel,
            String valorPadrao) {
        String valor = obterConfiguracaoOpcional(propriedade, variavel);
        return valor == null ? valorPadrao : valor;
    }

    private static String obterConfiguracaoOpcional(
            String propriedade,
            String variavel) {
        String valor = System.getProperty(propriedade);

        if (valor == null || valor.isBlank()) {
            valor = System.getenv(variavel);
        }

        if (valor == null || valor.isBlank()) {
            valor = DOTENV.get(variavel);
        }

        return valor == null || valor.isBlank()
                ? null
                : valor.trim();
    }
}
