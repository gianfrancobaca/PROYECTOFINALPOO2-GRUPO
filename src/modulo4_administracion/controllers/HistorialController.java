package modulo4_administracion.controllers;

import core.Controller;
import modulo4_administracion.models.*;
import modulo4_administracion.repositories.*;
import modulo4_administracion.views.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador para consultar el historial de operaciones.
 * RF: Historial de Operaciones.
 */
public class HistorialController extends Controller {

    private final HistorialOperacionRepository historialRepo;
    private final HistorialModel               historialModel;
    private       HistorialView                historialView;

    public HistorialController() {
        this.historialRepo  = new HistorialOperacionRepositorySQL();
        this.historialModel = new HistorialModel();
    }

    @Override
    public void run() {
        historialView = new HistorialView(this);
        historialModel.attach(historialView);
        addView("HistorialView", historialView);
        cargarHistorial();
        loadView("HistorialView");
    }

    public void cargarHistorial() {
        List<HistorialOperacion> ops = historialRepo.buscarTodos();
        historialModel.setOperaciones(ops);
    }

    public void filtrarPorUsuario(int usuarioId) {
        List<HistorialOperacion> ops = historialRepo.buscarPorUsuario(usuarioId);
        historialModel.setOperaciones(ops);
    }

    public void filtrarPorTipo(String tipo) {
        List<HistorialOperacion> ops = historialRepo.buscarPorTipo(tipo);
        historialModel.setOperaciones(ops);
    }

    public void filtrarPorRango(LocalDateTime inicio, LocalDateTime fin) {
        List<HistorialOperacion> ops = historialRepo.buscarPorRango(inicio, fin);
        historialModel.setOperaciones(ops);
    }

    public HistorialModel getModel() { return historialModel; }
}