package connection;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class ConnectionFactoryTest {

    @Test
    void deveAbrirConexaoComBancoDeDados() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            assertNotNull(connection);
            assertFalse(connection.isClosed());
        } catch (RuntimeException e) {
            assumeTrue(
                    false,
                    "MySQL indisponível em localhost:3306: " + e.getMessage()
            );
        } catch (Exception e) {
            throw new AssertionError("Erro ao verificar a conexão.", e);
        }
    }
}
