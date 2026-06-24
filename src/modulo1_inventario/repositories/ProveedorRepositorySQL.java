package modulo1_inventario.repositories;

import database.DatabaseConnection;
import modulo1_inventario.models.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorRepositorySQL implements ProveedorRepository {

    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    private Proveedor mapear(ResultSet rs) throws SQLException {
        Proveedor p = new Proveedor();
        p.setId(rs.getInt("id")); p.setRuc(rs.getString("ruc"));
        p.setRazonSocial(rs.getString("razon_social")); p.setContacto(rs.getString("contacto"));
        p.setTelefono(rs.getString("telefono")); p.setCorreo(rs.getString("correo"));
        p.setDireccion(rs.getString("direccion")); p.setActivo(rs.getBoolean("activo"));
        return p;
    }

    @Override public Proveedor buscarPorId(int id) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM inv_proveedores WHERE id=?")) {
            ps.setInt(1, id); ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) { System.err.println("[ProveedorRepo] " + e.getMessage()); }
        return null;
    }

    @Override public Proveedor buscarPorRuc(String ruc) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM inv_proveedores WHERE ruc=?")) {
            ps.setString(1, ruc); ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) { System.err.println("[ProveedorRepo] " + e.getMessage()); }
        return null;
    }

    @Override public List<Proveedor> buscarTodos() {
        List<Proveedor> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM inv_proveedores ORDER BY razon_social")) {
            ResultSet rs = ps.executeQuery(); while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) { System.err.println("[ProveedorRepo] " + e.getMessage()); }
        return lista;
    }

    @Override public List<Proveedor> buscarActivos() {
        List<Proveedor> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM inv_proveedores WHERE activo=true ORDER BY razon_social")) {
            ResultSet rs = ps.executeQuery(); while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) { System.err.println("[ProveedorRepo] " + e.getMessage()); }
        return lista;
    }

    @Override public void guardar(Proveedor p) {
        String sql = "INSERT INTO inv_proveedores (ruc, razon_social, contacto, telefono, correo, direccion, activo) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,p.getRuc()); ps.setString(2,p.getRazonSocial());
            ps.setString(3,p.getContacto()); ps.setString(4,p.getTelefono());
            ps.setString(5,p.getCorreo()); ps.setString(6,p.getDireccion());
            ps.setBoolean(7,p.isActivo()); ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys(); if (keys.next()) p.setId(keys.getInt(1));
        } catch (SQLException e) { System.err.println("[ProveedorRepo] " + e.getMessage()); }
    }

    @Override public void actualizar(Proveedor p) {
        String sql = "UPDATE inv_proveedores SET ruc=?, razon_social=?, contacto=?, telefono=?, correo=?, direccion=?, activo=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,p.getRuc()); ps.setString(2,p.getRazonSocial());
            ps.setString(3,p.getContacto()); ps.setString(4,p.getTelefono());
            ps.setString(5,p.getCorreo()); ps.setString(6,p.getDireccion());
            ps.setBoolean(7,p.isActivo()); ps.setInt(8,p.getId()); ps.executeUpdate();
        } catch (SQLException e) { System.err.println("[ProveedorRepo] " + e.getMessage()); }
    }

    @Override public void eliminar(int id) {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE inv_proveedores SET activo=false WHERE id=?")) {
            ps.setInt(1,id); ps.executeUpdate();
        } catch (SQLException e) { System.err.println("[ProveedorRepo] " + e.getMessage()); }
    }
}