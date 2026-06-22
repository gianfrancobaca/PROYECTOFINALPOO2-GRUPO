package modulo4_administracion.views;

import modulo4_administracion.controllers.*;
import core.Model;
import core.View;
import modulo4_administracion.models.SesionUsuario;
import javax.swing.*;
import java.awt.*;

public class AdminPanelView extends JPanel implements View {

    private final AdminController controller;

    public AdminPanelView(AdminController controller) {
        this.controller = controller;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        setBackground(new Color(250, 245, 240));

        // ── Header ─────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(139, 90, 43));
        header.setPreferredSize(new Dimension(0, 60));
        JLabel titulo = new JLabel("  Módulo: Administración y Reportes | Trapos y Sedas",
                SwingConstants.LEFT);
        titulo.setFont(new Font("Serif", Font.BOLD, 17));
        titulo.setForeground(Color.WHITE);
        header.add(titulo, BorderLayout.WEST);

        SesionUsuario sesion = SesionUsuario.getInstance();
        String infoUsuario = sesion.isAutenticado()
                ? "Usuario: " + sesion.getUsuarioActual().getNombre() + "  "
                : "  ";
        JLabel lblUsuario = new JLabel(infoUsuario, SwingConstants.RIGHT);
        lblUsuario.setForeground(Color.WHITE);
        header.add(lblUsuario, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // ── Botones de navegación ──────────────────────────────────
        JPanel panelBotones = new JPanel(new GridLayout(4, 2, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panelBotones.setBackground(new Color(250, 245, 240));

        String[] etiquetas = {
                "Gestión de Usuarios",     "Gestión de Roles y Permisos",
                "Reporte de Ventas",       "Reporte de Inventario",
                "Reporte de Clientes",     "Historial de Operaciones",
                "Indicadores KPI",         "Cerrar Sesión"
        };

        for (String etiqueta : etiquetas) {
            JButton btn = crearBoton(etiqueta);
            panelBotones.add(btn);
        }
        add(panelBotones, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setBackground(new Color(210, 180, 140));
        btn.setForeground(new Color(60, 30, 10));
        btn.setFocusPainted(false);
        btn.addActionListener(e -> navegarA(texto));
        return btn;
    }

    private void navegarA(String opcion) {
        switch (opcion) {
            case "Gestión de Usuarios"        -> new UsuarioController().run();
            case "Gestión de Roles y Permisos"-> new RolController().run();
            case "Reporte de Ventas"           -> new ReporteVentaController().run();
            case "Reporte de Inventario"       -> new ReporteInventarioController().run();
            case "Reporte de Clientes"         -> new ReporteClienteController().run();
            case "Historial de Operaciones"    -> new HistorialController().run();
            case "Indicadores KPI"             -> new IndicadorController().run();
            case "Cerrar Sesión"               -> new LoginController().cerrarSesion();
        }
    }

    @Override
    public void update(Model model, Object data) {}
}