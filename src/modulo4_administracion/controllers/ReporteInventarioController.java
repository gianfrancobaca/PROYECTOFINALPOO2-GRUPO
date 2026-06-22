package modulo4_administracion.controllers;

import core.Controller;
import modulo4_administracion.models.*;
import modulo4_administracion.repositories.*;
import modulo4_administracion.views.*;
import java.time.LocalDate;


public class ReporteInventarioController extends Controller {

    private final ReporteInventarioRepository      inventarioRepo;
    private final HistorialOperacionRepository      historialRepo;
    private final ExportacionReporteRepository      exportRepo;
    private final ReporteModel                      reporteModel;
    private       ReporteInventarioView             reporteInventarioView;

    public ReporteInventarioController() {
        this.inventarioRepo = new ReporteInventarioRepositorySQL();
        this.historialRepo  = new HistorialOperacionRepositorySQL();
        this.exportRepo     = new ExportacionReporteRepositorySQL();
        this.reporteModel   = new ReporteModel();
    }

    @Override
    public void run() {
        reporteInventarioView = new ReporteInventarioView(this);
        reporteModel.attach(reporteInventarioView);
        addView("ReporteInventarioView", reporteInventarioView);
        loadView("ReporteInventarioView");
    }

    public void generarReporte() {
        ReporteInventario reporte = inventarioRepo.generarReporte(LocalDate.now());
        reporteModel.setReporteInventario(reporte);
        registrarGeneracion("Reporte de inventario generado: " + LocalDate.now());
    }

    public void exportarReporte(ExportacionReporte.FormatoExportacion formato) {
        String ruta = "reportes/inventario_" + LocalDate.now()
                + "." + formato.name().toLowerCase();
        ExportacionReporte exp = new ExportacionReporte(0,
                SesionUsuario.getInstance().getUsuarioActual().getId(),
                ExportacionReporte.TipoReporte.INVENTARIO,
                formato, ruta, java.time.LocalDateTime.now(), true);
        exportRepo.guardar(exp);
        registrarGeneracion("Exportación reporte inventario: " + formato);
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
}