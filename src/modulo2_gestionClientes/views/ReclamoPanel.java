package modulo2_gestionClientes.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReclamoPanel extends JPanel {

    private JTable tablaReclamos;
    private DefaultTableModel modeloTabla;
    private JTextField txtCliente;
    private JTextArea txtReclamo;

    public ReclamoPanel() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Gestión de Reclamos");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Cliente", "Reclamo"}, 0
        );

        tablaReclamos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaReclamos);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Datos del Reclamo"));
        panelDerecho.setPreferredSize(new Dimension(300, 0));

        txtCliente = new JTextField();
        txtReclamo = new JTextArea(6, 20);
        txtReclamo.setLineWrap(true);
        txtReclamo.setWrapStyleWord(true);

        JButton btnAgregar = new JButton("Registrar Reclamo");
        JButton btnEliminar = new JButton("Eliminar Reclamo");

        panelDerecho.add(new JLabel("Cliente:"));
        panelDerecho.add(txtCliente);
        panelDerecho.add(Box.createVerticalStrut(10));

        panelDerecho.add(new JLabel("Reclamo:"));
        panelDerecho.add(new JScrollPane(txtReclamo));
        panelDerecho.add(Box.createVerticalStrut(20));

        panelDerecho.add(btnAgregar);
        panelDerecho.add(Box.createVerticalStrut(8));
        panelDerecho.add(btnEliminar);

        add(titulo, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);

        btnAgregar.addActionListener(e -> agregarReclamo());
        btnEliminar.addActionListener(e -> eliminarReclamo());
    }

    private void agregarReclamo() {
        String cliente = txtCliente.getText();
        String reclamo = txtReclamo.getText();

        if (cliente.isEmpty() || reclamo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa cliente y reclamo.");
            return;
        }

        int id = modeloTabla.getRowCount() + 1;
        modeloTabla.addRow(new Object[]{id, cliente, reclamo});

        txtCliente.setText("");
        txtReclamo.setText("");
    }

    private void eliminarReclamo() {
        int fila = tablaReclamos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un reclamo para eliminar.");
            return;
        }

        modeloTabla.removeRow(fila);
    }
}
