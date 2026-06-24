package modulo1_inventario.views;

import modulo1_inventario.controllers.AjusteDevolucionController;
import modulo1_inventario.controllers.ProductoController;
import modulo1_inventario.models.DetalleDevolucion;
import modulo1_inventario.models.Devolucion;
import modulo1_inventario.models.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DevolucionPanel extends JPanel {

    private final AjusteDevolucionController ajusteCtrl;
    private final ProductoController         productoCtrl;

    private final JComboBox<String> comboTipo;
    private final JTextField        campoMotivo;
    private final JComboBox<String> comboProducto;
    private final JTextField        campoCantidad;
    private final JTextField        campoMotivoLinea;
    private final DefaultTableModel modeloLineas;
    private final JTable            tablaLineas;

    public DevolucionPanel(AjusteDevolucionController ajusteCtrl,
                           ProductoController productoCtrl) {
        this.ajusteCtrl   = ajusteCtrl;
        this.productoCtrl = productoCtrl;
        this.comboTipo       = new JComboBox<>(new String[]{"CLIENTE","PROVEEDOR"});
        this.campoMotivo     = new JTextField(25);
        this.comboProducto   = new JComboBox<>();
        this.campoCantidad   = new JTextField(6);
        this.campoMotivoLinea= new JTextField(15);
        String[] cols = {"Producto ID","Producto","Cantidad","Motivo"};
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
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar Devolución"));
        panelForm.add(new JLabel("Tipo:")); panelForm.add(comboTipo);
        panelForm.add(new JLabel("Motivo General:")); panelForm.add(campoMotivo);
        panelForm.add(new JLabel("Producto:")); panelForm.add(comboProducto);
        panelForm.add(new JLabel("Cantidad:")); panelForm.add(campoCantidad);
        panelForm.add(new JLabel("Motivo Línea:")); panelForm.add(campoMotivoLinea);

        JButton btnAgregar   = new JButton("Agregar Línea");
        JButton btnRegistrar = new JButton("Registrar Devolución");
        panelForm.add(btnAgregar); panelForm.add(btnRegistrar);

        btnAgregar.addActionListener(e   -> agregarLinea());
        btnRegistrar.addActionListener(e -> registrarDevolucion());

        add(panelForm, BorderLayout.NORTH);
        add(new JScrollPane(tablaLineas), BorderLayout.CENTER);
    }

    private void cargarProductos() {
        comboProducto.removeAllItems();
        for (Producto p : productoCtrl.obtenerActivos())
            comboProducto.addItem(p.getId() + " - " + p.getNombre());
    }

    private void agregarLinea() {
        try {
            String sel = (String) comboProducto.getSelectedItem();
            if (sel == null) return;
            int productoId = Integer.parseInt(sel.split(" - ")[0]);
            Producto p = productoCtrl.buscarPorId(productoId);
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());
            modeloLineas.addRow(new Object[]{
                p.getId(), p.getNombre(), cantidad, campoMotivoLinea.getText().trim()
            });
            campoCantidad.setText(""); campoMotivoLinea.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarDevolucion() {
        if (modeloLineas.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agregue al menos una línea."); return;
        }
        try {
            Devolucion devolucion = new Devolucion(0,
                    Devolucion.TipoDevolucion.valueOf((String) comboTipo.getSelectedItem()),
                    0, campoMotivo.getText().trim(), LocalDateTime.now(), 1);
            List<DetalleDevolucion> detalles = new ArrayList<>();
            for (int i = 0; i < modeloLineas.getRowCount(); i++) {
                detalles.add(new DetalleDevolucion(0, 0,
                        (int) modeloLineas.getValueAt(i, 0),
                        (String) modeloLineas.getValueAt(i, 1),
                        (int) modeloLineas.getValueAt(i, 2),
                        (String) modeloLineas.getValueAt(i, 3)));
            }
            if ("CLIENTE".equals(comboTipo.getSelectedItem()))
                ajusteCtrl.registrarDevolucionCliente(devolucion, detalles, 1);
            else
                ajusteCtrl.registrarDevolucionProveedor(devolucion, detalles, 1);

            JOptionPane.showMessageDialog(this, "Devolución registrada correctamente.");
            modeloLineas.setRowCount(0); campoMotivo.setText(""); cargarProductos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
