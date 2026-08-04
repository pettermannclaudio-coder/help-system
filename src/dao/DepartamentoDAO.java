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
                """ INSERT INTO departamento (nome) VALUES (?) """;

        try (ConnectionFactory conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, departamento.getNome());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar departamento: ",e);
        }

    }





}