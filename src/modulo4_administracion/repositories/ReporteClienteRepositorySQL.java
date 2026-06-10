package modulo4_administracion.repositories;

import database.DatabaseConnection;
import modulo4_administracion.models.ReporteCliente;
import java.sql.*;
import java.time.LocalDate;

/**
 * Implementación SQL del repositorio de reportes de clientes.
 */
public class ReporteClienteRepositorySQL implements ReporteClienteRepository {

    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    @Override
    public ReporteCliente generarReporte(LocalDate inicio, LocalDate fin) {
        int    total        = contarTotalClientes();
        int    nuevos       = contarClientesNuevos(inicio, fin);
        int    recurrentes  = contarClientesRecurrentes();
        double promedio     = promedioComprasPorCliente(inicio, fin);
        String mayorCompra  = clienteConMayorCompra(inicio, fin);

        return new ReporteCliente(LocalDate.now(), total, nuevos, recurrentes, promedio, mayorCompra);
    }

    private int contarTotalClientes() {
        String sql = "SELECT COUNT(*) FROM clientes";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("[ReporteClienteRepo] contarTotal: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int contarClientesNuevos(LocalDate inicio, LocalDate fin) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE fecha_registro BETWEEN ? AND ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(inicio));
            ps.setDate(2, Date.valueOf(fin));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("[ReporteClienteRepo] contarNuevos: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int contarClientesRecurrentes() {
        String sql = "SELECT COUNT(*) FROM (SELECT cliente_id FROM ventas "
                + "GROUP BY cliente_id HAVING COUNT(*) > 1) AS recurrentes";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("[ReporteClienteRepo] contarRecurrentes: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public String clienteConMayorCompra(LocalDate inicio, LocalDate fin) {
        String sql = "SELECT c.nombre, c.apellido, SUM(v.total) AS total_comprado "
                + "FROM clientes c INNER JOIN ventas v ON c.id = v.cliente_id "
                + "WHERE v.fecha_venta BETWEEN ? AND ? "
                + "GROUP BY c.id ORDER BY total_comprado DESC LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(inicio));
            ps.setDate(2, Date.valueOf(fin));
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("nombre") + " " + rs.getString("apellido");
        } catch (SQLException e) {
            System.err.println("[ReporteClienteRepo] clienteMayorCompra: " + e.getMessage());
        }
        return "No disponible";
    }

    @Override
    public double promedioComprasPorCliente(LocalDate inicio, LocalDate fin) {
        String sql = "SELECT AVG(total_por_cliente) FROM "
                + "(SELECT SUM(total) AS total_por_cliente FROM ventas "
                + " WHERE fecha_venta BETWEEN ? AND ? GROUP BY cliente_id) AS sub";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(inicio));
            ps.setDate(2, Date.valueOf(fin));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.err.println("[ReporteClienteRepo] promedio: " + e.getMessage());
        }
        return 0.0;
    }
}