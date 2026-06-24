package modulo2_gestionClientes.views;

import modulo2_gestionClientes.controllers.MetodoPagoController;
import modulo2_gestionClientes.models.Cliente;
import modulo2_gestionClientes.models.MetodoPago;

import java.util.List;
import java.util.Scanner;

public class MetodoPagoView {

    private MetodoPagoController controller;
    private Scanner scanner;

    public MetodoPagoView() {
        this.controller = new MetodoPagoController();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== METODOS DE PAGO =====");
            System.out.println("1. Agregar metodo de pago");
            System.out.println("2. Actualizar metodo de pago");
            System.out.println("3. Eliminar metodo de pago");
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
        System.out.print("Tipo (EFECTIVO/TARJETA/TRANSFERENCIA): ");
        String tipo = scanner.nextLine();
        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        MetodoPago metodo = new MetodoPago(0, tipo, descripcion, cliente);
        controller.agregar(metodo);
        System.out.println("Metodo de pago agregado exitosamente");
    }

    private void actualizar() {
        System.out.print("ID del metodo de pago: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo tipo: ");
        String tipo = scanner.nextLine();
        System.out.print("Nueva descripcion: ");
        String descripcion = scanner.nextLine();
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        MetodoPago metodo = new MetodoPago(id, tipo, descripcion, cliente);
        controller.actualizar(metodo);
        System.out.println("Metodo de pago actualizado exitosamente");
    }

    private void eliminar() {
        System.out.print("ID del metodo de pago a eliminar: ");
        int id = scanner.nextInt();
        controller.eliminar(id);
        System.out.println("Metodo de pago eliminado exitosamente");
    }

    private void listarPorCliente() {
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        List<MetodoPago> lista = controller.listarPorCliente(idCliente);
        if (lista.isEmpty()) {
            System.out.println("No hay metodos de pago para este cliente");
        } else {
            lista.forEach(System.out::println);
        }
    }
}