package model;

import java.time.LocalDateTime;

public class Solicitacao {

    private Integer id;
    private String titulo;
    private String descricao;
    private Departamento departamento;
    private Usuario usuario;
    private String status;
    private PrioridadeSolicitacao prioridade = PrioridadeSolicitacao.MEDIA;
    private LocalDateTime dataCriacao;

    public Solicitacao() {
    }

    public Solicitacao(Integer id, String titulo, String descricao,
                       Usuario usuario, String status, Departamento departamento,
                       LocalDateTime dataCriacao, String prioridade) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.usuario = usuario;
        this.status = status;
        this.departamento = departamento;
        this.dataCriacao = dataCriacao;
        setPrioridade(prioridade);
    }

    public Solicitacao(Integer id, String titulo, String descricao,
                       Usuario usuario, String status, Departamento departamento,
                       LocalDateTime dataCriacao) {
        this(id, titulo, descricao, usuario, status, departamento,
                dataCriacao, PrioridadeSolicitacao.MEDIA.name());
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

    public PrioridadeSolicitacao getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade == null || prioridade.isBlank()
                ? PrioridadeSolicitacao.MEDIA
                : PrioridadeSolicitacao.valueOf(prioridade.toUpperCase());
    }

    public void setPrioridade(PrioridadeSolicitacao prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return titulo;
    }

}
