package modulo4_administracion.models;

import java.time.LocalDateTime;


public class HistorialOperacion {

    public enum TipoOperacion {
        VENTA, RESERVA, ACTUALIZACION_INVENTARIO,
        MODIFICACION_ADMIN, LOGIN, LOGOUT,
        CREACION_USUARIO, ELIMINACION_USUARIO,
        GENERACION_REPORTE, EXPORTACION_REPORTE
    }

    private int            id;
    private int            usuarioId;
    private String         usuarioNombre;
    private TipoOperacion  tipo;
    private String         descripcion;
    private LocalDateTime  fechaHora;
    private String         ipOrigen;

    public HistorialOperacion() {}

    public HistorialOperacion(int id, int usuarioId, String usuarioNombre,
                              TipoOperacion tipo, String descripcion,
                              LocalDateTime fechaHora, String ipOrigen) {
        this.id            = id;
        this.usuarioId     = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.tipo          = tipo;
        this.descripcion   = descripcion;
        this.fechaHora     = fechaHora;
        this.ipOrigen      = ipOrigen;
    }

    // ── Getters & Setters ──────────────────────────────────────────
    public int getId()                                    { return id; }
    public void setId(int id)                             { this.id = id; }

    public int getUsuarioId()                             { return usuarioId; }
    public void setUsuarioId(int u)                       { this.usuarioId = u; }

    public String getUsuarioNombre()                      { return usuarioNombre; }
    public void setUsuarioNombre(String n)                { this.usuarioNombre = n; }

    public TipoOperacion getTipo()                        { return tipo; }
    public void setTipo(TipoOperacion t)                  { this.tipo = t; }

    public String getDescripcion()                        { return descripcion; }
    public void setDescripcion(String d)                  { this.descripcion = d; }

    public LocalDateTime getFechaHora()                   { return fechaHora; }
    public void setFechaHora(LocalDateTime f)             { this.fechaHora = f; }

    public String getIpOrigen()                           { return ipOrigen; }
    public void setIpOrigen(String ip)                    { this.ipOrigen = ip; }

    @Override
    public String toString() {
        return String.format("[%s] %s → %s: %s", fechaHora, usuarioNombre, tipo, descripcion);
    }
}