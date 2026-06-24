package modulo2_gestionClientes.views;

import modulo2_gestionClientes.controllers.PedidoController;
import modulo2_gestionClientes.models.Cliente;
import modulo2_gestionClientes.models.Pedido;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PedidoView {

    private PedidoController controller;
    private Scanner scanner;

    public PedidoView() {
        this.controller = new PedidoController();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== GESTION DE PEDIDOS =====");
            System.out.println("1. Agregar pedido");
            System.out.println("2. Actualizar pedido");
            System.out.println("3. Eliminar pedido");
            System.out.println("4. Buscar pedido por ID");
            System.out.println("5. Listar todos");
            System.out.println("6. Listar por cliente");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1: agregar(); break;
                case 2: actualizar(); break;
                case 3: eliminar(); break;
                case 4: buscarPorId(); break;
                case 5: listarTodos(); break;
                case 6: listarPorCliente(); break;
                case 0: break;
                default: System.out.println("Opcion invalida");
            }
        } while (opcion != 0);
    }

    private void agregar() {
        scanner.nextLine();
        System.out.print("Estado (PENDIENTE/ENVIADO/ENTREGADO): ");
        String estado = scanner.nextLine();
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        Pedido pedido = new Pedido(0, new Date(), estado, cliente, null);
        controller.agregar(pedido);
        System.out.println("Pedido agregado exitosamente");
    }

    private void actualizar() {
        System.out.print("ID del pedido: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo estado: ");
        String estado = scanner.nextLine();
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        Pedido pedido = new Pedido(id, new Date(), estado, cliente, null);
        controller.actualizar(pedido);
        System.out.println("Pedido actualizado exitosamente");
    }

    private void eliminar() {
        System.out.print("ID del pedido a eliminar: ");
        int id = scanner.nextInt();
        controller.eliminar(id);
        System.out.println("Pedido eliminado exitosamente");
    }

    private void buscarPorId() {
        System.out.print("ID del pedido: ");
        int id = scanner.nextInt();
        Pedido pedido = controller.buscarPorId(id);
        if (pedido != null) {
            System.out.println(pedido);
        } else {
            System.out.println("Pedido no encontrado");
        }
    }

    private void listarTodos() {
        List<Pedido> lista = controller.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay pedidos registrados");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void listarPorCliente() {
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        List<Pedido> lista = controller.listarPorCliente(idCliente);
        if (lista.isEmpty()) {
            System.out.println("No hay pedidos para este cliente");
        } else {
            lista.forEach(System.out::println);
        }
    }
}