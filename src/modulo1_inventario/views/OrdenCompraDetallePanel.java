package modulo1_inventario.views;

import modulo1_inventario.controllers.OrdenCompraController;
import modulo1_inventario.models.DetalleOrdenCompra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OrdenCompraDetallePanel extends JPanel {

    private final OrdenCompraController controller;
    private final JTextField            campoOrdenId;
    private final DefaultTableModel     modeloTabla;
    private final JTable                tabla;
    private final JLabel                labelTotal;

    public OrdenCompraDetallePanel(OrdenCompraController controller) {
        this.controller  = controller;
        this.campoOrdenId = new JTextField(6);
        String[] cols = {"ID", "Producto", "Cantidad", "P. Unitario", "Subtotal"};
        this.modeloTabla = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        this.tabla      = new JTable(modeloTabla);
        this.labelTotal = new JLabel("Total: S/ 0.00");
        setLayout(new BorderLayout(5, 5));
        construirUI();
    }

    private void construirUI() {
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("ID Orden:"));
        panelBusqueda.add(campoOrdenId);
        JButton btnBuscar = new JButton("Cargar Detalle");
        btnBuscar.addActionListener(e -> cargarDetalle());
        panelBusqueda.add(btnBuscar);

        add(panelBusqueda, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(labelTotal, BorderLayout.SOUTH);
    }

    private void cargarDetalle() {
        try {
            int ordenId = Integer.parseInt(campoOrdenId.getText().trim());
            modeloTabla.setRowCount(0);
            double total = 0;
            for (DetalleOrdenCompra d : controller.obtenerDetalles(ordenId)) {
                modeloTabla.addRow(new Object[]{
                    d.getId(), d.getProductoNombre(),
                    d.getCantidad(), d.getPrecioUnitario(), d.getSubtotal()
                });
                total += d.getSubtotal();
            }
            labelTotal.setText(String.format("Total: S/ %.2f", total));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}