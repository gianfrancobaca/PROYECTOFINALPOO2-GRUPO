package modulo4_administracion.repositories;

import modulo4_administracion.models.ReporteCliente;
import java.time.LocalDate;


public interface ReporteClienteRepository {
    ReporteCliente generarReporte(LocalDate inicio, LocalDate fin);
    int            contarClientesNuevos(LocalDate inicio, LocalDate fin);
    int            contarClientesRecurrentes();
    String         clienteConMayorCompra(LocalDate inicio, LocalDate fin);
    double         promedioComprasPorCliente(LocalDate inicio, LocalDate fin);
}