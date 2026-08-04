package model;

import java.time.LocalDateTime;

public class Resposta {

    private Integer id;
    private String descricao;
    private Usuario usuario;
    private Solicitacao solicitacao;
    private LocalDateTime dataResposta;

    public Resposta() {
    }

    public Resposta(Integer id, String descricao, Usuario usuario,
                    Solicitacao solicitacao, LocalDateTime dataResposta) {
        this.id = id;
        this.descricao = descricao;
        this.usuario = usuario;
        this.solicitacao = solicitacao;
        this.dataResposta = dataResposta;
    }

    public Resposta(String descricao, Usuario usuario,
                    Solicitacao solicitacao) {
        this.descricao = descricao;
        this.usuario = usuario;
        this.solicitacao = solicitacao;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public LocalDateTime getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(LocalDateTime dataResposta) {
        this.dataResposta = dataResposta;
    }

    @Override
    public String toString() {
        return descricao;
    }

}