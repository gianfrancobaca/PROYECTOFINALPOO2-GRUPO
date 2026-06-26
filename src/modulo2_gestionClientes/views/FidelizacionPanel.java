package modulo2_gestionClientes.views;

import modulo2_gestionClientes.controllers.FidelizacionController;
import modulo2_gestionClientes.models.Cliente;
import modulo2_gestionClientes.models.ProgramaFidelizacion;

import java.util.List;
import java.util.Scanner;

public class FidelizacionPanel {

    private FidelizacionController controller;
    private Scanner scanner;

    public FidelizacionPanel() {
        this.controller = new FidelizacionController();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== PROGRAMA DE FIDELIZACION =====");
            System.out.println("1. Agregar programa");
            System.out.println("2. Actualizar programa");
            System.out.println("3. Eliminar programa");
            System.out.println("4. Listar por cliente");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1: agregar(); break;
                case 2: actualizar(); break;
                case 3: eliminar(); break;
                case 4: listarPorCliente(); break;
                case 0: break;
                default: System.out.println("Opcion invalida");
            }
        } while (opcion != 0);
    }

    private void agregar() {
        scanner.nextLine();
        System.out.print("Nombre del programa: ");
        String nombre = scanner.nextLine();
        System.out.print("Puntos acumulados: ");
        int puntos = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nivel (BRONCE/PLATA/ORO): ");
        String nivel = scanner.nextLine();
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        ProgramaFidelizacion programa = new ProgramaFidelizacion(0, nombre, puntos, nivel, cliente);
        controller.agregar(programa);
        System.out.println("Programa agregado exitosamente");
    }

    private void actualizar() {
        System.out.print("ID del programa: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevos puntos: ");
        int puntos = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo nivel: ");
        String nivel = scanner.nextLine();
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        ProgramaFidelizacion programa = new ProgramaFidelizacion(id, nombre, puntos, nivel, cliente);
        controller.actualizar(programa);
        System.out.println("Programa actualizado exitosamente");
    }

    private void eliminar() {
        System.out.print("ID del programa a eliminar: ");
        int id = scanner.nextInt();
        controller.eliminar(id);
        System.out.println("Programa eliminado exitosamente");
    }

    private void listarPorCliente() {
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        List<ProgramaFidelizacion> lista = controller.listarPorCliente(idCliente);
        if (lista.isEmpty()) {
            System.out.println("No hay programas para este cliente");
        } else {
            lista.forEach(System.out::println);
        }
    }
}
