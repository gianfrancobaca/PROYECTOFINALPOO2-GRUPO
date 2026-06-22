package modulo4_administracion.views;

import modulo4_administracion.controllers.IndicadorController;
import core.Model;
import core.View;
import modulo4_administracion.models.IndicadorAdministrativo;
import javax.swing.*;
import java.awt.*;


public class IndicadorView extends JPanel implements View {

    private final IndicadorController controller;

    private JLabel lblVentasDia, lblVentasMes, lblCantVentasDia;
    private JLabel lblDisponibles, lblReservados, lblPromedio, lblClientes;
    private JButton btnRefrescar, btnVolver;

    public IndicadorView(IndicadorController controller) {
        this.controller = controller;
        inicializarComponentes();
        refrescarIndicadores();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ── Título ─────────────────────────────────────────────────
        JLabel titulo = new JLabel("Dashboard de Indicadores – Trapos y Sedas",
                SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 18));
        titulo.setForeground(new Color(80, 50, 30));
        add(titulo, BorderLayout.NORTH);

        // ── Panel de tarjetas KPI ──────────────────────────────────
        JPanel panelKPI = new JPanel(new GridLayout(2, 4, 15, 15));
        panelKPI.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelKPI.setBackground(new Color(250, 245, 240));

        lblVentasDia    = crearTarjeta(panelKPI, "Ventas Hoy (S/)",     "0.00");
        lblVentasMes    = crearTarjeta(panelKPI, "Ventas Mes (S/)",     "0.00");
        lblCantVentasDia = crearTarjeta(panelKPI, "Transacciones Hoy", "0");
        lblDisponibles  = crearTarjeta(panelKPI, "Productos Disponibles","0");
        lblReservados   = crearTarjeta(panelKPI, "Productos Reservados","0");
        lblPromedio     = crearTarjeta(panelKPI, "Ticket Promedio (S/)","0.00");
        lblClientes     = crearTarjeta(panelKPI, "Total Clientes",      "0");
        crearTarjeta(panelKPI, "Sistema", "ACTIVO ✔");

        add(panelKPI, BorderLayout.CENTER);

        // ── Botones ────────────────────────────────────────────────
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnRefrescar = new JButton("↻ Refrescar");
        btnVolver    = new JButton("← Volver");
        panelBtns.add(btnRefrescar);
        panelBtns.add(btnVolver);
        add(panelBtns, BorderLayout.SOUTH);

        btnVolver.addActionListener(e    -> core.Controller.loadView("AdminPanelView"));
        btnRefrescar.addActionListener(e -> refrescarIndicadores());
    }

    private JLabel crearTarjeta(JPanel panel, String titulo, String valorInicial) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(new Color(255, 250, 240));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 170, 130), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblTitulo.setForeground(Color.GRAY);

        JLabel lblValor = new JLabel(valorInicial, SwingConstants.CENTER);
        lblValor.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblValor.setForeground(new Color(100, 60, 20));

        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(lblValor,  BorderLayout.CENTER);
        panel.add(tarjeta);
        return lblValor;
    }

    private void refrescarIndicadores() {
        IndicadorAdministrativo ind = controller.obtenerIndicadores();
        if (ind == null) return;
        lblVentasDia.setText(    String.format("%.2f", ind.getTotalVentasDia()));
        lblVentasMes.setText(    String.format("%.2f", ind.getTotalVentasMes()));
        lblCantVentasDia.setText(String.valueOf(ind.getCantidadVentasDia()));
        lblDisponibles.setText(  String.valueOf(ind.getProductosDisponibles()));
        lblReservados.setText(   String.valueOf(ind.getProductosReservados()));
        lblPromedio.setText(     String.format("%.2f", ind.getIngresoPromedioPorVenta()));
        lblClientes.setText(     String.valueOf(ind.getTotalClientesRegistrados()));
    }

    @Override
    public void update(Model model, Object data) {}
}