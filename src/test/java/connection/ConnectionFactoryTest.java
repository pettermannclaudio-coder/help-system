package connection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConnectionFactoryTest {

    @TempDir
    Path diretorioTemporario;

    @BeforeEach
    void configurarSqlite() {
        Path banco = diretorioTemporario.resolve("conexao.db");
        System.setProperty("help.db.type", "SQLITE");
        System.setProperty("help.db.url", criarUrl(banco));
        DatabaseInitializer.initialize();
    }

    @AfterEach
    void limparConfiguracao() {
        System.clearProperty("help.db.type");
        System.clearProperty("help.db.url");
    }

    @Test
    void deveAbrirConexaoSqliteComChavesEstrangeirasAtivas() throws Exception {
        try (Connection connection = ConnectionFactory.getConnection()) {
            assertNotNull(connection);
            assertFalse(connection.isClosed());
            assertTrue(connection.getMetaData().getURL().startsWith("jdbc:sqlite:"));

            try (
                    var statement = connection.createStatement();
                    ResultSet resultado = statement.executeQuery("PRAGMA foreign_keys")
            ) {
                assertTrue(resultado.next());
                assertEquals(1, resultado.getInt(1));
            }
        }
    }

    private String criarUrl(Path banco) {
        return "jdbc:sqlite:" + banco.toAbsolutePath().toString().replace('\\', '/');
    }
}
