package dao;

import connection.ConnectionFactory;
import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RespostaDAO implements InterfaceDAO<Resposta> {

    private static final DateTimeFormatter SQLITE_DATE_TIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void salvar(Resposta resposta) {

        String sql = """
                INSERT INTO resposta
                (texto,
                 data_resposta,
                 usuario_id,
                 solicitacao_id)
                VALUES (?,?,?,?)
                """;

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, resposta.getDescricao());
            definirDataResposta(statement, 2, resposta.getDataResposta());
            statement.setInt(3, resposta.getUsuario().getId());
            statement.setInt(4, resposta.getSolicitacao().getId());

            statement.executeUpdate();

            try (ResultSet chaves = statement.getGeneratedKeys()) {
                if (chaves.next()) {
                    resposta.setId(chaves.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar resposta.", e);
        }

    }

    @Override
    public void atualizar(Resposta resposta) {

        String sql = """
                UPDATE resposta
                SET texto=?
                WHERE id=?
                """;

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, resposta.getDescricao());
            statement.setInt(2, resposta.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM resposta WHERE id=?";
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Resposta buscarPorId(int id) {

        String sql = "SELECT * FROM resposta WHERE id=?";

        try (
                Connection connection = ConnectionFactory.getConnection();

                PreparedStatement statement = connection.prepareStatement(sql)

        ) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return mapearResposta(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;

    }

    @Override
    public List<Resposta> listar() {

        List<Resposta> respostas = new ArrayList<>();
        String sql = "SELECT * FROM resposta ORDER BY data_resposta";

        try (
                Connection connection = ConnectionFactory.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)

        ) {
            while (rs.next()) {
                respostas.add(mapearResposta(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return respostas;

    };

    public List<Resposta> buscarPorSolicitacao(int solicitacaoId) {

        List<Resposta> respostas = new ArrayList<>();

        String sql = """
            SELECT
                r.id,
                r.texto,
                r.data_resposta,

                u.id AS usuario_id,
                u.nome,
                u.email,
                u.tipo,

                d.id AS departamento_id,
                d.nome AS departamento,

                s.id AS solicitacao_id,
                s.titulo

            FROM resposta r

            INNER JOIN usuario u
                ON r.usuario_id = u.id

            INNER JOIN departamento d
                ON u.departamento_id = d.id

            INNER JOIN solicitacao s
                ON r.solicitacao_id = s.id

            WHERE s.id = ?

            ORDER BY r.data_resposta ASC
            """;

        try (Connection connection = ConnectionFactory.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, solicitacaoId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                respostas.add(
                        mapearResposta(rs)
                );

            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao buscar respostas da solicitação.",
                    e
            );

        }

        return respostas;

    }

    private Resposta mapearResposta(ResultSet rs)
            throws SQLException {

        Departamento departamento = new Departamento(rs.getString("departamento"));

        Usuario usuario = new Usuario();

        usuario.setId(rs.getInt("usuario_id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setDepartamento(departamento);

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(rs.getInt("solicitacao_id"));
        solicitacao.setTitulo(rs.getString("titulo"));
        Resposta resposta = new Resposta();
        resposta.setId(rs.getInt("id"));
        resposta.setDescricao(rs.getString("texto"));
        resposta.setDataResposta(lerDataResposta(rs));
        resposta.setUsuario(
                usuario
        );

        resposta.setSolicitacao(
                solicitacao
        );

        return resposta;

    }

    private void definirDataResposta(
            PreparedStatement statement, int indice, LocalDateTime data)
            throws SQLException {
        if (ConnectionFactory.isSqlite()) {
            statement.setString(indice, data.format(SQLITE_DATE_TIME));
        } else {
            statement.setTimestamp(indice, Timestamp.valueOf(data));
        }
    }

    private LocalDateTime lerDataResposta(ResultSet resultado)
            throws SQLException {
        if (ConnectionFactory.isSqlite()) {
            String valor = resultado.getString("data_resposta");
            return valor == null
                    ? null
                    : LocalDateTime.parse(valor, SQLITE_DATE_TIME);
        }
        Timestamp timestamp = resultado.getTimestamp("data_resposta");
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
