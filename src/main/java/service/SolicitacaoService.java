package service;

import dao.SolicitacaoDAO;
import model.Solicitacao;
import model.TipoUsuario;
import model.Departamento;
import model.Usuario;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class SolicitacaoService {

    private final SolicitacaoDAO solicitacaoDAO;

    public SolicitacaoService() {
        this.solicitacaoDAO = new SolicitacaoDAO();
    }

    public void salvar(Solicitacao solicitacao) {

        if (solicitacao == null) {
            throw new IllegalArgumentException("A solicitação não pode ser nula.");
        }

        if (solicitacao.getTitulo() == null || solicitacao.getTitulo().isBlank()) {
            throw new IllegalArgumentException("O título é obrigatório.");
        }

        if (solicitacao.getDescricao() == null || solicitacao.getDescricao().isBlank()) {
            throw new IllegalArgumentException("A descrição é obrigatória.");
        }

        if (solicitacao.getDepartamento() == null) {
            throw new IllegalArgumentException("O departamento é obrigatório.");
        }

        if (solicitacao.getUsuario() == null) {
            throw new IllegalArgumentException("O usuário é obrigatório.");
        }

        if (solicitacao.getPrioridade() == null) {
            throw new IllegalArgumentException("A prioridade é obrigatória.");
        }

        if (solicitacao.getStatus() == null || solicitacao.getStatus().isBlank()) {
            solicitacao.setStatus("ABERTA");
        }

        if (solicitacao.getDataCriacao() == null) {
            solicitacao.setDataCriacao(LocalDateTime.now());
        }

        solicitacaoDAO.salvar(solicitacao);
    }

    public void atualizar(Solicitacao solicitacao) {

        if (solicitacao == null) {
            throw new IllegalArgumentException("Solicitação inválida.");
        }

        if (solicitacao.getId() <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        solicitacaoDAO.atualizar(solicitacao);
    }

    public void excluir(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        solicitacaoDAO.excluir(id);
    }

    public Solicitacao buscarPorId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        return solicitacaoDAO.buscarPorId(id);
    }

    public List<Solicitacao> listar() {
        return solicitacaoDAO.listar();
    }

    public List<Solicitacao> listarParaUsuario(
            Usuario usuario,
            Integer departamentoId,
            String status,
            String colaborador,
            boolean ordenarPorPrioridade) {
        validarUsuario(usuario);

        List<Solicitacao> resultado = usuario.getTipo() == TipoUsuario.ADMIN
                ? solicitacaoDAO.listar()
                : solicitacaoDAO.buscarPorUsuario(usuario.getId());
        String nome = colaborador == null
                ? ""
                : colaborador.trim().toLowerCase(Locale.ROOT);

        resultado = resultado.stream()
                .filter(item -> departamentoId == null
                        || item.getDepartamento().getId().equals(departamentoId))
                .filter(item -> status == null || status.isBlank()
                        || item.getStatus().equalsIgnoreCase(status))
                .filter(item -> nome.isBlank()
                        || item.getUsuario().getNome().toLowerCase(Locale.ROOT).contains(nome))
                .toList();

        Comparator<Solicitacao> comparador = ordenarPorPrioridade
                ? Comparator.comparingInt(
                        (Solicitacao item) -> item.getPrioridade().getPeso())
                    .reversed()
                    .thenComparing(Solicitacao::getDataCriacao,
                            Comparator.reverseOrder())
                : Comparator.comparing(Solicitacao::getDataCriacao,
                        Comparator.reverseOrder());

        return resultado.stream().sorted(comparador).toList();
    }

    public void excluirPropria(int solicitacaoId, Usuario usuario) {
        validarUsuario(usuario);
        if (!solicitacaoDAO.excluirDoUsuario(solicitacaoId, usuario.getId())) {
            throw new SecurityException("Somente o autor pode excluir a solicitação.");
        }
    }

    public List<Solicitacao> buscar(
            Departamento departamento,
            String status,
            String colaborador,
            boolean ordenarPorPrioridade
    ) {

        return solicitacaoDAO.buscar(
                departamento,
                status,
                colaborador,
                ordenarPorPrioridade
        );

    }

    public void marcarComoResolvida(int solicitacaoId, Usuario usuario) {
        validarUsuario(usuario);
        if (!solicitacaoDAO.marcarComoResolvidaDoUsuario(
                solicitacaoId, usuario.getId())) {
            throw new SecurityException(
                    "Somente o autor pode marcar a solicitação como resolvida.");
        }
    }

    public void marcarComoRespondida(int solicitacaoId) {
        solicitacaoDAO.marcarComoRespondida(solicitacaoId);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null || usuario.getId() <= 0) {
            throw new SecurityException("Usuário não autenticado.");
        }
    }
}
