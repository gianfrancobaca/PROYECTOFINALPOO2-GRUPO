package modulo4_administracion.views;

import modulo4_administracion.controllers.RolController;
import core.Model;
import core.View;
import modulo4_administracion.models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class RolView extends JPanel implements View {

    private final RolController controller;
    private JTable           tablaRoles;
    private DefaultTableModel tableRolesModel;
    private JList<String>    listaPermisos;
    private JButton          btnCrearRol, btnAsignarPermiso, btnVolver;

    public RolView(RolController controller) {
        this.controller = controller;
        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ── Panel izquierdo: roles ──────────────────────────────────
        String[] cols = {"ID", "Nombre", "Descripción"};
        tableRolesModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaRoles = new JTable(tableRolesModel);
        JScrollPane scrollRoles = new JScrollPane(tablaRoles);
        scrollRoles.setBorder(BorderFactory.createTitledBorder("Roles del Sistema"));

        // ── Panel derecho: permisos ─────────────────────────────────
        listaPermisos = new JList<>(new DefaultListModel<>());
        JScrollPane scrollPermisos = new JScrollPane(listaPermisos);
        scrollPermisos.setBorder(BorderFactory.createTitledBorder("Permisos Disponibles"));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                scrollRoles, scrollPermisos);
        split.setDividerLocation(500);
        add(split, BorderLayout.CENTER);

        // ── Botones ────────────────────────────────────────────────
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        btnCrearRol      = new JButton("Crear Rol");
        btnAsignarPermiso = new JButton("Asignar Permiso");
        btnVolver        = new JButton("← Volver");
        panelBtns.add(btnCrearRol);
        panelBtns.add(btnAsignarPermiso);
        panelBtns.add(btnVolver);
        add(panelBtns, BorderLayout.SOUTH);

        btnVolver.addActionListener(e -> core.Controller.loadView("AdminPanelView"));

        btnAsignarPermiso.addActionListener(e -> {
            int filaRol = tablaRoles.getSelectedRow();
            String perm = listaPermisos.getSelectedValue();
            if (filaRol < 0 || perm == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un rol y un permiso.");
                return;
            }
            int rolId     = (int) tableRolesModel.getValueAt(filaRol, 0);
            int permisoId = Integer.parseInt(perm.split(":")[0].trim());
            controller.asignarPermiso(rolId, permisoId);
            JOptionPane.showMessageDialog(this, "Permiso asignado correctamente.");
        });
    }

    private void cargarDatos() {
        List<Rol> roles = controller.obtenerRoles();
        tableRolesModel.setRowCount(0);
        for (Rol r : roles) {
            tableRolesModel.addRow(new Object[]{r.getId(), r.getNombre(), r.getDescripcion()});
        }

        DefaultListModel<String> modeloPermisos = (DefaultListModel<String>) listaPermisos.getModel();
        modeloPermisos.clear();
        List<Permiso> permisos = controller.obtenerPermisos();
        for (Permiso p : permisos) {
            modeloPermisos.addElement(p.getId() + ": [" + p.getModulo() + "] " + p.getNombre());
        }
    }

    @Override
    public void update(Model model, Object data) {}
}