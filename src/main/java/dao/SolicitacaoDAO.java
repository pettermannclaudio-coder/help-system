package dao;

import connection.ConnectionFactory;
import dao.UsuarioDAO;
import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class SolicitacaoDAO implements InterfaceDAO<Solicitacao> {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DepartamentoDAO departamentoDAO = new DepartamentoDAO();

    @Override
    public void salvar(Solicitacao solicitacao) {
        String sql = """
                INSERT INTO solicitacao
                (titulo,
                 descricao,
                 status,
                 departamento_id,
                 data_abertura,
                 usuario_id)
                VALUES (?,?,?,?,?,?)
                """;

        try (Connection connection =
                     ConnectionFactory.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    solicitacao.getTitulo()
            );

            statement.setString(
                    2,
                    solicitacao.getDescricao()
            );

            statement.setInt(
                    4,
                    solicitacao.getDepartamento().getId()
            );

            statement.setString(
                    5,
                    solicitacao.getStatus()
            );

            statement.setTimestamp(
                    6,
                    Timestamp.valueOf(
                            solicitacao.getDataCriacao()
                    )
            );

            statement.setInt(
                    7,
                    solicitacao.getUsuario().getId()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao salvar solicitação.",
                    e
            );

        }

    }

    @Override
    public void atualizar(Solicitacao solicitacao) {
        String sql = """
                UPDATE solicitacao
                SET titulo=?,
                    descricao=?,
                    categoria=?,
                    status=?
                WHERE id=?
                """;

        try (Connection connection =
                     ConnectionFactory.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, solicitacao.getTitulo());
            statement.setString(2, solicitacao.getDescricao());
            statement.setInt(3, solicitacao.getDepartamento().getId());
            statement.setString(
                    4,
                    solicitacao.getStatus()
            );

            statement.setInt(
                    5,
                    solicitacao.getId()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM solicitacao WHERE id=?";

        try (Connection connection = ConnectionFactory.getConnection();

             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Solicitacao buscarPorId(int id) {
        String sql = "SELECT * FROM solicitacao WHERE id=?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return criarSolicitacao(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Solicitacao> listar() {
        return List.of();
    }

    private Solicitacao criarSolicitacao(ResultSet rs)
            throws SQLException {

        Solicitacao solicitacao = new Solicitacao();

        solicitacao.setId(rs.getInt("id"));
        solicitacao.setTitulo(rs.getString("titulo"));
        solicitacao.setDescricao(rs.getString("descricao"));
        solicitacao.setStatus(rs.getString("status"));
        Timestamp timestamp = rs.getTimestamp("data_criacao");
        if (timestamp != null) {
            solicitacao.setDataCriacao(timestamp.toLocalDateTime());
        }
        solicitacao.setUsuario(usuarioDAO.buscarPorId(rs.getInt("usuario_id")));
        solicitacao.setDepartamento(departamentoDAO.buscarPorId(rs.getInt("departamento_id")));

        return solicitacao;

    }

    public List<Solicitacao> buscarPorUsuario(int usuarioId) {

        List<Solicitacao> lista = new ArrayList<>();
        String sql =
                """
                SELECT *
                FROM solicitacao
                WHERE usuario_id=?
                ORDER BY data_abertura DESC
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuarioId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                lista.add(criarSolicitacao(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;

    }

    public void marcarComoResolvida(int id) {

        String sql =
                """
                UPDATE solicitacao
                SET status='RESOLVIDA'
                WHERE id=?
                """;

        try (Connection connection = ConnectionFactory.getConnection();

             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
