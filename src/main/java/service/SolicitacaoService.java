package service;

import dao.SolicitacaoDAO;
import model.Solicitacao;

import java.time.LocalDateTime;
import java.util.List;

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
}