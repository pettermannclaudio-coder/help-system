package model;

public class Usuario {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private Departamento departamento;

    public Usuario() {

    }

    public Usuario(Integer id, String nome, String email, String senha, Departamento departamento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.departamento = departamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", departamento=" + departamento +
                '}';
    }
}
