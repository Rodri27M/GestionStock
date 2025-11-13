
package modelo;

import java.util.List;

public interface CRUD<T> {
    List<T> listar();
    T listarPorId(int id);
    boolean agregar(T obj);
    boolean editar(T obj);
    boolean eliminar(int id);
}