package modulo2_gestionClientes.views;

import modulo2_gestionClientes.controllers.LoginController;

import java.util.Scanner;

public class LoginView {

    private LoginController controller;
    private Scanner scanner;

    public LoginView() {
        this.controller = new LoginController();
        this.scanner = new Scanner(System.in);
    }

    public boolean mostrarLogin() {
        System.out.println("\n===== INICIO DE SESION =====");
        System.out.print("Usuario: ");
        String username = scanner.nextLine();
        System.out.print("Contrasena: ");
        String password = scanner.nextLine();

        boolean resultado = controller.iniciarSesion(username, password);
        if (resultado) {
            System.out.println("Sesion iniciada correctamente. Bienvenido " + controller.getUsuarioActivo().getNombre());
        } else {
            System.out.println("Usuario o contrasena incorrectos");
        }
        return resultado;
    }

    public void cerrarSesion() {
        controller.cerrarSesion();
        System.out.println("Sesion cerrada correctamente");
    }
}
