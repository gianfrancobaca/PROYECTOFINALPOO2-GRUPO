package modulo4_administracion.repositories;

import database.DatabaseConnection;
import models.Usuario;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación SQL del repositorio de usuarios.
 * Usa PreparedStatement para evitar SQL Injection.
 */
public class UsuarioRepositorySQL implements UsuarioRepository {

    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setCorreo(rs.getString("correo"));
        u.setContrasenia(rs.getString("contrasenia"));
        u.setActivo(rs.getBoolean("activo"));
        u.setRolId(rs.getInt("rol_id"));
        Timestamp ts = rs.getTimestamp("fecha_creacion");
        if (ts != null) u.setFechaCreacion(ts.toLocalDateTime());
        return u;
    }

    @Override
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("[UsuarioRepo] buscarPorId: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("[UsuarioRepo] buscarPorCorreo: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> buscarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY nombre";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[UsuarioRepo] buscarTodos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Usuario> buscarPorRol(int rolId) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE rol_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rolId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[UsuarioRepo] buscarPorRol: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, correo, contrasenia, activo, rol_id, fecha_creacion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getContrasenia());
            ps.setBoolean(5, usuario.isActivo());
            ps.setInt(6, usuario.getRolId());
            ps.setTimestamp(7, Timestamp.valueOf(
                    usuario.getFechaCreacion() != null
                            ? usuario.getFechaCreacion()
                            : LocalDateTime.now()));
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) usuario.setId(keys.getInt(1));
        } catch (SQLException e) {
            System.err.println("[UsuarioRepo] guardar: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre=?, apellido=?, correo=?, contrasenia=?, activo=?, rol_id=? "
                + "WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getContrasenia());
            ps.setBoolean(5, usuario.isActivo());
            ps.setInt(6, usuario.getRolId());
            ps.setInt(7, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[UsuarioRepo] actualizar: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[UsuarioRepo] eliminar: " + e.getMessage());
        }
    }

    @Override
    public boolean existeCorreo(String correo) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("[UsuarioRepo] existeCorreo: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void cambiarEstado(int id, boolean activo) {
        String sql = "UPDATE usuarios SET activo = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, activo);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[UsuarioRepo] cambiarEstado: " + e.getMessage());
        }
    }
}