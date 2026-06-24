package modulo2_gestionClientes.views;

import modulo2_gestionClientes.controllers.NotificacionController;
import modulo2_gestionClientes.models.Notificacion;

import java.util.List;
import java.util.Scanner;

public class NotificacionView {

    private NotificacionController controller;
    private Scanner scanner;

    public NotificacionView() {
        this.controller = new NotificacionController();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== NOTIFICACIONES =====");
            System.out.println("1. Ver notificaciones por cliente");
            System.out.println("2. Ver notificaciones no leidas");
            System.out.println("3. Marcar como leida");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1: listarPorCliente(); break;
                case 2: listarNoLeidas(); break;
                case 3: marcarComoLeida(); break;
                case 0: break;
                default: System.out.println("Opcion invalida");
            }
        } while (opcion != 0);
    }

    private void listarPorCliente() {
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        List<Notificacion> lista = controller.listarPorCliente(idCliente);
        if (lista.isEmpty()) {
            System.out.println("No hay notificaciones para este cliente");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void listarNoLeidas() {
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        List<Notificacion> lista = controller.listarNoLeidas(idCliente);
        if (lista.isEmpty()) {
            System.out.println("No hay notificaciones sin leer");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void marcarComoLeida() {
        System.out.print("ID de la notificacion: ");
        int id = scanner.nextInt();
        controller.marcarComoLeida(id);
        System.out.println("Notificacion marcada como leida");
    }
}
