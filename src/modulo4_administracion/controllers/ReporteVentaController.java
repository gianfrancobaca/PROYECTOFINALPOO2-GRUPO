package modulo4_administracion.controllers;

import core.Controller;
import modulo4_administracion.models.*;
import modulo4_administracion.repositories.*;
import modulo4_administracion.views.*;
import java.time.LocalDate;

/**
 * Controlador para la generación de reportes de ventas.
 * RF: Generación de Reportes de Ventas.
 */
public class ReporteVentaController extends Controller {

    private final ReporteVentaRepository           reporteRepo;
    private final HistorialOperacionRepository      historialRepo;
    private final ExportacionReporteRepository      exportRepo;
    private final ReporteModel                      reporteModel;
    private       ReporteVentaView                  reporteVentaView;

    public ReporteVentaController() {
        this.reporteRepo   = new ReporteVentaRepositorySQL();
        this.historialRepo = new HistorialOperacionRepositorySQL();
        this.exportRepo    = new ExportacionReporteRepositorySQL();
        this.reporteModel  = new ReporteModel();
    }

    @Override
    public void run() {
        reporteVentaView = new ReporteVentaView(this);
        reporteModel.attach(reporteVentaView);
        addView("ReporteVentaView", reporteVentaView);
        loadView("ReporteVentaView");
    }

    public void generarReporte(LocalDate inicio, LocalDate fin) {
        ReporteVenta reporte = reporteRepo.generarReporte(inicio, fin);
        reporteModel.setReporteVenta(reporte);
        registrarGeneracion("Reporte de ventas: " + inicio + " al " + fin);
    }

    public void exportarReporte(ReporteVenta reporte,
                                ExportacionReporte.FormatoExportacion formato) {
        String ruta = generarRuta("ventas", formato);
        ExportacionReporte exp = new ExportacionReporte(0,
                SesionUsuario.getInstance().getUsuarioActual().getId(),
                ExportacionReporte.TipoReporte.VENTAS,
                formato, ruta,
                java.time.LocalDateTime.now(), true);
        exportRepo.guardar(exp);
        registrarGeneracion("Exportación reporte ventas: " + formato);
    }

    public ReporteModel getModel() { return reporteModel; }

    private void registrarGeneracion(String descripcion) {
        SesionUsuario sesion = SesionUsuario.getInstance();
        if (!sesion.isAutenticado()) return;
        HistorialOperacion op = new HistorialOperacion(0,
                sesion.getUsuarioActual().getId(),
                sesion.getUsuarioActual().getNombre(),
                HistorialOperacion.TipoOperacion.GENERACION_REPORTE,
                descripcion, java.time.LocalDateTime.now(), "127.0.0.1");
        historialRepo.registrar(op);
    }

    private String generarRuta(String tipo, ExportacionReporte.FormatoExportacion fmt) {
        return "reportes/" + tipo + "_" + LocalDate.now() + "." + fmt.name().toLowerCase();
    }
}