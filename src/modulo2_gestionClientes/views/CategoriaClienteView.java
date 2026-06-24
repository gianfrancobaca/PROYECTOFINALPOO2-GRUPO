package modulo2_gestionClientes.views;

import modulo2_gestionClientes.controllers.CategoriaClienteController;
import modulo2_gestionClientes.models.CategoriaCliente;

import java.util.List;
import java.util.Scanner;

public class CategoriaClienteView {

    private CategoriaClienteController controller;
    private Scanner scanner;

    public CategoriaClienteView() {
        this.controller = new CategoriaClienteController();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== CATEGORIAS DE CLIENTE =====");
            System.out.println("1. Agregar categoria");
            System.out.println("2. Actualizar categoria");
            System.out.println("3. Eliminar categoria");
            System.out.println("4. Buscar categoria por ID");
            System.out.println("5. Listar todas");
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
        System.out.print("Tipo (VIP/REGULAR/NUEVO/GENERAL): ");
        String tipo = scanner.nextLine();
        controller.agregar(tipo);
        System.out.println("Categoria agregada exitosamente");
    }

    private void actualizar() {
        System.out.print("ID de la categoria: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nueva descripcion: ");
        String descripcion = scanner.nextLine();
        System.out.print("Nuevo descuento: ");
        double descuento = scanner.nextDouble();
        CategoriaCliente categoria = new CategoriaCliente(id, nombre, descripcion, descuento);
        controller.actualizar(categoria);
        System.out.println("Categoria actualizada exitosamente");
    }

    private void eliminar() {
        System.out.print("ID de la categoria a eliminar: ");
        int id = scanner.nextInt();
        controller.eliminar(id);
        System.out.println("Categoria eliminada exitosamente");
    }

    private void buscarPorId() {
        System.out.print("ID de la categoria: ");
        int id = scanner.nextInt();
        CategoriaCliente categoria = controller.buscarPorId(id);
        if (categoria != null) {
            System.out.println(categoria);
        } else {
            System.out.println("Categoria no encontrada");
        }
    }

    private void listarTodos() {
        List<CategoriaCliente> lista = controller.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay categorias registradas");
        } else {
            lista.forEach(System.out::println);
        }
    }
}
