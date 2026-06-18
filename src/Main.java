
import modulo4_administracion.controllers.LoginController;


public class Main {
    public static void main(String[] args) {
        // Configura Look & Feel del sistema operativo
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception ignored) {}

        // Lanza el módulo por el controlador de login
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginController loginController = new LoginController();
            loginController.run();
        });
    }
}