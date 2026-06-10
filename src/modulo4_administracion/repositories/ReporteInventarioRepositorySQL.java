package modulo4_administracion.repositories;

import database.DatabaseConnection;
import modulo4_administracion.models.ReporteInventario;
import java.sql.*;
import java.time.LocalDate;

/**
 * Implementación SQL del repositorio de reportes de inventario.
 */
public class ReporteInventarioRepositorySQL implements ReporteInventarioRepository {

    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    @Override
    public ReporteInventario generarReporte(LocalDate fecha) {
        int    total       = contarProductosPorEstado(null);   // todos
        int    disponibles = contarProductosPorEstado("DISPONIBLE");
        int    reservados  = contarProductosPorEstado("RESERVADO");
        int    vendidos    = contarProductosPorEstado("VENDIDO");
        double valor       = calcularValorInventario();

        return new ReporteInventario(fecha, total, disponibles, reservados, vendidos, valor);
    }

    @Override
    public int contarProductosPorEstado(String estado) {
        String sql = estado == null
                ? "SELECT COUNT(*) FROM productos"
                : "SELECT COUNT(*) FROM productos WHERE estado = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (estado != null) ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("[ReporteInventarioRepo] contarPorEstado: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public double calcularValorInventario() {
        String sql = "SELECT COALESCE(SUM(precio), 0) FROM productos WHERE estado = 'DISPONIBLE'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.err.println("[ReporteInventarioRepo] calcularValor: " + e.getMessage());
        }
        return 0.0;
    }
}