package modulo4_administracion.repositories;

import database.DatabaseConnection;
import modulo4_administracion.models.HistorialOperacion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistorialOperacionRepositorySQL implements HistorialOperacionRepository {

    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    private HistorialOperacion mapear(ResultSet rs) throws SQLException {
        HistorialOperacion h = new HistorialOperacion();
        h.setId(rs.getInt("id"));
        h.setUsuarioId(rs.getInt("usuario_id"));
        h.setUsuarioNombre(rs.getString("usuario_nombre"));
        h.setTipo(HistorialOperacion.TipoOperacion.valueOf(rs.getString("tipo")));
        h.setDescripcion(rs.getString("descripcion"));
        h.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
        h.setIpOrigen(rs.getString("ip_origen"));
        return h;
    }

    @Override
    public HistorialOperacion buscarPorId(int id) {
        String sql = "SELECT * FROM historial_operaciones WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("[HistorialRepo] buscarPorId: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<HistorialOperacion> buscarTodos() {
        List<HistorialOperacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM historial_operaciones ORDER BY fecha_hora DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[HistorialRepo] buscarTodos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<HistorialOperacion> buscarPorUsuario(int usuarioId) {
        List<HistorialOperacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM historial_operaciones WHERE usuario_id = ? ORDER BY fecha_hora DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[HistorialRepo] buscarPorUsuario: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<HistorialOperacion> buscarPorTipo(String tipo) {
        List<HistorialOperacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM historial_operaciones WHERE tipo = ? ORDER BY fecha_hora DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[HistorialRepo] buscarPorTipo: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<HistorialOperacion> buscarPorRango(LocalDateTime inicio, LocalDateTime fin) {
        List<HistorialOperacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM historial_operaciones WHERE fecha_hora BETWEEN ? AND ? "
                + "ORDER BY fecha_hora DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(inicio));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[HistorialRepo] buscarPorRango: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void registrar(HistorialOperacion operacion) {
        String sql = "INSERT INTO historial_operaciones "
                + "(usuario_id, usuario_nombre, tipo, descripcion, fecha_hora, ip_origen) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, operacion.getUsuarioId());
            ps.setString(2, operacion.getUsuarioNombre());
            ps.setString(3, operacion.getTipo().name());
            ps.setString(4, operacion.getDescripcion());
            ps.setTimestamp(5, Timestamp.valueOf(
                    operacion.getFechaHora() != null
                            ? operacion.getFechaHora()
                            : LocalDateTime.now()));
            ps.setString(6, operacion.getIpOrigen());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) operacion.setId(keys.getInt(1));
        } catch (SQLException e) {
            System.err.println("[HistorialRepo] registrar: " + e.getMessage());
        }
    }

    @Override
    public int contarPorTipo(String tipo) {
        String sql = "SELECT COUNT(*) FROM historial_operaciones WHERE tipo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("[HistorialRepo] contarPorTipo: " + e.getMessage());
        }
        return 0;
    }
}