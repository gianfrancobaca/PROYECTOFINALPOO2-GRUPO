package modulo4_administracion.repositories;

import database.DatabaseConnection;
import models.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación SQL del repositorio de roles.
 */
public class RolRepositorySQL implements RolRepository {

    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    private Rol mapear(ResultSet rs) throws SQLException {
        return new Rol(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("descripcion")
        );
    }

    @Override
    public Rol buscarPorId(int id) {
        String sql = "SELECT * FROM roles WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("[RolRepo] buscarPorId: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Rol buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM roles WHERE nombre = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("[RolRepo] buscarPorNombre: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Rol> buscarTodos() {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT * FROM roles";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[RolRepo] buscarTodos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void guardar(Rol rol) {
        String sql = "INSERT INTO roles (nombre, descripcion) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rol.getNombre());
            ps.setString(2, rol.getDescripcion());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) rol.setId(keys.getInt(1));
        } catch (SQLException e) {
            System.err.println("[RolRepo] guardar: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Rol rol) {
        String sql = "UPDATE roles SET nombre=?, descripcion=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rol.getNombre());
            ps.setString(2, rol.getDescripcion());
            ps.setInt(3, rol.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[RolRepo] actualizar: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM roles WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[RolRepo] eliminar: " + e.getMessage());
        }
    }
}