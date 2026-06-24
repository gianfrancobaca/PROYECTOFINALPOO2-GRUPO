package modulo1_inventario.views;

import modulo1_inventario.controllers.ProductoController;
import modulo1_inventario.models.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductoVariantePanel extends JPanel {

    private final ProductoController controller;
    private final JComboBox<String>  comboProducto;
    private final JTextField         campoTalla;
    private final JTextField         campoColor;
    private final JTextField         campoStock;
    private final DefaultTableModel  modeloTabla;
    private final JTable             tabla;

    public ProductoVariantePanel(ProductoController controller) {
        this.controller   = controller;
        this.comboProducto = new JComboBox<>();
        this.campoTalla    = new JTextField(8);
        this.campoColor    = new JTextField(12);
        this.campoStock    = new JTextField(6);
        String[] cols = {"Código Variante", "Talla", "Color", "Stock"};
        this.modeloTabla = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        this.tabla = new JTable(modeloTabla);

        setLayout(new BorderLayout(5, 5));
        construirUI();
        cargarProductos();
    }

    private void construirUI() {
        JPanel panelForm = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelForm.setBorder(BorderFactory.createTitledBorder("Nueva Variante"));
        panelForm.add(new JLabel("Producto:")); panelForm.add(comboProducto);
        panelForm.add(new JLabel("Talla:"));    panelForm.add(campoTalla);
        panelForm.add(new JLabel("Color:"));    panelForm.add(campoColor);
        panelForm.add(new JLabel("Stock:"));    panelForm.add(campoStock);
        JButton btnAgregar = new JButton("Agregar Variante");
        btnAgregar.addActionListener(e -> agregarVariante());
        panelForm.add(btnAgregar);

        add(panelForm, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void cargarProductos() {
        comboProducto.removeAllItems();
        for (Producto p : controller.obtenerActivos())
            comboProducto.addItem(p.getCodigo() + " - " + p.getNombre());
    }

    private void agregarVariante() {
        JOptionPane.showMessageDialog(this,
            "Variante registrada: Talla=" + campoTalla.getText()
            + " Color=" + campoColor.getText()
            + " Stock=" + campoStock.getText());
        campoTalla.setText(""); campoColor.setText(""); campoStock.setText("");
    }
}