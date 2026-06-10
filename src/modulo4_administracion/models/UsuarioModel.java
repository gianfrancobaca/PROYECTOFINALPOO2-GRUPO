package modulo4_administracion.models;

import core.Model;
import core.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo observable para la gestión de usuarios.
 * Notifica a las vistas cuando cambia el estado de los usuarios.
 */
public class UsuarioModel implements Model {

    private List<View>    observers  = new ArrayList<>();
    private List<Usuario> usuarios   = new ArrayList<>();
    private String        ultimaAccion;

    @Override
    public void attach(View view)   { observers.add(view); }

    @Override
    public void detach(View view)   { observers.remove(view); }

    @Override
    public void notifyViews() {
        for (View v : observers) {
            v.update(this, ultimaAccion);
        }
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios    = usuarios;
        this.ultimaAccion = "LISTA_ACTUALIZADA";
        notifyViews();
    }

    public List<Usuario> getUsuarios() { return usuarios; }

    public void registrarAccion(String accion) {
        this.ultimaAccion = accion;
        notifyViews();
    }
}