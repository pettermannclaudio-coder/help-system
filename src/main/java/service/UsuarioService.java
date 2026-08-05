package service;

import dao.UsuarioDAO;
import model.Departamento;
import model.TipoUsuario;
import model.Usuario;
import util.PasswordUtil;
import util.ValidationUtil;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public UsuarioService(UsuarioDAO usuarioDAO) {
        if (usuarioDAO == null) {
            throw new IllegalArgumentException(
                    "UsuarioDAO não pode ser nulo.");
        }

        this.usuarioDAO = usuarioDAO;
    }

    public Usuario cadastrar(
            String nome,
            String email,
            String senha,
            Departamento departamento) {
        return cadastrar(
                nome,
                email,
                senha,
                TipoUsuario.COMUM,
                departamento
        );
    }

    public Usuario cadastrar(
            String nome,
            String email,
            String senha,
            TipoUsuario tipo,
            Departamento departamento) {
        String nomeValidado = ValidationUtil.validarENormalizarNome(nome);

        String emailValidado = ValidationUtil.validarENormalizarEmail(email);

        ValidationUtil.validarSenha(senha);
        ValidationUtil.validarTipoUsuario(tipo);
        validarDepartamento(departamento);

        Usuario usuarioExistente = usuarioDAO.buscarPorEmail(emailValidado);

        if (usuarioExistente != null) {
            throw new IllegalArgumentException(
                    "Já existe um usuário cadastrado com este e-mail.");
        }

        String senhaHash = PasswordUtil.gerarHash(senha);

        Usuario usuario = new Usuario();

        usuario.setNome(nomeValidado);
        usuario.setEmail(emailValidado);
        usuario.setSenha(senhaHash);
        usuario.setTipo(tipo);
        usuario.setDepartamento(departamento);

        usuarioDAO.salvar(usuario);

        return usuario;
    }

    public Usuario login(
            String email,
            String senha) {
        String emailValidado = ValidationUtil.validarENormalizarEmail(email);

        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException(
                    "Informe a senha.");
        }

        Usuario usuario = usuarioDAO.buscarPorEmail(emailValidado);

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "E-mail ou senha inválidos.");
        }

        boolean senhaCorreta = PasswordUtil.verificarSenha(
                senha,
                usuario.getSenha());

        if (!senhaCorreta) {
            throw new IllegalArgumentException(
                    "E-mail ou senha inválidos.");
        }

        return usuario;
    }

    public Usuario buscarPorId(int id) {
        validarId(id);

        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Usuário não encontrado.");
        }

        return usuario;
    }

    public void atualizar(
            Usuario usuario,
            String novaSenha) {
        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Usuário inválido.");
        }

        validarId(usuario.getId());

        String nomeValidado = ValidationUtil.validarENormalizarNome(
                usuario.getNome());

        String emailValidado = ValidationUtil.validarENormalizarEmail(
                usuario.getEmail());

        validarDepartamento(
                usuario.getDepartamento());

        Usuario usuarioComMesmoEmail = usuarioDAO.buscarPorEmail(emailValidado);

        if (usuarioComMesmoEmail != null
                && usuarioComMesmoEmail.getId() != usuario.getId()) {
            throw new IllegalArgumentException(
                    "Este e-mail já está sendo utilizado.");
        }

        usuario.setNome(nomeValidado);
        usuario.setEmail(emailValidado);

        if (novaSenha != null
                && !novaSenha.isBlank()) {
            ValidationUtil.validarSenha(novaSenha);

            usuario.setSenha(
                    PasswordUtil.gerarHash(novaSenha));
        } else {
            Usuario usuarioSalvo = usuarioDAO.buscarPorId(usuario.getId());

            if (usuarioSalvo == null) {
                throw new IllegalArgumentException(
                        "Usuário não encontrado.");
            }

            usuario.setSenha(
                    usuarioSalvo.getSenha());
        }

        usuarioDAO.atualizar(usuario);
    }

    public void excluir(int id) {
        validarId(id);

        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Usuário não encontrado.");
        }

        usuarioDAO.excluir(id);
    }

    private void validarDepartamento(
            Departamento departamento) {
        if (departamento == null
                || departamento.getId() == null
                || departamento.getId() <= 0) {
            throw new IllegalArgumentException(
                    "Selecione um departamento válido.");
        }
    }

    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Identificador de usuário inválido.");
        }
    }
}
