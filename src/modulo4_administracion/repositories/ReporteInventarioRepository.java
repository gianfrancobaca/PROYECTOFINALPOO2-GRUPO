package modulo4_administracion.repositories;

import modulo4_administracion.models.ReporteInventario;
import java.time.LocalDate;


public interface ReporteInventarioRepository {
    ReporteInventario generarReporte(LocalDate fecha);
    int               contarProductosPorEstado(String estado);
    double            calcularValorInventario();
}