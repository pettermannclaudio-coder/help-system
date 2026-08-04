package dao;

import java.util.List;

public interface InterfaceDAO<T> {

    void salvar(T objeto);

    void atualizar(T objeto);

    void excluir(int id);

    T buscarPorId(int id);

    List<T> listar();

}