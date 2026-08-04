package dao;

import connection.ConnectionFactory;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements InterfaceDAO<Usuario> {

    private final DepartamentoDAO departamentoDAO = new DepartamentoDAO();

    @Override
    public void salvar(Usuario usuario) {

        String sql =
                """
                INSERT INTO usuario
                (nome,email,senha,departamento_id)
                VALUES (?,?,?,?)
                """;

        try (ConnectionFactory conn = ConnectionFactoryFactory.getConnectionFactory();
             PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, usuario.getNome());
                statement.setString(2, usuario.getEmail());
                statement.setString(3, usuario.getSenha());
                statement.setInt(4, usuario.getDepartamento().getId());

                statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void atualizar(Usuario usuario) {

        String sql =
                """
                UPDATE usuario
                SET nome=?,
                    email=?,
                    senha=?,
                    departamento_id=?
                WHERE id=?
                """;

        try (ConnectionFactory conn = conn.getConnectionFactory();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getSenha());
            statement.setInt(4, usuario.getDepartamento().getId());
            statement.setInt(5, usuario.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void excluir(int id) {

        String sql = "DELETE FROM usuario WHERE id=?";

        try (ConnectionFactory conn = conn.getConnectionFactory();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Usuario buscarPorId(int id) {

        String sql = "SELECT * FROM usuario WHERE id=?";

        try (
                ConnectionFactory conn = conn.getConnectionFactory();
                PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, id);
                ResultSet res = statement.executeQuery();

            if (res.next()) {
                Usuario usuario = new Usuario();

                usuario.setId(res.getInt("id"));
                usuario.setNome(res.getString("nome"));
                usuario.setEmail(res.getString("email"));
                usuario.setSenha(res.getString("senha"));
                usuario.setDepartamento(departamentoDAO.buscarPorId(
                            res.getInt("departamento_id")
                    )
                );

                return usuario;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;

    }

    @Override
    public List<Usuario> listar() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuario ORDER BY nome";

        try (
            ConnectionFactory conn = conn.getConnectionFactory();
             Statement statement = conn.createStatement();
             ResultSet res = statement.executeQuery(sql)) {

            while (res.next()) {

                Usuario usuario = new Usuario();
                usuario.setId(res.getInt("id"));
                usuario.setNome(res.getString("nome"));
                usuario.setEmail(res.getString("email"));
                usuario.setSenha(res.getString("senha"));
                usuario.setDepartamento(departamentoDAO.buscarPorId(res.getInt("departamento_id")));
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usuarios;

    }

    public Usuario buscarPorEmail(String email) {

        String sql = "SELECT * FROM usuario WHERE email=?";

        try (ConnectionFactory conn = conn.getConnectionFactory();

            PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet res = statement.executeQuery();

            if (res.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(res.getInt("id"));
                usuario.setNome(res.getString("nome"));
                usuario.setEmail(res.getString("email"));
                usuario.setSenha(res.getString("senha"));

                usuario.setDepartamento(departamentoDAO.buscarPorId(res.getInt("departamento_id")));

                return usuario;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;

    }

}
