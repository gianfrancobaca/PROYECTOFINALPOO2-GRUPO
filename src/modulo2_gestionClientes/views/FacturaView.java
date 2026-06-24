package modulo2_gestionClientes.views;

import modulo2_gestionClientes.controllers.FacturaController;
import modulo2_gestionClientes.models.Factura;

import java.util.List;
import java.util.Scanner;

public class FacturaView {

    private FacturaController controller;
    private Scanner scanner;

    public FacturaView() {
        this.controller = new FacturaController();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== GESTION DE FACTURAS =====");
            System.out.println("1. Buscar factura por ID");
            System.out.println("2. Listar todas");
            System.out.println("3. Eliminar factura");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1: buscarPorId(); break;
                case 2: listarTodos(); break;
                case 3: eliminar(); break;
                case 0: break;
                default: System.out.println("Opcion invalida");
            }
        } while (opcion != 0);
    }

    private void buscarPorId() {
        System.out.print("ID de la factura: ");
        int id = scanner.nextInt();
        Factura factura = controller.buscarPorId(id);
        if (factura != null) {
            System.out.println(factura);
        } else {
            System.out.println("Factura no encontrada");
        }
    }

    private void listarTodos() {
        List<Factura> lista = controller.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay facturas registradas");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void eliminar() {
        System.out.print("ID de la factura a eliminar: ");
        int id = scanner.nextInt();
        controller.eliminar(id);
        System.out.println("Factura eliminada exitosamente");
    }
}
