package modulo2_gestionClientes.views;

import modulo2_gestionClientes.controllers.ReporteClienteController;
import modulo2_gestionClientes.models.Cliente;

import java.util.List;
import java.util.Scanner;

public class ReporteClientePanel {

    private ReporteClienteController controller;
    private Scanner scanner;

    public ReporteClientePanel() {
        this.controller = new ReporteClienteController();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== REPORTES DE CLIENTES =====");
            System.out.println("1. Reporte todos los clientes");
            System.out.println("2. Reporte por categoria");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1: reporteTodos(); break;
                case 2: reportePorCategoria(); break;
                case 0: break;
                default: System.out.println("Opcion invalida");
            }
        } while (opcion != 0);
    }

    private void reporteTodos() {
        List<Cliente> lista = controller.reporteTodos();
        controller.mostrarReporte(lista);
    }

    private void reportePorCategoria() {
        System.out.print("ID de la categoria: ");
        int idCategoria = scanner.nextInt();
        List<Cliente> lista = controller.reportePorCategoria(idCategoria);
        controller.mostrarReporte(lista);
    }
}
