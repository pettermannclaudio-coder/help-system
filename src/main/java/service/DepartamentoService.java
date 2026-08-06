package service;

import dao.DepartamentoDAO;
import model.Departamento;

import java.util.List;

public class DepartamentoService {

    private final DepartamentoDAO departamentoDAO =
            new DepartamentoDAO();

    public void salvar(Departamento departamento) {

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