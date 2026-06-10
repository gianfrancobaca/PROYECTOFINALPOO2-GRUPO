package modulo4_administracion.repositories;


import modulo4_administracion.models.Usuario;
import java.util.List;

/**
 * Contrato del repositorio de usuarios.
 * RF: Gestión de Usuarios.
 */
public interface UsuarioRepository {
    Usuario    buscarPorId(int id);
    Usuario    buscarPorCorreo(String correo);
    List<Usuario> buscarTodos();
    List<Usuario> buscarPorRol(int rolId);
    void       guardar(Usuario usuario);
    void       actualizar(Usuario usuario);
    void       eliminar(int id);
    boolean    existeCorreo(String correo);
    void       cambiarEstado(int id, boolean activo);
}