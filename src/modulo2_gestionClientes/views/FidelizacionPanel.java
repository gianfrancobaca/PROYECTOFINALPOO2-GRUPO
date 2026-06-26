package modulo2_gestionClientes.views;

import modulo2_gestionClientes.controllers.FidelizacionController;
import modulo2_gestionClientes.models.Cliente;
import modulo2_gestionClientes.models.ProgramaFidelizacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FidelizacionPanel extends JPanel {

    private FidelizacionController controller;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtIdCliente;
    private JTextField txtPuntos;
    private JTextField txtNivel;

    public FidelizacionPanel(FidelizacionController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Gestión de Fidelización");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modelo = new DefaultTableModel(new Object[]{"ID", "ID Cliente", "Puntos", "Nivel"}, 0);
        tabla = new JTable(modelo);

        JPanel form = crearFormulario();

        add(titulo, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(form, BorderLayout.EAST);

        cargarTabla();

        tabla.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());
    }

    private JPanel crearFormulario() {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createTitledBorder("Datos de Fidelización"));
        form.setPreferredSize(new Dimension(300, 0));

        txtIdCliente = new JTextField();
        txtPuntos = new JTextField();
        txtNivel = new JTextField();

        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        form.add(new JLabel("ID Cliente:"));
        form.add(txtIdCliente);
        form.add(Box.createVerticalStrut(10));

        form.add(new JLabel("Puntos:"));
        form.add(txtPuntos);
        form.add(Box.createVerticalStrut(10));

        form.add(new JLabel("Nivel:"));
        form.add(txtNivel);
        form.add(Box.createVerticalStrut(20));

        form.add(btnAgregar);
        form.add(Box.createVerticalStrut(8));
        form.add(btnActualizar);
        form.add(Box.createVerticalStrut(8));
        form.add(btnEliminar);

        btnAgregar.addActionListener(e -> agregar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());

        return form;
    }

    private void cargarTabla() {
        modelo.setRowCount(0);

        List<ProgramaFidelizacion> programas = controller.listarTodos();

        for (ProgramaFidelizacion p : programas) {
            modelo.addRow(new Object[]{
                    p.getIdPrograma(),
                    p.getCliente() != null ? p.getCliente().getIdCliente() : "",
                    p.getPuntos(),
                    p.getNivel()
            });
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();

        if (fila >= 0) {
            txtIdCliente.setText(tabla.getValueAt(fila, 1).toString());
            txtPuntos.setText(tabla.getValueAt(fila, 2).toString());
            txtNivel.setText(tabla.getValueAt(fila, 3).toString());
        }
    }

    private void agregar() {
        try {
            Cliente cliente = new Cliente();
            cliente.setIdCliente(Integer.parseInt(txtIdCliente.getText()));

            ProgramaFidelizacion programa = new ProgramaFidelizacion(
                    0,
                    Integer.parseInt(txtPuntos.getText()),
                    txtNivel.getText(),
                    cliente
            );

            controller.agregar(programa);
            cargarTabla();
            limpiarCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar fidelización: " + e.getMessage());
        }
    }

    private void actualizar() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un registro.");
            return;
        }

        try {
            int idPrograma = Integer.parseInt(tabla.getValueAt(fila, 0).toString());

            Cliente cliente = new Cliente();
            cliente.setIdCliente(Integer.parseInt(txtIdCliente.getText()));

            ProgramaFidelizacion programa = new ProgramaFidelizacion(
                    idPrograma,
                    Integer.parseInt(txtPuntos.getText()),
                    txtNivel.getText(),
                    cliente
            );

            controller.actualizar(programa);
            cargarTabla();
            limpiarCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar fidelización: " + e.getMessage());
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un registro.");
            return;
        }

        int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());

        controller.eliminar(id);
        cargarTabla();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtIdCliente.setText("");
        txtPuntos.setText("");
        txtNivel.setText("");
    }
} 