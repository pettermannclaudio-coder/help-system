package dao;

import connection.ConnectionFactory;
import model.Departamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO implements InterfaceDAO<Departamento> {

    @Override
    public void salvar(Departamento departamento) {

        String sql =
                "INSERT INTO departamento(nome) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();

             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, departamento.getNome());

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao salvar departamento:", e
            );

        }

    }

    @Override
    public void atualizar(Departamento departamento) {

        String sql = "UPDATE departamento SET nome=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();

             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, departamento.getNome());
            statement.setInt(2, departamento.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void excluir(int id) {

        String sql = "DELETE FROM departamento WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Departamento buscarPorId(int id) {

        String sql = "SELECT * FROM departamento WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();

            if (res.next()) {
                Departamento departamento = new Departamento();

                departamento.setId(res.getInt("id"));

                departamento.setNome(res.getString("nome"));

                return departamento;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;

    }

    @Override
    public List<Departamento> listar() {

        List<Departamento> departamentos =
                new ArrayList<>();

        String sql = "SELECT * FROM departamento ORDER BY nome";

        try (Connection conn = ConnectionFactory.getConnection();

             Statement statement = conn.createStatement();

             ResultSet res = statement.executeQuery(sql)) {

            while (res.next()) {Departamento departamento = new Departamento();
                departamento.setId(res.getInt("id"));

                departamento.setNome(res.getString("nome"));
                departamentos.add(departamento);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return departamentos;

    }

}