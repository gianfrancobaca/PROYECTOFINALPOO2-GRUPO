package modulo4_administracion.models;

import java.time.LocalDate;

public class ReporteCliente {

    private LocalDate fechaGeneracion;
    private int       totalClientes;
    private int       clientesNuevos;
    private int       clientesRecurrentes;
    private double    promedioComprasPorCliente;
    private String    clienteMayorCompra;

    public ReporteCliente() {}

    public ReporteCliente(LocalDate fechaGeneracion, int totalClientes,
                          int clientesNuevos, int clientesRecurrentes,
                          double promedioComprasPorCliente, String clienteMayorCompra) {
        this.fechaGeneracion             = fechaGeneracion;
        this.totalClientes               = totalClientes;
        this.clientesNuevos              = clientesNuevos;
        this.clientesRecurrentes         = clientesRecurrentes;
        this.promedioComprasPorCliente   = promedioComprasPorCliente;
        this.clienteMayorCompra          = clienteMayorCompra;
    }

    // ── Getters & Setters ──────────────────────────────────────────
    public LocalDate getFechaGeneracion()                  { return fechaGeneracion; }
    public void setFechaGeneracion(LocalDate f)            { this.fechaGeneracion = f; }

    public int getTotalClientes()                          { return totalClientes; }
    public void setTotalClientes(int t)                    { this.totalClientes = t; }

    public int getClientesNuevos()                         { return clientesNuevos; }
    public void setClientesNuevos(int c)                   { this.clientesNuevos = c; }

    public int getClientesRecurrentes()                    { return clientesRecurrentes; }
    public void setClientesRecurrentes(int c)              { this.clientesRecurrentes = c; }

    public double getPromedioComprasPorCliente()           { return promedioComprasPorCliente; }
    public void setPromedioComprasPorCliente(double p)     { this.promedioComprasPorCliente = p; }

    public String getClienteMayorCompra()                  { return clienteMayorCompra; }
    public void setClienteMayorCompra(String c)            { this.clienteMayorCompra = c; }

    @Override
    public String toString() {
        return String.format("ReporteCliente[%s | Total: %d | Nuevos: %d | Recurrentes: %d]",
                fechaGeneracion, totalClientes, clientesNuevos, clientesRecurrentes);
    }
}