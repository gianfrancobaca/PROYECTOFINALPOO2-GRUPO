package modulo4_administracion.models;

import core.Model;
import core.View;
import java.util.ArrayList;
import java.util.List;


public class HistorialModel implements Model {

    private List<View>               observers  = new ArrayList<>();
    private List<HistorialOperacion> operaciones = new ArrayList<>();
    private String                   ultimaAccion;

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

    public void setOperaciones(List<HistorialOperacion> ops) {
        this.operaciones  = ops;
        this.ultimaAccion = "HISTORIAL_CARGADO";
        notifyViews();
    }

    public void agregarOperacion(HistorialOperacion op) {
        this.operaciones.add(op);
        this.ultimaAccion = "OPERACION_REGISTRADA";
        notifyViews();
    }

    public List<HistorialOperacion> getOperaciones() { return operaciones; }
}