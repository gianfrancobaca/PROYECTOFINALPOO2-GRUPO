package modulo2_gestionClientes.views;

import core.Model;
import core.View;
import javax.swing.*;
import java.awt.*;

public class VentasPlaceholder extends JPanel implements View {
    public VentasPlaceholder() {
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Modulo de Ventas - En construccion", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 18));
        lbl.setForeground(Color.GRAY);
        add(lbl, BorderLayout.CENTER);
    }

    @Override
    public void update(Model model, Object data) {
        // Placeholder: no hay lógica de actualización todavía
    }
}