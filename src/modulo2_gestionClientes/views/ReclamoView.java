package modulo2_gestionClientes.views;

import modulo2_gestionClientes.controllers.ReclamoController;
import modulo2_gestionClientes.models.Cliente;
import modulo2_gestionClientes.models.Reclamo;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ReclamoView {

    private ReclamoController controller;
    private Scanner scanner;

    public ReclamoView() {
        this.controller = new ReclamoController();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== GESTION DE RECLAMOS =====");
            System.out.println("1. Registrar reclamo");
            System.out.println("2. Actualizar reclamo");
            System.out.println("3. Eliminar reclamo");
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
        System.out.print("Motivo: ");
        String motivo = scanner.nextLine();
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        Reclamo reclamo = new Reclamo(0, motivo, "PENDIENTE", new Date(), cliente);
        controller.agregar(reclamo);
        System.out.println("Reclamo registrado exitosamente");
    }

    private void actualizar() {
        System.out.print("ID del reclamo: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo motivo: ");
        String motivo = scanner.nextLine();
        System.out.print("Nuevo estado (PENDIENTE/EN PROCESO/RESUELTO): ");
        String estado = scanner.nextLine();
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        Reclamo reclamo = new Reclamo(id, motivo, estado, new Date(), cliente);
        controller.actualizar(reclamo);
        System.out.println("Reclamo actualizado exitosamente");
    }

    private void eliminar() {
        System.out.print("ID del reclamo a eliminar: ");
        int id = scanner.nextInt();
        controller.eliminar(id);
        System.out.println("Reclamo eliminado exitosamente");
    }

    private void listarPorCliente() {
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        List<Reclamo> lista = controller.listarPorCliente(idCliente);
        if (lista.isEmpty()) {
            System.out.println("No hay reclamos para este cliente");
        } else {
            lista.forEach(System.out::println);
        }
    }
}