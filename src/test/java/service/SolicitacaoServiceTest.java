package service;

import model.Departamento;
import model.Solicitacao;
import model.Usuario;
import org.junit.jupiter.api.*;

import dao.SolicitacaoDAO;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SolicitacaoDAOTest {

    private SolicitacaoDAO solicitacaoDAO;
    private Solicitacao solicitacao;

    @BeforeEach
    void setUp() {

        solicitacaoDAO = new SolicitacaoDAO();

        Usuario usuario = new Usuario();
        usuario.setId(1);
        Departamento departamento = new Departamento();
        departamento.setId(1);
        departamento.setNome("Teste de banco de dados");
        LocalDateTime dataCriacao = LocalDateTime.of(2026, 8, 4, 15, 0);

        solicitacao = new Solicitacao(
                20,
                "Acesso ao sistema",
                "Não consigo acessar o sistema interno.",
                usuario,
                "ABERTA",
                departamento,
                dataCriacao);
    }

    @AfterEach
    void tearDown() {

        if (solicitacao.getId() > 0) {
            solicitacaoDAO.excluir(solicitacao.getId());
        }
    }

    @Test
    void deveSalvarSolicitacaoNoBanco() {

        solicitacaoDAO.salvar(solicitacao);

        assertTrue(solicitacao.getId() > 0);

        Solicitacao encontrada = solicitacaoDAO.buscarPorId(solicitacao.getId());

        assertNotNull(encontrada);
        assertEquals(solicitacao.getTitulo(), encontrada.getTitulo());
        assertEquals(solicitacao.getDescricao(), encontrada.getDescricao());
        assertEquals(solicitacao.getStatus(), encontrada.getStatus());
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

        Solicitacao encontrada = solicitacaoDAO.buscarPorId(id);

        assertNull(encontrada);

        solicitacao.setId(0);
    }

    @Test
    void deveMarcarSolicitacaoComoResolvida() {

        solicitacaoDAO.salvar(solicitacao);

        solicitacaoDAO.marcarComoResolvida(
                solicitacao.getId());

        Solicitacao encontrada = solicitacaoDAO.buscarPorId(
                solicitacao.getId());

        assertEquals("RESOLVIDA", encontrada.getStatus());
    }
}