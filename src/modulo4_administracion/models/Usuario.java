package modulo4_administracion.models;

// ─── File: src/models/Usuario.java ───
import java.time.LocalDateTime;

/**
 * Representa un usuario administrador del sistema.
 * RF: Gestión de Usuarios, Seguridad y Control de Acceso.
 */
public class Usuario {

    private int    id;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasenia;   // almacenada con hash en la BD
    private boolean activo;
    private LocalDateTime fechaCreacion;
    private int    rolId;

    public Usuario() {}

    public Usuario(int id, String nombre, String apellido,
                   String correo, String contrasenia, boolean activo, int rolId) {
        this.id           = id;
        this.nombre       = nombre;
        this.apellido     = apellido;
        this.correo       = correo;
        this.contrasenia  = contrasenia;
        this.activo       = activo;
        this.rolId        = rolId;
        this.fechaCreacion = LocalDateTime.now();
    }

    // ── Getters & Setters ──────────────────────────────────────────
    public int getId()                       { return id; }
    public void setId(int id)                { this.id = id; }

    public String getNombre()                { return nombre; }
    public void setNombre(String nombre)     { this.nombre = nombre; }

    public String getApellido()              { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo()                { return correo; }
    public void setCorreo(String correo)     { this.correo = correo; }

    public String getContrasenia()           { return contrasenia; }
    public void setContrasenia(String c)     { this.contrasenia = c; }

    public boolean isActivo()                { return activo; }
    public void setActivo(boolean activo)    { this.activo = activo; }

    public LocalDateTime getFechaCreacion()  { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime f) { this.fechaCreacion = f; }

    public int getRolId()                    { return rolId; }
    public void setRolId(int rolId)          { this.rolId = rolId; }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + nombre + " " + apellido + "'}";
    }
}