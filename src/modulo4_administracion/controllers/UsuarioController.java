package modulo4_administracion.controllers;

import core.Controller;
import modulo4_administracion.models.*;
import modulo4_administracion.repositories.*;
import modulo4_administracion.views.*;
import java.util.List;

/**
 * Controlador de gestión de usuarios del sistema.
 * RF: Gestión de Usuarios, Seguridad y Control de Acceso.
 */

public class UsuarioController extends Controller {

    private final UsuarioRepository     usuarioRepo;
    private final RolRepository         rolRepo;
    private final HistorialOperacionRepository historialRepo;
    private final UsuarioModel          usuarioModel;
    private       UsuarioListView       usuarioListView;
    private       UsuarioFormView       usuarioFormView;

    public UsuarioController() {
        this.usuarioRepo   = new UsuarioRepositorySQL();
        this.rolRepo       = new RolRepositorySQL();
        this.historialRepo = new HistorialOperacionRepositorySQL();
        this.usuarioModel  = new UsuarioModel();
    }

    @Override
    public void run() {
        usuarioListView = new UsuarioListView(this);
        usuarioFormView = new UsuarioFormView(this);
        usuarioModel.attach(usuarioListView);
        addView("UsuarioListView", usuarioListView);
        addView("UsuarioFormView", usuarioFormView);
        cargarUsuarios();
        loadView("UsuarioListView");
    }

    public void cargarUsuarios() {
        List<Usuario> lista = usuarioRepo.buscarTodos();
        usuarioModel.setUsuarios(lista);
    }

    public void registrarUsuario(Usuario usuario) {
        if (usuarioRepo.existeCorreo(usuario.getCorreo())) {
            usuarioModel.registrarAccion("ERROR_CORREO_DUPLICADO");
            return;
        }
        // Encriptación básica (en producción usar BCrypt)
        usuario.setContrasenia(encriptar(usuario.getContrasenia()));
        usuarioRepo.guardar(usuario);
        registrarEnHistorial("Registro de nuevo usuario: " + usuario.getNombre(),
                HistorialOperacion.TipoOperacion.CREACION_USUARIO);
        cargarUsuarios();
    }

    public void actualizarUsuario(Usuario usuario) {
        usuarioRepo.actualizar(usuario);
        registrarEnHistorial("Actualización de usuario ID: " + usuario.getId(),
                HistorialOperacion.TipoOperacion.MODIFICACION_ADMIN);
        cargarUsuarios();
    }

    public void eliminarUsuario(int id) {
        usuarioRepo.eliminar(id);
        registrarEnHistorial("Eliminación de usuario ID: " + id,
                HistorialOperacion.TipoOperacion.ELIMINACION_USUARIO);
        cargarUsuarios();
    }

    public void cambiarEstadoUsuario(int id, boolean activo) {
        usuarioRepo.cambiarEstado(id, activo);
        String acc = activo ? "Activación" : "Desactivación";
        registrarEnHistorial(acc + " de usuario ID: " + id,
                HistorialOperacion.TipoOperacion.MODIFICACION_ADMIN);
        cargarUsuarios();
    }

    public List<Rol> obtenerRoles() {
        return rolRepo.buscarTodos();
    }

    public void mostrarFormulario() { loadView("UsuarioFormView"); }
    public void mostrarLista()      { loadView("UsuarioListView"); }

    public UsuarioModel getModel() { return usuarioModel; }

    private void registrarEnHistorial(String descripcion,
                                      HistorialOperacion.TipoOperacion tipo) {
        SesionUsuario sesion = SesionUsuario.getInstance();
        if (!sesion.isAutenticado()) return;
        HistorialOperacion op = new HistorialOperacion(0,
                sesion.getUsuarioActual().getId(),
                sesion.getUsuarioActual().getNombre(),
                tipo, descripcion,
                java.time.LocalDateTime.now(), "127.0.0.1");
        historialRepo.registrar(op);
    }

    private String encriptar(String password) {
        // Simulación: en producción usar BCrypt o SHA-256
        return Integer.toHexString(password.hashCode());
    }
}