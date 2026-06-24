package modulo1_inventario.views;

import modulo1_inventario.controllers.AjusteDevolucionController;
import modulo1_inventario.controllers.ProductoController;
import modulo1_inventario.models.AjusteInventario;
import modulo1_inventario.models.DetalleAjuste;
import modulo1_inventario.models.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AjusteInventarioPanel extends JPanel {

    private final AjusteDevolucionController ajusteCtrl;
    private final ProductoController         productoCtrl;

    private final JComboBox<String> comboTipo;
    private final JTextField        campoMotivo;
    private final JComboBox<String> comboProducto;
    private final JTextField        campoStockNuevo;
    private final DefaultTableModel modeloLineas;
    private final JTable            tablaLineas;

    public AjusteInventarioPanel(AjusteDevolucionController ajusteCtrl,
                                  ProductoController productoCtrl) {
        this.ajusteCtrl   = ajusteCtrl;
        this.productoCtrl = productoCtrl;
        this.comboTipo     = new JComboBox<>(new String[]{"CONTEO_FISICO","CORRECCION_ERROR","MERMA","OTRO"});
        this.campoMotivo   = new JTextField(25);
        this.comboProducto = new JComboBox<>();
        this.campoStockNuevo = new JTextField(8);
        String[] cols = {"Producto ID","Producto","Stock Ant.","Stock Nuevo","Diferencia"};
        this.modeloLineas = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        this.tablaLineas = new JTable(modeloLineas);

        setLayout(new BorderLayout(5, 5));
        construirUI();
        cargarProductos();
    }

    private void construirUI() {
        JPanel panelForm = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelForm.setBorder(BorderFactory.createTitledBorder("Ajuste de Inventario"));
        panelForm.add(new JLabel("Tipo:")); panelForm.add(comboTipo);
        panelForm.add(new JLabel("Motivo:")); panelForm.add(campoMotivo);
        panelForm.add(new JLabel("Producto:")); panelForm.add(comboProducto);
        panelForm.add(new JLabel("Stock Nuevo:")); panelForm.add(campoStockNuevo);

        JButton btnAgregar   = new JButton("Agregar Línea");
        JButton btnRegistrar = new JButton("Registrar Ajuste");
        JButton btnLimpiar   = new JButton("Limpiar");
        panelForm.add(btnAgregar); panelForm.add(btnRegistrar); panelForm.add(btnLimpiar);

        btnAgregar.addActionListener(e   -> agregarLinea());
        btnRegistrar.addActionListener(e -> registrarAjuste());
        btnLimpiar.addActionListener(e   -> { modeloLineas.setRowCount(0); campoMotivo.setText(""); });

        add(panelForm, BorderLayout.NORTH);
        add(new JScrollPane(tablaLineas), BorderLayout.CENTER);
    }

    private void cargarProductos() {
        comboProducto.removeAllItems();
        for (Producto p : productoCtrl.obtenerActivos())
            comboProducto.addItem(p.getId() + " - " + p.getNombre() + " [stock=" + p.getStockActual() + "]");
    }

    private void agregarLinea() {
        try {
            String sel = (String) comboProducto.getSelectedItem();
            if (sel == null) return;
            int productoId = Integer.parseInt(sel.split(" - ")[0]);
            Producto p = productoCtrl.buscarPorId(productoId);
            int stockNuevo = Integer.parseInt(campoStockNuevo.getText().trim());
            modeloLineas.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getStockActual(), stockNuevo, stockNuevo - p.getStockActual()
            });
            campoStockNuevo.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Stock inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarAjuste() {
        if (modeloLineas.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agregue al menos una línea."); return;
        }
        AjusteInventario ajuste = new AjusteInventario(0,
                AjusteInventario.TipoAjuste.valueOf((String) comboTipo.getSelectedItem()),
                campoMotivo.getText().trim(), LocalDateTime.now(), 1);
        List<DetalleAjuste> detalles = new ArrayList<>();
        for (int i = 0; i < modeloLineas.getRowCount(); i++) {
            detalles.add(new DetalleAjuste(0,
                    0, (int) modeloLineas.getValueAt(i, 0),
                    (String) modeloLineas.getValueAt(i, 1),
                    (int) modeloLineas.getValueAt(i, 2),
                    (int) modeloLineas.getValueAt(i, 3)));
        }
        ajusteCtrl.registrarAjuste(ajuste, detalles, 1);
        JOptionPane.showMessageDialog(this, "Ajuste registrado correctamente.");
        modeloLineas.setRowCount(0); campoMotivo.setText(""); cargarProductos();
    }
}