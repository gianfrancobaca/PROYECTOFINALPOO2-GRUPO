package modulo4_administracion.models;

public class Permiso {

    private int    id;
    private String nombre;       // VER_REPORTES, EDITAR_USUARIOS, EXPORTAR, etc.
    private String modulo;       // ADMIN, INVENTARIO, VENTAS, CLIENTES
    private String descripcion;

    public Permiso() {}

    public Permiso(int id, String nombre, String modulo, String descripcion) {
        this.id          = id;
        this.nombre      = nombre;
        this.modulo      = modulo;
        this.descripcion = descripcion;
    }

    // ── Getters & Setters ──────────────────────────────────────────
    public int getId()                       { return id; }
    public void setId(int id)                { this.id = id; }

    public String getNombre()                { return nombre; }
    public void setNombre(String nombre)     { this.nombre = nombre; }

    public String getModulo()                { return modulo; }
    public void setModulo(String modulo)     { this.modulo = modulo; }

    public String getDescripcion()           { return descripcion; }
    public void setDescripcion(String d)     { this.descripcion = d; }

    @Override
    public String toString() {
        return "Permiso{" + modulo + "::" + nombre + "}";
    }
}