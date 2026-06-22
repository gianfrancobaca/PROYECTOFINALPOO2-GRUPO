package modulo4_administracion.models;

import java.time.LocalDate;

public class ReporteInventario {

    private LocalDate fechaGeneracion;
    private int       totalProductos;
    private int       productosDisponibles;
    private int       productosReservados;
    private int       productosVendidos;
    private double    valorTotalInventario;

    public ReporteInventario() {}

    public ReporteInventario(LocalDate fechaGeneracion, int totalProductos,
                             int productosDisponibles, int productosReservados,
                             int productosVendidos, double valorTotalInventario) {
        this.fechaGeneracion      = fechaGeneracion;
        this.totalProductos       = totalProductos;
        this.productosDisponibles = productosDisponibles;
        this.productosReservados  = productosReservados;
        this.productosVendidos    = productosVendidos;
        this.valorTotalInventario = valorTotalInventario;
    }

    // ── Getters & Setters ──────────────────────────────────────────
    public LocalDate getFechaGeneracion()                  { return fechaGeneracion; }
    public void setFechaGeneracion(LocalDate f)            { this.fechaGeneracion = f; }

    public int getTotalProductos()                         { return totalProductos; }
    public void setTotalProductos(int t)                   { this.totalProductos = t; }

    public int getProductosDisponibles()                   { return productosDisponibles; }
    public void setProductosDisponibles(int p)             { this.productosDisponibles = p; }

    public int getProductosReservados()                    { return productosReservados; }
    public void setProductosReservados(int p)              { this.productosReservados = p; }

    public int getProductosVendidos()                      { return productosVendidos; }
    public void setProductosVendidos(int p)                { this.productosVendidos = p; }

    public double getValorTotalInventario()                { return valorTotalInventario; }
    public void setValorTotalInventario(double v)          { this.valorTotalInventario = v; }

    @Override
    public String toString() {
        return String.format("ReporteInventario[%s | Total: %d | Disponibles: %d | Valor: S/ %.2f]",
                fechaGeneracion, totalProductos, productosDisponibles, valorTotalInventario);
    }
}