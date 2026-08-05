package service;

import connection.DatabaseInitializer;
import dao.DepartamentoDAO;
import dao.UsuarioDAO;
import model.Departamento;
import model.TipoUsuario;
import model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UsuarioLoginIntegrationTest {

    @TempDir
    static Path diretorioTemporario;

    private static final String EMAIL = "login@example.com";
    private static final String SENHA = "Senha123";
    private static Integer usuarioCriadoId;

    @BeforeAll
    static void prepararBancoEUsuario() {
        Path banco = diretorioTemporario.resolve("login.db");
        System.setProperty("help.db.type", "SQLITE");
        System.setProperty(
                "help.db.url",
                "jdbc:sqlite:" + banco.toAbsolutePath().toString().replace('\\', '/')
        );
        DatabaseInitializer.initialize();

        Departamento departamento = new DepartamentoDAO().listar().getFirst();
        new UsuarioService().cadastrar(
                "Usuário Login",
                EMAIL,
                SENHA,
                departamento
        );

        usuarioCriadoId = new UsuarioDAO().buscarPorEmail(EMAIL).getId();
    }

    @AfterAll
    static void limparBancoEConfiguracao() {
        if (usuarioCriadoId != null) {
            new UsuarioDAO().excluir(usuarioCriadoId);
        }

        System.clearProperty("help.db.type");
        System.clearProperty("help.db.url");
    }

    @Test
    void deveAutenticarUsuarioPersistido() {
        Usuario usuario = new UsuarioService().login(
                EMAIL.toUpperCase(),
                SENHA
        );

        assertNotNull(usuario);
        assertEquals("Usuário Login", usuario.getNome());
        assertEquals(EMAIL, usuario.getEmail());
        assertEquals(TipoUsuario.COMUM, usuario.getTipo());
    }

    @Test
    void deveRejeitarSenhaIncorreta() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new UsuarioService().login(EMAIL, "SenhaErrada123")
        );
    }
}
