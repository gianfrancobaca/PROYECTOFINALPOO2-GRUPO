package modulo4_administracion.models;


import java.time.LocalDateTime;

public class ExportacionReporte {

    public enum FormatoExportacion { CSV, PDF, EXCEL, TXT }
    public enum TipoReporte        { VENTAS, INVENTARIO, CLIENTES, HISTORIAL, INDICADORES }

    private int                 id;
    private int                 usuarioId;
    private TipoReporte         tipoReporte;
    private FormatoExportacion  formato;
    private String              rutaArchivo;
    private LocalDateTime       fechaExportacion;
    private boolean             exitoso;

    public ExportacionReporte() {}

    public ExportacionReporte(int id, int usuarioId, TipoReporte tipoReporte,
                              FormatoExportacion formato, String rutaArchivo,
                              LocalDateTime fechaExportacion, boolean exitoso) {
        this.id               = id;
        this.usuarioId        = usuarioId;
        this.tipoReporte      = tipoReporte;
        this.formato          = formato;
        this.rutaArchivo      = rutaArchivo;
        this.fechaExportacion = fechaExportacion;
        this.exitoso          = exitoso;
    }

    // ── Getters & Setters ──────────────────────────────────────────
    public int getId()                                    { return id; }
    public void setId(int id)                             { this.id = id; }

    public int getUsuarioId()                             { return usuarioId; }
    public void setUsuarioId(int u)                       { this.usuarioId = u; }

    public TipoReporte getTipoReporte()                   { return tipoReporte; }
    public void setTipoReporte(TipoReporte t)             { this.tipoReporte = t; }

    public FormatoExportacion getFormato()                { return formato; }
    public void setFormato(FormatoExportacion f)          { this.formato = f; }

    public String getRutaArchivo()                        { return rutaArchivo; }
    public void setRutaArchivo(String r)                  { this.rutaArchivo = r; }

    public LocalDateTime getFechaExportacion()            { return fechaExportacion; }
    public void setFechaExportacion(LocalDateTime f)      { this.fechaExportacion = f; }

    public boolean isExitoso()                            { return exitoso; }
    public void setExitoso(boolean e)                     { this.exitoso = e; }

    @Override
    public String toString() {
        return String.format("Exportacion{%s | %s | %s | %s}",
                tipoReporte, formato, rutaArchivo, fechaExportacion);
    }
}