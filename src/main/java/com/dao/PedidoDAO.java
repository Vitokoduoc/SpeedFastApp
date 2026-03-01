package com.dao;

import com.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Data Access Object (DAO) para la entidad Pedido.
 * Gestiona las operaciones CRUD, el mapeo de Enums y la instanciación polimórfica.
 */
public class PedidoDAO {

    /**
     * Crea un nuevo pedido en la base de datos.
     * @param pedido Objeto Pedido (puede ser Comida, Encomienda o Express).
     * @return true si se guardó exitosamente, false en caso contrario.
     */
    public boolean create(Pedido pedido) {
        // La tabla tiene id (auto_increment), direccion, tipo y estado
        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pedido.getDireccionEntrega());
            // Usamos .name() para obtener el String exacto del Enum ("COMIDA", "PENDIENTE", etc.)
            ps.setString(2, pedido.getTipoPedido().name());
            ps.setString(3, pedido.getEstado().name());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar el pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lee todos los pedidos de la base de datos y reconstruye los objetos según su tipo.
     * @return Lista de objetos Pedido polimórficos.
     */
    public List<Pedido> readAll() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT id, direccion, tipo, estado FROM pedidos";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String direccion = rs.getString("direccion");
                String tipo = rs.getString("tipo");
                String estadoStr = rs.getString("estado");

                Pedido pedido = null;
                // La BD no guarda distancia, usamos 0.0 por defecto para cumplir con el constructor
                double distanciaPorDefecto = 0.0;

                // Instanciamos la clase hija correspondiente según el tipo en la BD
                switch (tipo) {
                    case "COMIDA":
                        pedido = new PedidoComida(id, direccion, distanciaPorDefecto);
                        break;
                    case "ENCOMIENDA":
                        pedido = new PedidoEncomienda(id, direccion, distanciaPorDefecto);
                        break;
                    case "EXPRESS":
                        pedido = new PedidoCompraXpress(id, direccion, distanciaPorDefecto);
                        break;
                }

                if (pedido != null) {
                    // Reconstruimos el estado usando el Enum
                    pedido.setEstado(EstadoPedido.valueOf(estadoStr));
                    lista.add(pedido);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al listar pedidos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza la dirección, el tipo y el estado de un pedido existente.
     * @param pedido Objeto Pedido con los datos actualizados.
     * @return true si se actualizó exitosamente, false en caso contrario.
     */
    public boolean update(Pedido pedido) {
        String sql = "UPDATE pedidos SET direccion = ?, tipo = ?, estado = ? WHERE id = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pedido.getDireccionEntrega());
            ps.setString(2, pedido.getTipoPedido().name());
            ps.setString(3, pedido.getEstado().name());
            ps.setInt(4, pedido.getIdPedido());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar el pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un pedido de la base de datos según su ID.
     * @param id Identificador del pedido a eliminar.
     * @return true si se eliminó exitosamente, false en caso contrario.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el pedido: " + e.getMessage());
            return false;
        }
    }
}