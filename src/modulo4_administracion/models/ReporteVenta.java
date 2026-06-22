package modulo4_administracion.models;

import java.time.LocalDate;


public class ReporteVenta {

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int       totalTransacciones;
    private double    ingresoTotal;
    private int       productosVendidos;
    private String    metodoPagoMasFrecuente;
    private String    periodoMayorActividad;

    public ReporteVenta() {}

    public ReporteVenta(LocalDate fechaInicio, LocalDate fechaFin,
                        int totalTransacciones, double ingresoTotal,
                        int productosVendidos, String metodoPagoMasFrecuente,
                        String periodoMayorActividad) {
        this.fechaInicio             = fechaInicio;
        this.fechaFin                = fechaFin;
        this.totalTransacciones      = totalTransacciones;
        this.ingresoTotal            = ingresoTotal;
        this.productosVendidos       = productosVendidos;
        this.metodoPagoMasFrecuente  = metodoPagoMasFrecuente;
        this.periodoMayorActividad   = periodoMayorActividad;
    }

    // ── Getters & Setters ──────────────────────────────────────────
    public LocalDate getFechaInicio()                      { return fechaInicio; }
    public void setFechaInicio(LocalDate f)                { this.fechaInicio = f; }

    public LocalDate getFechaFin()                         { return fechaFin; }
    public void setFechaFin(LocalDate f)                   { this.fechaFin = f; }

    public int getTotalTransacciones()                     { return totalTransacciones; }
    public void setTotalTransacciones(int t)               { this.totalTransacciones = t; }

    public double getIngresoTotal()                        { return ingresoTotal; }
    public void setIngresoTotal(double i)                  { this.ingresoTotal = i; }

    public int getProductosVendidos()                      { return productosVendidos; }
    public void setProductosVendidos(int p)                { this.productosVendidos = p; }

    public String getMetodoPagoMasFrecuente()              { return metodoPagoMasFrecuente; }
    public void setMetodoPagoMasFrecuente(String m)        { this.metodoPagoMasFrecuente = m; }

    public String getPeriodoMayorActividad()               { return periodoMayorActividad; }
    public void setPeriodoMayorActividad(String p)         { this.periodoMayorActividad = p; }

    @Override
    public String toString() {
        return String.format("ReporteVenta[%s → %s | Ingresos: S/ %.2f | Transacciones: %d]",
                fechaInicio, fechaFin, ingresoTotal, totalTransacciones);
    }
}