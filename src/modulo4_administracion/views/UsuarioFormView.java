package modulo4_administracion.views;

import modulo4_administracion.controllers.UsuarioController;
import core.Model;
import core.View;
import modulo4_administracion.models.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Formulario para crear o editar un usuario del sistema.
 * RF: Gestión de Usuarios.
 */
@SuppressWarnings("serial")
public class UsuarioFormView extends JPanel implements View {

    private final UsuarioController controller;

    private JTextField   txtNombre, txtApellido, txtCorreo;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRol;
    private JCheckBox    chkActivo;
    private JButton      btnGuardar, btnCancelar;

    public UsuarioFormView(UsuarioController controller) {
        this.controller = controller;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Formulario de Usuario"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        String[] etiquetas = {"Nombre:", "Apellido:", "Correo:", "Contraseña:", "Rol:", "Activo:"};
        int fila = 0;
        for (String etiq : etiquetas) {
            gbc.gridx = 0; gbc.gridy = fila;
            add(new JLabel(etiq), gbc);
            fila++;
        }

        txtNombre   = new JTextField(18);
        txtApellido = new JTextField(18);
        txtCorreo   = new JTextField(18);
        txtPassword = new JPasswordField(18);
        cmbRol      = new JComboBox<>();
        chkActivo   = new JCheckBox();
        chkActivo.setSelected(true);

        // Cargar roles en el combo
        List<Rol> roles = controller.obtenerRoles();
        for (Rol r : roles) cmbRol.addItem(r.getId() + " - " + r.getNombre());

        JComponent[] campos = {txtNombre, txtApellido, txtCorreo, txtPassword, cmbRol, chkActivo};
        for (int i = 0; i < campos.length; i++) {
            gbc.gridx = 1; gbc.gridy = i;
            add(campos[i], gbc);
        }

        btnGuardar  = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBtn.add(btnGuardar);
        panelBtn.add(btnCancelar);
        gbc.gridx = 0; gbc.gridy = etiquetas.length;
        gbc.gridwidth = 2;
        add(panelBtn, gbc);

        btnGuardar.addActionListener(e -> guardarUsuario());
        btnCancelar.addActionListener(e -> controller.mostrarLista());
    }

    private void guardarUsuario() {
        String nombre   = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String correo   = txtCorreo.getText().trim();
        String pass     = new String(txtPassword.getPassword());
        boolean activo  = chkActivo.isSelected();

        if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int rolId = 1;
        if (cmbRol.getSelectedItem() != null) {
            rolId = Integer.parseInt(cmbRol.getSelectedItem().toString().split(" - ")[0]);
        }

        Usuario usuario = new Usuario(0, nombre, apellido, correo, pass, activo, rolId);
        controller.registrarUsuario(usuario);
        controller.mostrarLista();
    }

    @Override
    public void update(Model model, Object data) {}
}