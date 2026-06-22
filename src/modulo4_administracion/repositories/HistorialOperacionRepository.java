package modulo4_administracion.repositories;

import modulo4_administracion.models.HistorialOperacion;
import java.time.LocalDateTime;
import java.util.List;


public interface HistorialOperacionRepository {
    HistorialOperacion         buscarPorId(int id);
    List<HistorialOperacion>   buscarTodos();
    List<HistorialOperacion>   buscarPorUsuario(int usuarioId);
    List<HistorialOperacion>   buscarPorTipo(String tipo);
    List<HistorialOperacion>   buscarPorRango(LocalDateTime inicio, LocalDateTime fin);
    void                       registrar(HistorialOperacion operacion);
    int                        contarPorTipo(String tipo);
}