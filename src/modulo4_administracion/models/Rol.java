package modulo4_administracion.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Define el rol de un usuario dentro del sistema.
 * RF: Gestión de Roles y Permisos.
 */
public class Rol {

    private int    id;
    private String nombre;        // ADMIN, VENDEDOR, INVENTARISTA, SUPERVISOR
    private String descripcion;
    private List<Permiso> permisos;

    public Rol() {
        this.permisos = new ArrayList<>();
    }

    public Rol(int id, String nombre, String descripcion) {
        this.id          = id;
        this.nombre      = nombre;
        this.descripcion = descripcion;
        this.permisos    = new ArrayList<>();
    }

    public void agregarPermiso(Permiso permiso) {
        if (!permisos.contains(permiso)) {
            permisos.add(permiso);
        }
    }

    public void eliminarPermiso(Permiso permiso) {
        permisos.remove(permiso);
    }

    public boolean tienePermiso(String nombrePermiso) {
        return permisos.stream()
                .anyMatch(p -> p.getNombre().equalsIgnoreCase(nombrePermiso));
    }

    // ── Getters & Setters ──────────────────────────────────────────
    public int getId()                       { return id; }
    public void setId(int id)                { this.id = id; }

    public String getNombre()                { return nombre; }
    public void setNombre(String nombre)     { this.nombre = nombre; }

    public String getDescripcion()           { return descripcion; }
    public void setDescripcion(String d)     { this.descripcion = d; }

    public List<Permiso> getPermisos()       { return permisos; }
    public void setPermisos(List<Permiso> p) { this.permisos = p; }

    @Override
    public String toString() {
        return "Rol{id=" + id + ", nombre='" + nombre + "'}";
    }
}