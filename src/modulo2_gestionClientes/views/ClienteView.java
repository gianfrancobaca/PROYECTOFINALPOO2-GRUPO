package modulo2_gestionClientes.views;

import modulo2_gestionClientes.controllers.ClienteController;
import modulo2_gestionClientes.models.CategoriaCliente;
import modulo2_gestionClientes.models.Cliente;

import java.util.List;
import java.util.Scanner;

public class ClienteView {

    private ClienteController controller;
    private Scanner scanner;

    public ClienteView() {
        this.controller = new ClienteController();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== GESTION DE CLIENTES =====");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Actualizar cliente");
            System.out.println("3. Eliminar cliente");
            System.out.println("4. Buscar cliente por ID");
            System.out.println("5. Listar todos");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1: agregar(); break;
                case 2: actualizar(); break;
                case 3: eliminar(); break;
                case 4: buscarPorId(); break;
                case 5: listarTodos(); break;
                case 0: break;
                default: System.out.println("Opcion invalida");
            }
        } while (opcion != 0);
    }

    private void agregar() {
        scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Direccion: ");
        String direccion = scanner.nextLine();
        System.out.print("ID de categoria: ");
        int idCategoria = scanner.nextInt();
        CategoriaCliente categoria = new CategoriaCliente();
        categoria.setIdCategoria(idCategoria);
        Cliente cliente = new Cliente(0, nombre, apellido, email, direccion, categoria);
        controller.agregar(cliente);
        System.out.println("Cliente agregado exitosamente");
    }

    private void actualizar() {
        System.out.print("ID del cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevo apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Nuevo email: ");
        String email = scanner.nextLine();
        System.out.print("Nueva direccion: ");
        String direccion = scanner.nextLine();
        System.out.print("ID de categoria: ");
        int idCategoria = scanner.nextInt();
        CategoriaCliente categoria = new CategoriaCliente();
        categoria.setIdCategoria(idCategoria);
        Cliente cliente = new Cliente(id, nombre, apellido, email, direccion, categoria);
        controller.actualizar(cliente);
        System.out.println("Cliente actualizado exitosamente");
    }

    private void eliminar() {
        System.out.print("ID del cliente a eliminar: ");
        int id = scanner.nextInt();
        controller.eliminar(id);
        System.out.println("Cliente eliminado exitosamente");
    }

    private void buscarPorId() {
        System.out.print("ID del cliente: ");
        int id = scanner.nextInt();
        Cliente cliente = controller.buscarPorId(id);
        if (cliente != null) {
            System.out.println(cliente);
        } else {
            System.out.println("Cliente no encontrado");
        }
    }

    private void listarTodos() {
        List<Cliente> lista = controller.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay clientes registrados");
        } else {
            lista.forEach(System.out::println);
        }
    }
}
