package model;

import java.time.LocalDateTime;

public class Solicitacao {

    private Integer id;
    private String titulo;
    private String descricao;
    private Usuario usuario;
    private String status;
    private LocalDateTime dataCriacao;

    public Solicitacao() {
    }

    public Solicitacao(Integer id, String titulo, String descricao,
                       Usuario usuario, String status,
                       LocalDateTime dataCriacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.usuario = usuario;
        this.status = status;
        this.dataCriacao = dataCriacao;
    }

    public Solicitacao(String titulo, String descricao,
                       Usuario usuario, String status) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.usuario = usuario;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return titulo;
    }

}