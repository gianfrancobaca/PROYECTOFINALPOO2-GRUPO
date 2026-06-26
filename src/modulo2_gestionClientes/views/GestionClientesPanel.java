package modulo2_gestionClientes.views;

import core.View;
import modulo2_gestionClientes.controllers.ClienteController;
import modulo2_gestionClientes.controllers.FacturaController;

import javax.swing.*;
import java.awt.*;

public class GestionClientesPanel extends JPanel implements View {

    public GestionClientesPanel(ClienteController clienteCtrl, FacturaController facturaCtrl) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("Gestión de Clientes");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        JTabbedPane pestañas = new JTabbedPane();

        pestañas.addTab("Clientes", new ClientePanel(clienteCtrl));
        pestañas.addTab("Facturas", new FacturaPanel(facturaCtrl));
        pestañas.addTab("Categorías", crearPanelTemporal("Gestión de categorías de clientes"));
        pestañas.addTab("Reclamos", new ReclamoPanel());
        pestañas.addTab("Fidelización", crearPanelTemporal("Programa de fidelización"));
        pestañas.addTab("Notificaciones", crearPanelTemporal("Notificaciones de clientes"));
        pestañas.addTab("Reportes", crearPanelTemporal("Reportes de clientes"));

        add(titulo, BorderLayout.NORTH);
        add(pestañas, BorderLayout.CENTER);
    }

    private JPanel crearPanelTemporal(String texto) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));

        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
    @Override
    public void update(core.Model model, Object data) {
        // No se usa por ahora
    }

}