package modulo4_administracion.models;

import java.time.LocalDate;


public class IndicadorAdministrativo {

    private LocalDate fecha;
    private double    totalVentasDia;
    private double    totalVentasMes;
    private int       cantidadVentasDia;
    private int       productosDisponibles;
    private int       productosReservados;
    private double    ingresoPromedioPorVenta;
    private int       totalClientesRegistrados;

    public IndicadorAdministrativo() {}

    public IndicadorAdministrativo(LocalDate fecha, double totalVentasDia,
                                   double totalVentasMes, int cantidadVentasDia,
                                   int productosDisponibles, int productosReservados,
                                   double ingresoPromedioPorVenta,
                                   int totalClientesRegistrados) {
        this.fecha                    = fecha;
        this.totalVentasDia           = totalVentasDia;
        this.totalVentasMes           = totalVentasMes;
        this.cantidadVentasDia        = cantidadVentasDia;
        this.productosDisponibles     = productosDisponibles;
        this.productosReservados      = productosReservados;
        this.ingresoPromedioPorVenta  = ingresoPromedioPorVenta;
        this.totalClientesRegistrados = totalClientesRegistrados;
    }

    // ── Getters & Setters ──────────────────────────────────────────
    public LocalDate getFecha()                            { return fecha; }
    public void setFecha(LocalDate f)                      { this.fecha = f; }

    public double getTotalVentasDia()                      { return totalVentasDia; }
    public void setTotalVentasDia(double t)                { this.totalVentasDia = t; }

    public double getTotalVentasMes()                      { return totalVentasMes; }
    public void setTotalVentasMes(double t)                { this.totalVentasMes = t; }

    public int getCantidadVentasDia()                      { return cantidadVentasDia; }
    public void setCantidadVentasDia(int c)                { this.cantidadVentasDia = c; }

    public int getProductosDisponibles()                   { return productosDisponibles; }
    public void setProductosDisponibles(int p)             { this.productosDisponibles = p; }

    public int getProductosReservados()                    { return productosReservados; }
    public void setProductosReservados(int p)              { this.productosReservados = p; }

    public double getIngresoPromedioPorVenta()             { return ingresoPromedioPorVenta; }
    public void setIngresoPromedioPorVenta(double i)       { this.ingresoPromedioPorVenta = i; }

    public int getTotalClientesRegistrados()               { return totalClientesRegistrados; }
    public void setTotalClientesRegistrados(int t)         { this.totalClientesRegistrados = t; }
}