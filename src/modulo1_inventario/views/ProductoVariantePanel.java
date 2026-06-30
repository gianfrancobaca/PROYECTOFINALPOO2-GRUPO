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
        this.controller    = controller;
        this.comboProducto = new JComboBox<>();
        this.campoTalla    = new JTextField(8);
        this.campoColor    = new JTextField(12);
        this.campoStock    = new JTextField(6);
        String[] cols = {"Producto", "Código Variante", "Talla", "Color", "Stock"};
        this.modeloTabla = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        this.tabla = new JTable(modeloTabla);

        setLayout(new BorderLayout(5, 5));
        construirUI();
        cargarProductos();
    }

    private void construirUI() {
        // ── Formulario en panel con GridBagLayout para que no se comprima ──
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Nueva Variante (Talla / Color)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(6, 8, 6, 8);
        gbc.anchor  = GridBagConstraints.WEST;
        gbc.fill    = GridBagConstraints.HORIZONTAL;

        // Fila 0: Producto
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelForm.add(new JLabel("Producto:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        comboProducto.setPreferredSize(new Dimension(220, 26));
        panelForm.add(comboProducto, gbc);

        // Fila 1: Talla
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelForm.add(new JLabel("Talla:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panelForm.add(campoTalla, gbc);

        // Fila 2: Color
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelForm.add(new JLabel("Color:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panelForm.add(campoColor, gbc);

        // Fila 3: Stock
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        panelForm.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panelForm.add(campoStock, gbc);

        // Fila 4: Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JButton btnAgregar  = new JButton("Agregar Variante");
        JButton btnLimpiar  = new JButton("Limpiar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnLimpiar);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.weightx = 1.0;
        panelForm.add(panelBotones, gbc);

        btnAgregar.addActionListener(e -> agregarVariante());
        btnLimpiar.addActionListener(e -> limpiar());

        // ── Tabla de variantes registradas ──
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Variantes registradas en esta sesión"));

        add(panelForm, BorderLayout.NORTH);
        add(scroll,    BorderLayout.CENTER);
    }

    private void cargarProductos() {
        comboProducto.removeAllItems();
        for (Producto p : controller.obtenerActivos())
            comboProducto.addItem(p.getId() + " – " + p.getNombre());
    }

    private void agregarVariante() {
        String seleccion = (String) comboProducto.getSelectedItem();
        if (seleccion == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }
        String talla = campoTalla.getText().trim();
        String color = campoColor.getText().trim();
        String stockTxt = campoStock.getText().trim();

        if (talla.isEmpty() || color.isEmpty() || stockTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete Talla, Color y Stock.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int stock = Integer.parseInt(stockTxt);
            if (stock < 0) throw new NumberFormatException();

            // Genera un código de variante simple: COD-TALLA-COLOR
            String codigoProd = seleccion.split(" – ")[0]; // ID numérico
            String codVariante = "P" + codigoProd + "-" + talla.toUpperCase() + "-" + color.toUpperCase().replace(" ", "");

            String productoNombre = seleccion.substring(seleccion.indexOf("–") + 2).trim();

            modeloTabla.addRow(new Object[]{
                    productoNombre, codVariante, talla, color, stock
            });

            JOptionPane.showMessageDialog(this,
                    "Variante registrada correctamente:\n" +
                            "  Código : " + codVariante + "\n" +
                            "  Talla  : " + talla + "\n" +
                            "  Color  : " + color + "\n" +
                            "  Stock  : " + stock,
                    "Variante agregada", JOptionPane.INFORMATION_MESSAGE);

            limpiar();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El stock debe ser un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiar() {
        campoTalla.setText("");
        campoColor.setText("");
        campoStock.setText("");
    }
}