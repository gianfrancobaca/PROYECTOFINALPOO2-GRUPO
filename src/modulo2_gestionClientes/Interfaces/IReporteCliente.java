package modulo2_gestionClientes.interfaces;

import modulo2_gestionClientes.models.Cliente;
import java.util.List;

public interface IReporteCliente {
    List<Cliente> reporteClientesPorCategoria(int idCategoria);
    List<Cliente> reporteClientesActivos();
    void generarReportePDF(List<Cliente> clientes);
}