package modulo4_administracion.repositories;

import modulo4_administracion.models.ExportacionReporte;
import java.util.List;

public interface ExportacionReporteRepository {
    ExportacionReporte         buscarPorId(int id);
    List<ExportacionReporte>   buscarTodos();
    List<ExportacionReporte>   buscarPorUsuario(int usuarioId);
    void                       guardar(ExportacionReporte exportacion);
}