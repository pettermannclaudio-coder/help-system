package service;

import connection.DatabaseInitializer;
import dao.DepartamentoDAO;
import dao.SolicitacaoDAO;
import dao.UsuarioDAO;

import model.Departamento;
import model.Solicitacao;
import model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolicitacaoDAOTest {

    @TempDir
    static Path diretorioTemporario;

    private static Usuario usuario;
    private static Departamento departamento;

    private SolicitacaoDAO solicitacaoDAO;
    private Solicitacao solicitacao;

    @BeforeAll
    static void prepararBancoEUsuario() {
        Path banco = diretorioTemporario.resolve("solicitacoes.db");
        System.setProperty("help.db.type", "SQLITE");
        System.setProperty(
                "help.db.url",
                "jdbc:sqlite:" + banco.toAbsolutePath().toString().replace('\\', '/'));

        DatabaseInitializer.initialize();
        departamento = new DepartamentoDAO().listar().getFirst();
        usuario = new UsuarioService().cadastrar(
                "Usuário Solicitação",
                "solicitacao@example.com",
                "Senha123",
                departamento);
    }

    @AfterAll
    static void removerUsuarioELimparConfiguracao() {
        if (usuario != null && usuario.getId() != null) {
            new UsuarioDAO().excluir(usuario.getId());
        }

        System.clearProperty("help.db.type");
        System.clearProperty("help.db.url");
    }

    @BeforeEach
    void prepararSolicitacao() {
        solicitacaoDAO = new SolicitacaoDAO();
        solicitacao = new Solicitacao(
                null,
                "Acesso ao sistema",
                "Não consigo acessar o sistema interno.",
                usuario,
                "ABERTA",
                departamento,
                LocalDateTime.of(2026, 8, 4, 15, 0));
    }

    @AfterEach
    void removerSolicitacao() {
        if (solicitacao.getId() != null && solicitacao.getId() > 0) {
            solicitacaoDAO.excluir(solicitacao.getId());
        }
    }

    @Test
    void deveSalvarSolicitacaoNoBanco() {
        solicitacaoDAO.salvar(solicitacao);

        assertNotNull(solicitacao.getId());
        assertTrue(solicitacao.getId() > 0);

        Solicitacao encontrada = solicitacaoDAO.buscarPorId(solicitacao.getId());

        assertNotNull(encontrada);
        assertEquals(solicitacao.getTitulo(), encontrada.getTitulo());
        assertEquals(solicitacao.getDescricao(), encontrada.getDescricao());
        assertEquals(solicitacao.getStatus(), encontrada.getStatus());
        assertEquals(
                solicitacao.getDataCriacao(),
                encontrada.getDataCriacao());
        assertEquals(
                solicitacao.getDepartamento().getId(),
                encontrada.getDepartamento().getId());
        assertEquals(
                solicitacao.getUsuario().getId(),
                encontrada.getUsuario().getId());
    }

    @Test
    void deveBuscarSolicitacaoPorId() {
        solicitacaoDAO.salvar(solicitacao);

        Solicitacao encontrada = solicitacaoDAO.buscarPorId(solicitacao.getId());

        assertNotNull(encontrada);
        assertEquals(solicitacao.getTitulo(), encontrada.getTitulo());
    }

    @Test
    void deveExcluirSolicitacao() {
        solicitacaoDAO.salvar(solicitacao);
        int id = solicitacao.getId();

        solicitacaoDAO.excluir(id);
        solicitacao.setId(null);

        assertNull(solicitacaoDAO.buscarPorId(id));
    }

    @Test
    void deveMarcarSolicitacaoComoResolvida() {
        solicitacaoDAO.salvar(solicitacao);
        solicitacaoDAO.marcarComoResolvida(solicitacao.getId());

        Solicitacao encontrada = solicitacaoDAO.buscarPorId(
                solicitacao.getId());

        assertNotNull(encontrada);
        assertEquals("RESOLVIDA", encontrada.getStatus());
    }
}
