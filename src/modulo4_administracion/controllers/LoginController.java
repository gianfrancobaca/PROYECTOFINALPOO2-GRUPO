package modulo4_administracion.controllers;

import core.Controller;
import modulo4_administracion.models.*;
import modulo4_administracion.repositories.*;
import modulo4_administracion.views.*;
import java.util.List;

/**
 * Controlador de autenticación del sistema.
 * RF: Seguridad y Control de Acceso.
 */
public class LoginController extends Controller {

    private final UsuarioRepository  usuarioRepo;
    private final RolRepository      rolRepo;
    private final PermisoRepository  permisoRepo;
    private final HistorialOperacionRepository historialRepo;
    private       LoginView          loginView;

    public LoginController() {
        this.usuarioRepo   = new UsuarioRepositorySQL();
        this.rolRepo       = new RolRepositorySQL();
        this.permisoRepo   = new PermisoRepositorySQL();
        this.historialRepo = new HistorialOperacionRepositorySQL();
    }

    @Override
    public void run() {
        loginView = new LoginView(this);
        addView("LoginView", loginView);
        mainFrame.setVisible(true);
        loadView("LoginView");
    }

    /**
     * Valida credenciales. Si son correctas, inicia sesión y redirige al panel admin.
     */
    public boolean autenticar(String correo, String password) {
        Usuario usuario = usuarioRepo.buscarPorCorreo(correo);

        if (usuario == null || !usuario.isActivo()) {
            loginView.mostrarError("Usuario no encontrado o inactivo.");
            return false;
        }

        String hashIngresado = Integer.toHexString(password.hashCode());
        if (!hashIngresado.equals(usuario.getContrasenia())) {
            loginView.mostrarError("Contraseña incorrecta.");
            return false;
        }

        // Carga rol y permisos
        Rol rol = rolRepo.buscarPorId(usuario.getRolId());
        if (rol != null) {
            List<Permiso> permisos = permisoRepo.buscarPorRol(rol.getId());
            permisos.forEach(rol::agregarPermiso);
        }

        // Inicia sesión
        SesionUsuario.getInstance().iniciarSesion(usuario, rol);

        // Registra en historial
        HistorialOperacion op = new HistorialOperacion(0, usuario.getId(),
                usuario.getNombre(), HistorialOperacion.TipoOperacion.LOGIN,
                "Inicio de sesión exitoso", java.time.LocalDateTime.now(), "127.0.0.1");
        historialRepo.registrar(op);

        // Redirige al módulo admin
        AdminController adminCtrl = new AdminController();
        adminCtrl.run();
        return true;
    }

    public void cerrarSesion() {
        SesionUsuario sesion = SesionUsuario.getInstance();
        if (sesion.isAutenticado()) {
            HistorialOperacion op = new HistorialOperacion(0,
                    sesion.getUsuarioActual().getId(),
                    sesion.getUsuarioActual().getNombre(),
                    HistorialOperacion.TipoOperacion.LOGOUT,
                    "Cierre de sesión", java.time.LocalDateTime.now(), "127.0.0.1");
            historialRepo.registrar(op);
        }
        sesion.cerrarSesion();
        loadView("LoginView");
    }
}