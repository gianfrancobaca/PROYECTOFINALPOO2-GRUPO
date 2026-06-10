package modulo4_administracion.repositories;

import modulo4_administracion.models.ReporteInventario;
import java.time.LocalDate;

/**
 * Contrato del repositorio para reportes de inventario (solo lectura).
 * RF: Generación de Reportes de Inventario.
 */
public interface ReporteInventarioRepository {
    ReporteInventario generarReporte(LocalDate fecha);
    int               contarProductosPorEstado(String estado);
    double            calcularValorInventario();
}