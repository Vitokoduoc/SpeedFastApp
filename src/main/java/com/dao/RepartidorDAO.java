package com.dao;

import com.model.Repartidor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Data Access Object (DAO) para la entidad Repartidor.
 * Gestiona las operaciones CRUD directamente en la base de datos MySQL.
 */
public class RepartidorDAO {

    /**
     * Crea (Registra) un nuevo repartidor en la base de datos.
     * * @param repartidor Objeto Repartidor con los datos a guardar.
     * @return true si se guardó exitosamente, false en caso contrario.
     */
    public boolean create(Repartidor repartidor) {
        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, repartidor.getNombre());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar repartidor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lee (Lista) todos los repartidores almacenados en la base de datos.
     * * @return Lista de objetos Repartidor.
     */
    public List<Repartidor> readAll() {
        List<Repartidor> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM repartidores";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                // Usamos el nuevo constructor que armamos para la vista
                lista.add(new Repartidor(id, nombre));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar repartidores: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza (Edita) los datos de un repartidor existente.
     * * @param repartidor Objeto Repartidor con los datos actualizados y su ID original.
     * @return true si se actualizó exitosamente, false en caso contrario.
     */
    public boolean update(Repartidor repartidor) {
        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, repartidor.getNombre());
            ps.setInt(2, repartidor.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar repartidor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un repartidor de la base de datos según su ID.
     * * @param id Identificador del repartidor a eliminar.
     * @return true si se eliminó exitosamente, false en caso contrario.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM repartidores WHERE id = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar repartidor: " + e.getMessage());
            return false;
        }
    }
}