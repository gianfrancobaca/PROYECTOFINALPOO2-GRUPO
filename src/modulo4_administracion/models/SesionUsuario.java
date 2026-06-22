package modulo4_administracion.models;

import java.time.LocalDateTime;

public class SesionUsuario {

    private static SesionUsuario instancia;

    private Usuario       usuarioActual;
    private Rol           rolActual;
    private LocalDateTime fechaInicio;
    private String        token;         // token simple de sesión
    private boolean       autenticado;

    private SesionUsuario() {
        this.autenticado = false;
    }

    public static SesionUsuario getInstance() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    public void iniciarSesion(Usuario usuario, Rol rol) {
        this.usuarioActual = usuario;
        this.rolActual     = rol;
        this.fechaInicio   = LocalDateTime.now();
        this.token         = generarToken(usuario);
        this.autenticado   = true;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
        this.rolActual     = null;
        this.token         = null;
        this.autenticado   = false;
    }

    private String generarToken(Usuario usuario) {
        return "TYS-" + usuario.getId() + "-" + System.currentTimeMillis();
    }

    public boolean tienePermiso(String nombrePermiso) {
        if (!autenticado || rolActual == null) return false;
        return rolActual.tienePermiso(nombrePermiso);
    }

    // ── Getters ───────────────────────────────────────────────────
    public Usuario getUsuarioActual()              { return usuarioActual; }
    public Rol getRolActual()                      { return rolActual; }
    public LocalDateTime getFechaInicio()          { return fechaInicio; }
    public String getToken()                       { return token; }
    public boolean isAutenticado()                 { return autenticado; }
}