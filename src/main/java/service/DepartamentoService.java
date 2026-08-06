package service;

import dao.DepartamentoDAO;
import model.Departamento;

import java.util.List;

public class DepartamentoService {

    private final DepartamentoDAO departamentoDAO =
            new DepartamentoDAO();

    public void salvar(Departamento departamento) {

        if (departamento == null) {
            throw new IllegalArgumentException("Departamento inválido.");
        }

        if (departamento.getNome() == null
                || departamento.getNome().isBlank()) {

            throw new IllegalArgumentException(
                    "Informe o nome do departamento."
            );

        }

        if (departamentoDAO.existePorNome(departamento.getNome())) {

            throw new IllegalArgumentException(
                    "Já existe um departamento com esse nome."
            );

        }

        departamentoDAO.salvar(departamento);

    }

    public void atualizar(Departamento departamento) {

        departamentoDAO.atualizar(departamento);

    }

    public void excluir(int id) {

        departamentoDAO.excluir(id);

    }

    public Departamento buscarPorId(int id) {

        return departamentoDAO.buscarPorId(id);

    }

    public List<Departamento> listar() {

        return departamentoDAO.listar();

    }

}