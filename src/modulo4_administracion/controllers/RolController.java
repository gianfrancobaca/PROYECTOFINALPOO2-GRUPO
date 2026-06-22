package modulo4_administracion.controllers;

import core.Controller;
import modulo4_administracion.models.*;
import modulo4_administracion.repositories.*;
import modulo4_administracion.views.*;
import java.util.List;


public class RolController extends Controller {

    private final RolRepository     rolRepo;
    private final PermisoRepository permisoRepo;
    private       RolView           rolView;

    public RolController() {
        this.rolRepo     = new RolRepositorySQL();
        this.permisoRepo = new PermisoRepositorySQL();
    }

    @Override
    public void run() {
        rolView = new RolView(this);
        addView("RolView", rolView);
        loadView("RolView");
    }

    public List<Rol> obtenerRoles() {
        return rolRepo.buscarTodos();
    }

    public List<Permiso> obtenerPermisos() {
        return permisoRepo.buscarTodos();
    }

    public List<Permiso> obtenerPermisosPorRol(int rolId) {
        return permisoRepo.buscarPorRol(rolId);
    }

    public void crearRol(Rol rol) {
        rolRepo.guardar(rol);
    }

    public void actualizarRol(Rol rol) {
        rolRepo.actualizar(rol);
    }

    public void eliminarRol(int id) {
        rolRepo.eliminar(id);
    }

    public void asignarPermiso(int rolId, int permisoId) {
        permisoRepo.asignarPermisoARol(rolId, permisoId);
    }

    public void revocarPermiso(int rolId, int permisoId) {
        permisoRepo.revocarPermisoDeRol(rolId, permisoId);
    }
}