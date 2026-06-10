package modulo4_administracion.repositories;

import modulo4_administracion.models.Permiso;
import java.util.List;

/**
 * Contrato del repositorio de permisos.
 * RF: Gestión de Roles y Permisos.
 */
public interface PermisoRepository {
    Permiso         buscarPorId(int id);
    List<Permiso>   buscarTodos();
    List<Permiso>   buscarPorRol(int rolId);
    List<Permiso>   buscarPorModulo(String modulo);
    void            guardar(Permiso permiso);
    void            eliminar(int id);
    void            asignarPermisoARol(int rolId, int permisoId);
    void            revocarPermisoDeRol(int rolId, int permisoId);
}