package dao;

import connection.ConnectionFactory;
import model.TipoUsuario;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements InterfaceDAO<Usuario> {

    private final DepartamentoDAO departamentoDAO = new DepartamentoDAO();

    @Override
    public void salvar(Usuario usuario) {

        String sql = """
                INSERT INTO usuario
                    (nome, email, senha, tipo, departamento_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getSenha());
            statement.setString(4, usuario.getTipo().name());
            statement.setInt(5, usuario.getDepartamento().getId());

            statement.executeUpdate();

            try (ResultSet chaves = statement.getGeneratedKeys()) {
                if (chaves.next()) {
                    usuario.setId(chaves.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário.", e);
        }
    }

    @Override
    public void atualizar(Usuario usuario) {

        String sql = """
                UPDATE usuario
                SET nome = ?,
                    email = ?,
                    senha = ?,
                    departamento_id = ?
                WHERE id = ?
                """;

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getSenha());
            statement.setInt(4, usuario.getDepartamento().getId());
            statement.setInt(5, usuario.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário.", e);
        }
    }

    @Override
    public void excluir(int id) {

        String sql = "DELETE FROM usuario WHERE id = ?";

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuário.", e);
        }
    }

    @Override
    public Usuario buscarPorId(int id) {

        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {
                    return criarUsuario(resultado);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário pelo ID.", e);
        }

        return null;
    }

    @Override
    public List<Usuario> listar() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuario ORDER BY nome";

        try (
                Connection connection = ConnectionFactory.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultado = statement.executeQuery(sql)) {

            while (resultado.next()) {
                Usuario usuario = criarUsuario(resultado);
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários.", e);
        }

        return usuarios;
    }

    public Usuario buscarPorEmail(String email) {

        String sql = "SELECT * FROM usuario WHERE email = ?";

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {
                    return criarUsuario(resultado);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário pelo e-mail.", e);
        }

        return null;
    }

    private Usuario criarUsuario(ResultSet resultado) throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setId(resultado.getInt("id"));
        usuario.setNome(resultado.getString("nome"));
        usuario.setEmail(resultado.getString("email"));
        usuario.setSenha(resultado.getString("senha"));
        usuario.setTipo(
                TipoUsuario.valueOf(resultado.getString("tipo")));

        int departamentoId = resultado.getInt("departamento_id");

        usuario.setDepartamento(
                departamentoDAO.buscarPorId(departamentoId));

        return usuario;
    }
}
