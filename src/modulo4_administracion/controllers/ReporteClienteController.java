package modulo4_administracion.controllers;

import core.Controller;
import modulo4_administracion.models.*;
import modulo4_administracion.repositories.*;
import modulo4_administracion.views.*;
import java.time.LocalDate;


public class ReporteClienteController extends Controller {

    private final ReporteClienteRepository         clienteRepo;
    private final HistorialOperacionRepository      historialRepo;
    private final ExportacionReporteRepository      exportRepo;
    private final ReporteModel                      reporteModel;
    private       ReporteClienteView                reporteClienteView;

    public ReporteClienteController() {
        this.clienteRepo  = new ReporteClienteRepositorySQL();
        this.historialRepo = new HistorialOperacionRepositorySQL();
        this.exportRepo   = new ExportacionReporteRepositorySQL();
        this.reporteModel = new ReporteModel();
    }

    @Override
    public void run() {
        reporteClienteView = new ReporteClienteView(this);
        reporteModel.attach(reporteClienteView);
        addView("ReporteClienteView", reporteClienteView);
        loadView("ReporteClienteView");
    }

    public void generarReporte(LocalDate inicio, LocalDate fin) {
        ReporteCliente reporte = clienteRepo.generarReporte(inicio, fin);
        reporteModel.setReporteCliente(reporte);
        registrarGeneracion("Reporte de clientes: " + inicio + " al " + fin);
    }

    public void exportarReporte(ExportacionReporte.FormatoExportacion formato) {
        String ruta = "reportes/clientes_" + LocalDate.now()
                + "." + formato.name().toLowerCase();
        ExportacionReporte exp = new ExportacionReporte(0,
                SesionUsuario.getInstance().getUsuarioActual().getId(),
                ExportacionReporte.TipoReporte.CLIENTES,
                formato, ruta, java.time.LocalDateTime.now(), true);
        exportRepo.guardar(exp);
        registrarGeneracion("Exportación reporte clientes: " + formato);
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