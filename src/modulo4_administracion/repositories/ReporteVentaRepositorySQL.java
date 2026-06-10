package modulo4_administracion.repositories;

import database.DatabaseConnection;
import modulo4_administracion.models.ReporteVenta;
import java.sql.*;
import java.time.LocalDate;

/**
 * Implementación SQL del repositorio de reportes de ventas.
 * Realiza consultas de agregación sobre la tabla de ventas.
 */
public class ReporteVentaRepositorySQL implements ReporteVentaRepository {

    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    @Override
    public ReporteVenta generarReporte(LocalDate inicio, LocalDate fin) {
        double totalIngresos    = totalIngresosPorPeriodo(inicio, fin);
        int    totalVentas      = cantidadVentasPorPeriodo(inicio, fin);
        String metodoPago       = metodoPagoMasFrecuente(inicio, fin);
        int    productosVendidos = contarProductosVendidos(inicio, fin);
        String periodoActivo    = periodoMayorActividad(inicio.getYear(), inicio.getMonthValue());

        return new ReporteVenta(inicio, fin, totalVentas, totalIngresos,
                productosVendidos, metodoPago, periodoActivo);
    }

    @Override
    public double totalIngresosPorPeriodo(LocalDate inicio, LocalDate fin) {
        String sql = "SELECT COALESCE(SUM(total), 0) FROM ventas "
                + "WHERE fecha_venta BETWEEN ? AND ? AND estado = 'COMPLETADA'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(inicio));
            ps.setDate(2, Date.valueOf(fin));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.err.println("[ReporteVentaRepo] totalIngresos: " + e.getMessage());
        }
        return 0.0;
    }

    @Override
    public int cantidadVentasPorPeriodo(LocalDate inicio, LocalDate fin) {
        String sql = "SELECT COUNT(*) FROM ventas "
                + "WHERE fecha_venta BETWEEN ? AND ? AND estado = 'COMPLETADA'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(inicio));
            ps.setDate(2, Date.valueOf(fin));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("[ReporteVentaRepo] cantidadVentas: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public String metodoPagoMasFrecuente(LocalDate inicio, LocalDate fin) {
        String sql = "SELECT metodo_pago, COUNT(*) AS frecuencia FROM ventas "
                + "WHERE fecha_venta BETWEEN ? AND ? "
                + "GROUP BY metodo_pago ORDER BY frecuencia DESC LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(inicio));
            ps.setDate(2, Date.valueOf(fin));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("metodo_pago");
        } catch (SQLException e) {
            System.err.println("[ReporteVentaRepo] metodoPago: " + e.getMessage());
        }
        return "No disponible";
    }

    @Override
    public String periodoMayorActividad(int anio, int mes) {
        String sql = "SELECT DAY(fecha_venta) AS dia, COUNT(*) AS total FROM ventas "
                + "WHERE YEAR(fecha_venta) = ? AND MONTH(fecha_venta) = ? "
                + "GROUP BY dia ORDER BY total DESC LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, anio);
            ps.setInt(2, mes);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return "Día " + rs.getInt("dia") + " del mes " + mes + "/" + anio;
        } catch (SQLException e) {
            System.err.println("[ReporteVentaRepo] periodoMayorActividad: " + e.getMessage());
        }
        return "No disponible";
    }

    private int contarProductosVendidos(LocalDate inicio, LocalDate fin) {
        String sql = "SELECT COUNT(*) FROM detalle_venta dv "
                + "INNER JOIN ventas v ON dv.venta_id = v.id "
                + "WHERE v.fecha_venta BETWEEN ? AND ? AND v.estado = 'COMPLETADA'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(inicio));
            ps.setDate(2, Date.valueOf(fin));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("[ReporteVentaRepo] contarProductos: " + e.getMessage());
        }
        return 0;
    }
}