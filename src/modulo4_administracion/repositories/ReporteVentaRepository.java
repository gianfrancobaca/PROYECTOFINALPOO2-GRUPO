package modulo4_administracion.repositories;

import modulo4_administracion.models.ReporteVenta;
import java.time.LocalDate;

/**
 * Contrato del repositorio para consultas de datos de ventas (solo lectura).
 * RF: Generación de Reportes de Ventas.
 */
public interface ReporteVentaRepository {
    ReporteVenta generarReporte(LocalDate inicio, LocalDate fin);
    double       totalIngresosPorPeriodo(LocalDate inicio, LocalDate fin);
    int          cantidadVentasPorPeriodo(LocalDate inicio, LocalDate fin);
    String       metodoPagoMasFrecuente(LocalDate inicio, LocalDate fin);
    String       periodoMayorActividad(int anio, int mes);
}