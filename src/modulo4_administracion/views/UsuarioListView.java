package modulo4_administracion.views;

import modulo4_administracion.controllers.UsuarioController;
import core.Model;
import core.View;
import modulo4_administracion.models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Vista que lista todos los usuarios del sistema con opciones CRUD.
 * RF: Gestión de Usuarios.
 */
@SuppressWarnings("serial")
public class UsuarioListView extends JPanel implements View {

    private final UsuarioController controller;
    private JTable           tabla;
    private DefaultTableModel tableModel;
    private JButton          btnNuevo, btnEditar, btnEliminar, btnVolver;

    public UsuarioListView(UsuarioController controller) {
        this.controller = controller;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ── Tabla ──────────────────────────────────────────────────
        String[] columnas = {"ID", "Nombre", "Apellido", "Correo", "Rol ID", "Activo"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(tableModel);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(22);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ── Botones ────────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        btnNuevo    = new JButton("Nuevo Usuario");
        btnEditar   = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnVolver   = new JButton("← Volver");

        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        // ── Listeners ──────────────────────────────────────────────
        btnNuevo.addActionListener(e -> controller.mostrarFormulario());
        btnVolver.addActionListener(e -> core.Controller.loadView("AdminPanelView"));

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { mostrarAviso("Seleccione un usuario."); return; }
            int id = (int) tableModel.getValueAt(fila, 0);
            int conf = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar usuario ID " + id + "?", "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) controller.eliminarUsuario(id);
        });

        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { mostrarAviso("Seleccione un usuario."); return; }
            controller.mostrarFormulario();
        });
    }

    private void mostrarAviso(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void update(Model model, Object data) {
        if (model instanceof UsuarioModel um) {
            tableModel.setRowCount(0);
            List<Usuario> usuarios = um.getUsuarios();
            for (Usuario u : usuarios) {
                tableModel.addRow(new Object[]{
                        u.getId(), u.getNombre(), u.getApellido(),
                        u.getCorreo(), u.getRolId(), u.isActivo() ? "Sí" : "No"
                });
            }
        }
    }
}