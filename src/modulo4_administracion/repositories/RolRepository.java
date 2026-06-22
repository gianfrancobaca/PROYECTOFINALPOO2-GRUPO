package modulo4_administracion.repositories;

import modulo4_administracion.models.Rol;
import java.util.List;

public interface RolRepository {
    Rol         buscarPorId(int id);
    Rol         buscarPorNombre(String nombre);
    List<Rol>   buscarTodos();
    void        guardar(Rol rol);
    void        actualizar(Rol rol);
    void        eliminar(int id);
}
