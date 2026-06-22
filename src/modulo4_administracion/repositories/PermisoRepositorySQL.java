package modulo4_administracion.repositories;

import database.DatabaseConnection;
import modulo4_administracion.models.Permiso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PermisoRepositorySQL implements PermisoRepository {

    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    private Permiso mapear(ResultSet rs) throws SQLException {
        return new Permiso(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("modulo"),
                rs.getString("descripcion")
        );
    }

    @Override
    public Permiso buscarPorId(int id) {
        String sql = "SELECT * FROM permisos WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("[PermisoRepo] buscarPorId: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Permiso> buscarTodos() {
        List<Permiso> lista = new ArrayList<>();
        String sql = "SELECT * FROM permisos";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[PermisoRepo] buscarTodos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Permiso> buscarPorRol(int rolId) {
        List<Permiso> lista = new ArrayList<>();
        String sql = "SELECT p.* FROM permisos p "
                + "INNER JOIN roles_permisos rp ON p.id = rp.permiso_id "
                + "WHERE rp.rol_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rolId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[PermisoRepo] buscarPorRol: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Permiso> buscarPorModulo(String modulo) {
        List<Permiso> lista = new ArrayList<>();
        String sql = "SELECT * FROM permisos WHERE modulo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, modulo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[PermisoRepo] buscarPorModulo: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void guardar(Permiso permiso) {
        String sql = "INSERT INTO permisos (nombre, modulo, descripcion) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, permiso.getNombre());
            ps.setString(2, permiso.getModulo());
            ps.setString(3, permiso.getDescripcion());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) permiso.setId(keys.getInt(1));
        } catch (SQLException e) {
            System.err.println("[PermisoRepo] guardar: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM permisos WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[PermisoRepo] eliminar: " + e.getMessage());
        }
    }

    @Override
    public void asignarPermisoARol(int rolId, int permisoId) {
        String sql = "INSERT IGNORE INTO roles_permisos (rol_id, permiso_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rolId);
            ps.setInt(2, permisoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[PermisoRepo] asignarPermisoARol: " + e.getMessage());
        }
    }

    @Override
    public void revocarPermisoDeRol(int rolId, int permisoId) {
        String sql = "DELETE FROM roles_permisos WHERE rol_id = ? AND permiso_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rolId);
            ps.setInt(2, permisoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[PermisoRepo] revocarPermisoDeRol: " + e.getMessage());
        }
    }
}