package dao;

import connection.ConnectionFactory;
import dao.UsuarioDAO;
import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoDAO implements InterfaceDAO<Solicitacao> {

    private static final DateTimeFormatter SQLITE_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DepartamentoDAO departamentoDAO = new DepartamentoDAO();

    @Override
    public void salvar(Solicitacao solicitacao) {

        String sql = """
                INSERT INTO solicitacao
                (
                    titulo,
                    descricao,
                    status,
                    data_criacao,
                    usuario_id,
                    departamento_id
                )
                VALUES (?,?,?,?,?,?)
                """;

        try (Connection connection = ConnectionFactory.getConnection();

                PreparedStatement statement = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(
                    1,
                    solicitacao.getTitulo());

            statement.setString(
                    2,
                    solicitacao.getDescricao());

            statement.setString(
                    3,
                    solicitacao.getStatus());

            definirDataCriacao(
                    statement,
                    4,
                    solicitacao.getDataCriacao());

            statement.setInt(
                    5,
                    solicitacao.getUsuario().getId());

            statement.setInt(
                    6,
                    solicitacao.getDepartamento().getId());

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLException("Nenhuma solicitação foi inserida.");
            }

            try (ResultSet rs = statement.getGeneratedKeys()) {

                if (rs.next()) {
                    solicitacao.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Não foi possível obter o ID gerado.");
                }

            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao salvar solicitação.",
                    e);

        }

    }

    @Override
    public void atualizar(Solicitacao solicitacao) {
        String sql = """
                UPDATE solicitacao
                SET titulo=?,
                    descricao=?,
                    departamento_id=?,
                    status=?
                WHERE id=?
                """;

        try (Connection connection = ConnectionFactory.getConnection();

                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, solicitacao.getTitulo());
            statement.setString(2, solicitacao.getDescricao());
            statement.setInt(3, solicitacao.getDepartamento().getId());
            statement.setString(
                    4,
                    solicitacao.getStatus());

            statement.setInt(
                    5,
                    solicitacao.getId());

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

        List<Solicitacao> solicitacoes = new ArrayList<>();

        String sql = """
                SELECT *
                FROM solicitacao
                ORDER BY data_criacao DESC
                """;

        try (Connection connection = ConnectionFactory.getConnection();

                PreparedStatement statement = connection.prepareStatement(sql);

                ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {

                solicitacoes.add(
                        criarSolicitacao(rs));

            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao listar solicitações.",
                    e);

        }

        return solicitacoes;

    }

    private Solicitacao criarSolicitacao(ResultSet rs)
            throws SQLException {

        Solicitacao solicitacao = new Solicitacao();

        solicitacao.setId(rs.getInt("id"));
        solicitacao.setTitulo(rs.getString("titulo"));
        solicitacao.setDescricao(rs.getString("descricao"));
        solicitacao.setStatus(rs.getString("status"));
        solicitacao.setDataCriacao(lerDataCriacao(rs));
        solicitacao.setUsuario(usuarioDAO.buscarPorId(rs.getInt("usuario_id")));
        solicitacao.setDepartamento(departamentoDAO.buscarPorId(rs.getInt("departamento_id")));

        return solicitacao;

    }

    private void definirDataCriacao(
            PreparedStatement statement,
            int indice,
            LocalDateTime dataCriacao) throws SQLException {
        if (ConnectionFactory.isSqlite()) {
            statement.setString(
                    indice,
                    dataCriacao.format(SQLITE_DATE_TIME));
            return;
        }

        statement.setTimestamp(
                indice,
                Timestamp.valueOf(dataCriacao));
    }

    private LocalDateTime lerDataCriacao(ResultSet resultado)
            throws SQLException {
        if (ConnectionFactory.isSqlite()) {
            String valor = resultado.getString("data_criacao");

            return valor == null
                    ? null
                    : LocalDateTime.parse(valor, SQLITE_DATE_TIME);
        }

        Timestamp timestamp = resultado.getTimestamp("data_criacao");

        return timestamp == null
                ? null
                : timestamp.toLocalDateTime();
    }

    public List<Solicitacao> buscarPorUsuario(int usuarioId) {

        List<Solicitacao> lista = new ArrayList<>();
        String sql = """
                SELECT *
                FROM solicitacao
                WHERE usuario_id=?
                ORDER BY data_criacao DESC
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

        String sql = """
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
