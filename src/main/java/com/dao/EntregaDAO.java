package com.dao;

import com.model.Entrega;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Data Access Object (DAO) para la entidad Entrega.
 * Gestiona el registro histórico que asocia un Pedido con un Repartidor.
 */
public class EntregaDAO {

    /**
     * Crea un nuevo registro de entrega en la base de datos.
     * @param entrega Objeto Entrega con los IDs foráneos y la fecha/hora.
     * @return true si se guardó exitosamente, false en caso contrario.
     */
    public boolean create(Entrega entrega) {
        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setDate(3, entrega.getFecha());
            ps.setTime(4, entrega.getHora());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar la entrega: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lee todas las entregas registradas en la base de datos.
     * @return Lista de objetos Entrega.
     */
    public List<Entrega> readAll() {
        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT id, id_pedido, id_repartidor, fecha, hora FROM entregas";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int idPedido = rs.getInt("id_pedido");
                int idRepartidor = rs.getInt("id_repartidor");
                Date fecha = rs.getDate("fecha");
                Time hora = rs.getTime("hora");

                lista.add(new Entrega(id, idPedido, idRepartidor, fecha, hora));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar entregas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza los datos de una entrega existente (por ejemplo, si se reasignó un repartidor).
     * @param entrega Objeto Entrega con los datos actualizados.
     * @return true si se actualizó exitosamente, false en caso contrario.
     */
    public boolean update(Entrega entrega) {
        String sql = "UPDATE entregas SET id_pedido = ?, id_repartidor = ?, fecha = ?, hora = ? WHERE id = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setDate(3, entrega.getFecha());
            ps.setTime(4, entrega.getHora());
            ps.setInt(5, entrega.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar la entrega: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un registro de entrega de la base de datos.
     * @param id Identificador de la entrega a eliminar.
     * @return true si se eliminó exitosamente, false en caso contrario.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM entregas WHERE id = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar la entrega: " + e.getMessage());
            return false;
        }
    }
}