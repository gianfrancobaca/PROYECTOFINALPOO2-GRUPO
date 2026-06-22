package modulo4_administracion.repositories;

import database.DatabaseConnection;
import modulo4_administracion.models.ExportacionReporte;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExportacionReporteRepositorySQL implements ExportacionReporteRepository {

    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    private ExportacionReporte mapear(ResultSet rs) throws SQLException {
        ExportacionReporte e = new ExportacionReporte();
        e.setId(rs.getInt("id"));
        e.setUsuarioId(rs.getInt("usuario_id"));
        e.setTipoReporte(ExportacionReporte.TipoReporte.valueOf(rs.getString("tipo_reporte")));
        e.setFormato(ExportacionReporte.FormatoExportacion.valueOf(rs.getString("formato")));
        e.setRutaArchivo(rs.getString("ruta_archivo"));
        e.setFechaExportacion(rs.getTimestamp("fecha_exportacion").toLocalDateTime());
        e.setExitoso(rs.getBoolean("exitoso"));
        return e;
    }

    @Override
    public ExportacionReporte buscarPorId(int id) {
        String sql = "SELECT * FROM exportaciones_reportes WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("[ExportacionRepo] buscarPorId: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<ExportacionReporte> buscarTodos() {
        List<ExportacionReporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM exportaciones_reportes ORDER BY fecha_exportacion DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[ExportacionRepo] buscarTodos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<ExportacionReporte> buscarPorUsuario(int usuarioId) {
        List<ExportacionReporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM exportaciones_reportes WHERE usuario_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[ExportacionRepo] buscarPorUsuario: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void guardar(ExportacionReporte exportacion) {
        String sql = "INSERT INTO exportaciones_reportes "
                + "(usuario_id, tipo_reporte, formato, ruta_archivo, fecha_exportacion, exitoso) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, exportacion.getUsuarioId());
            ps.setString(2, exportacion.getTipoReporte().name());
            ps.setString(3, exportacion.getFormato().name());
            ps.setString(4, exportacion.getRutaArchivo());
            ps.setTimestamp(5, Timestamp.valueOf(exportacion.getFechaExportacion()));
            ps.setBoolean(6, exportacion.isExitoso());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) exportacion.setId(keys.getInt(1));
        } catch (SQLException e) {
            System.err.println("[ExportacionRepo] guardar: " + e.getMessage());
        }
    }
}
