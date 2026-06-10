package modulo4_administracion.controllers;

import core.Controller;
import modulo4_administracion.models.*;
import modulo4_administracion.repositories.*;
import modulo4_administracion.views.*;
import java.time.LocalDate;

/**
 * Controlador para el dashboard de indicadores administrativos.
 * RF: Consulta de Indicadores Administrativos.
 */
public class IndicadorController extends Controller {

    private final ReporteVentaRepository      ventaRepo;
    private final ReporteInventarioRepository inventarioRepo;
    private final ReporteClienteRepository    clienteRepo;
    private       IndicadorView               indicadorView;

    public IndicadorController() {
        this.ventaRepo      = new ReporteVentaRepositorySQL();
        this.inventarioRepo = new ReporteInventarioRepositorySQL();
        this.clienteRepo    = new ReporteClienteRepositorySQL();
    }

    @Override
    public void run() {
        indicadorView = new IndicadorView(this);
        addView("IndicadorView", indicadorView);
        loadView("IndicadorView");
    }

    public IndicadorAdministrativo obtenerIndicadores() {
        LocalDate hoy     = LocalDate.now();
        LocalDate inicioMes = hoy.withDayOfMonth(1);

        double ventasDia  = ventaRepo.totalIngresosPorPeriodo(hoy, hoy);
        double ventasMes  = ventaRepo.totalIngresosPorPeriodo(inicioMes, hoy);
        int    cantDia    = ventaRepo.cantidadVentasPorPeriodo(hoy, hoy);
        int    disponibles = inventarioRepo.contarProductosPorEstado("DISPONIBLE");
        int    reservados  = inventarioRepo.contarProductosPorEstado("RESERVADO");

        int    totalClientes = clienteRepo.generarReporte(inicioMes, hoy).getTotalClientes();
        double promedio      = cantDia > 0 ? ventasDia / cantDia : 0.0;

        return new IndicadorAdministrativo(hoy, ventasDia, ventasMes, cantDia,
                disponibles, reservados, promedio, totalClientes);
    }
}