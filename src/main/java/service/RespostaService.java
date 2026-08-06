package service;

import dao.RespostaDAO;
import model.Resposta;

import java.time.LocalDateTime;
import java.util.List;

public class RespostaService {

    private final RespostaDAO respostaDAO;

    public RespostaService() {
        this.respostaDAO = new RespostaDAO();
    }

    public void salvar(Resposta resposta) {

        validarResposta(resposta);

        if (resposta.getDataResposta() == null) {
            resposta.setDataResposta(LocalDateTime.now());
        }

        respostaDAO.salvar(resposta);
    }

    public void atualizar(Resposta resposta) {

        if (resposta == null) {
            throw new IllegalArgumentException("Resposta inválida.");
        }

        if (resposta.getId() <= 0) {
            throw new IllegalArgumentException("ID da resposta inválido.");
        }

        validarResposta(resposta);

        respostaDAO.atualizar(resposta);
    }

    public void excluir(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        respostaDAO.excluir(id);
    }

    public Resposta buscarPorId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        return respostaDAO.buscarPorId(id);
    }

    public List<Resposta> listar() {
        return respostaDAO.listar();
    }

    private void validarResposta(Resposta resposta) {

        if (resposta == null) {
            throw new IllegalArgumentException("Resposta não pode ser nula.");
        }

        if (resposta.getDescricao() == null || resposta.getDescricao().isBlank()) {
            throw new IllegalArgumentException("O texto da resposta é obrigatório.");
        }

        if (resposta.getUsuario() == null) {
            throw new IllegalArgumentException("O usuário é obrigatório.");
        }

        if (resposta.getUsuario().getId() <= 0) {
            throw new IllegalArgumentException("Usuário inválido.");
        }

        if (resposta.getSolicitacao() == null) {
            throw new IllegalArgumentException("A solicitação é obrigatória.");
        }

        if (resposta.getSolicitacao().getId() <= 0) {
            throw new IllegalArgumentException("Solicitação inválida.");
        }
    }

    public List<Resposta> buscarPorSolicitacao(int solicitacaoId) {
        return respostaDAO.buscarPorSolicitacao(solicitacaoId);
    }

}