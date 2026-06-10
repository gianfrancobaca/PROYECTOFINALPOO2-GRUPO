package modulo4_administracion.views;

import modulo4_administracion.controllers.LoginController;
import core.Model;
import core.View;
import javax.swing.*;
import java.awt.*;

/**
 * Vista de autenticación del sistema.
 * RF: Seguridad y Control de Acceso.
 */
@SuppressWarnings("serial")
public class LoginView extends JPanel implements View {

    private final LoginController controller;

    private JTextField  txtCorreo;
    private JPasswordField txtPassword;
    private JButton     btnIngresar;
    private JLabel      lblMensaje;

    public LoginView(LoginController controller) {
        this.controller = controller;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 240, 235));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        // ── Título ─────────────────────────────────────────────────
        JLabel titulo = new JLabel("Trapos y Sedas – Administración", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 20));
        titulo.setForeground(new Color(80, 50, 30));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titulo, gbc);

        // ── Correo ─────────────────────────────────────────────────
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Correo:"), gbc);
        txtCorreo = new JTextField(20);
        gbc.gridx = 1;
        add(txtCorreo, gbc);

        // ── Contraseña ─────────────────────────────────────────────
        gbc.gridy = 2; gbc.gridx = 0;
        add(new JLabel("Contraseña:"), gbc);
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        add(txtPassword, gbc);

        // ── Botón ──────────────────────────────────────────────────
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBackground(new Color(139, 90, 43));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("SansSerif", Font.BOLD, 13));
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        add(btnIngresar, gbc);

        // ── Mensaje de error ───────────────────────────────────────
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        gbc.gridy = 4;
        add(lblMensaje, gbc);

        // ── Listener ───────────────────────────────────────────────
        btnIngresar.addActionListener(e -> {
            String correo = txtCorreo.getText().trim();
            String pass   = new String(txtPassword.getPassword());
            if (correo.isEmpty() || pass.isEmpty()) {
                mostrarError("Por favor complete todos los campos.");
                return;
            }
            lblMensaje.setForeground(Color.BLUE);
            lblMensaje.setText("Autenticando...");
            controller.autenticar(correo, pass);
        });
    }

    public void mostrarError(String mensaje) {
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setText(mensaje);
    }

    @Override
    public void update(Model model, Object data) {}
}