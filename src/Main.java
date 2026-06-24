
import modulo2_gestionClientes.views.LoginView;

public class Main {
    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception ignored) {}

        LoginView loginView = new LoginView();
        loginView.mostrarLogin();
    }
}