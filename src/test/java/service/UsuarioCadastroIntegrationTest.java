package service;

import connection.ConnectionFactory;
import dao.DepartamentoDAO;
import dao.UsuarioDAO;
import model.Departamento;
import model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.PasswordUtil;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class UsuarioCadastroIntegrationTest {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Integer usuarioCriadoId;

    @BeforeAll
    static void verificarDisponibilidadeDoBanco() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            assumeTrue(!connection.isClosed());
        } catch (Exception e) {
            assumeTrue(false, "MySQL indisponível em localhost:3306.");
        }
    }

    @AfterEach
    void removerUsuarioCriado() {
        if (usuarioCriadoId != null) {
            usuarioDAO.excluir(usuarioCriadoId);
        }
    }

    @Test
    void deveCadastrarEPersistirUsuarioComSenhaProtegida() {
        List<Departamento> departamentos = new DepartamentoDAO().listar();
        assumeFalse(departamentos.isEmpty(), "Cadastre ao menos um departamento para executar o teste.");

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
        usuarioCriadoId = usuarioPersistido.getId();

        assertEquals("Usuário de Teste", usuarioRetornado.getNome());
        assertEquals(email, usuarioPersistido.getEmail());
        assertEquals(departamento.getId(), usuarioPersistido.getDepartamento().getId());
        assertNotEquals(senha, usuarioPersistido.getSenha());
        assertTrue(PasswordUtil.verificarSenha(senha, usuarioPersistido.getSenha()));
    }
}
