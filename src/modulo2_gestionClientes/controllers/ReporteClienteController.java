package modulo2_gestionClientes.controllers;

import modulo2_gestionClientes.models.Cliente;
import modulo2_gestionClientes.repositories.ClienteRepository;

import java.util.List;

public class ReporteClienteController {

    private ClienteRepository repository;

    public ReporteClienteController() {
        this.repository = new ClienteRepository();
    }

    public List<Cliente> reportePorCategoria(int idCategoria) {
        return repository.listarTodos().stream()
                .filter(c -> c.getCategoria().getIdCategoria() == idCategoria)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<Cliente> reporteTodos() {
        return repository.listarTodos();
    }

    public void mostrarReporte(List<Cliente> clientes) {
        System.out.println("===== REPORTE DE CLIENTES =====");
        for (Cliente c : clientes) {
            System.out.println(c);
        }
        System.out.println("Total: " + clientes.size() + " clientes");
        System.out.println("================================");
    }
}
