package modulo4_administracion.models;

import core.Model;
import core.View;
import java.util.ArrayList;
import java.util.List;


public class ReporteModel implements Model {

    private List<View> observers = new ArrayList<>();

    private ReporteVenta       ultimoReporteVenta;
    private ReporteInventario  ultimoReporteInventario;
    private ReporteCliente     ultimoReporteCliente;
    private String             ultimaAccion;

    @Override
    public void attach(View view)  { observers.add(view); }

    @Override
    public void detach(View view)  { observers.remove(view); }

    @Override
    public void notifyViews() {
        for (View v : observers) {
            v.update(this, ultimaAccion);
        }
    }

    public void setReporteVenta(ReporteVenta reporte) {
        this.ultimoReporteVenta = reporte;
        this.ultimaAccion = "REPORTE_VENTAS_GENERADO";
        notifyViews();
    }

    public void setReporteInventario(ReporteInventario reporte) {
        this.ultimoReporteInventario = reporte;
        this.ultimaAccion = "REPORTE_INVENTARIO_GENERADO";
        notifyViews();
    }

    public void setReporteCliente(ReporteCliente reporte) {
        this.ultimoReporteCliente = reporte;
        this.ultimaAccion = "REPORTE_CLIENTES_GENERADO";
        notifyViews();
    }

    public ReporteVenta      getUltimoReporteVenta()       { return ultimoReporteVenta; }
    public ReporteInventario getUltimoReporteInventario()  { return ultimoReporteInventario; }
    public ReporteCliente    getUltimoReporteCliente()     { return ultimoReporteCliente; }
}