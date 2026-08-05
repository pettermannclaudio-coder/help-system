package service;

import connection.DatabaseInitializer;
import dao.DepartamentoDAO;
import dao.UsuarioDAO;
import model.Departamento;
import model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import util.PasswordUtil;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsuarioCadastroIntegrationTest {

    @TempDir
    static Path diretorioTemporario;

    @BeforeAll
    static void prepararBancoSqlite() {
        Path banco = diretorioTemporario.resolve("cadastro.db");
        System.setProperty("help.db.type", "SQLITE");
        System.setProperty(
                "help.db.url",
                "jdbc:sqlite:" + banco.toAbsolutePath().toString().replace('\\', '/')
        );
        DatabaseInitializer.initialize();
    }

    @AfterAll
    static void limparConfiguracao() {
        System.clearProperty("help.db.type");
        System.clearProperty("help.db.url");
    }

    @Test
    void deveCadastrarEPersistirUsuarioComSenhaProtegida() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Departamento> departamentos = new DepartamentoDAO().listar();
        Departamento departamento = departamentos.getFirst();
        String email = "teste+" + UUID.randomUUID() + "@example.com";
        String senha = "Senha123";

        Usuario usuarioRetornado = new UsuarioService().cadastrar(
                "  Usuário de Teste  ",
                email.toUpperCase(),
                senha,
                departamento
        );

        Usuario usuarioPersistido = usuarioDAO.buscarPorEmail(email);

        assertNotNull(usuarioPersistido);
        assertEquals("Usuário de Teste", usuarioRetornado.getNome());
        assertEquals(email, usuarioPersistido.getEmail());
        assertEquals(
                departamento.getId(),
                usuarioPersistido.getDepartamento().getId()
        );
        assertNotEquals(senha, usuarioPersistido.getSenha());
        assertTrue(PasswordUtil.verificarSenha(senha, usuarioPersistido.getSenha()));

        usuarioDAO.excluir(usuarioPersistido.getId());
    }
}
