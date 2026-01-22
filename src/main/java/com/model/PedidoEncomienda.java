package com.model;

/**
 * Representa un pedido asociado a entrega de encomiendas.
 * <p>
 * Este tipo de pedido considera procesos adicionales (por ejemplo, validación de embalaje)
 * que se reflejan en una regla propia para estimar el tiempo de entrega.
 * </p>
 */
public class PedidoEncomienda extends Pedido {

    /**
     * Crea un pedido de encomienda.
     *
     * @param idPedido         identificador del pedido (debe ser mayor que 0).
     * @param direccionEntrega dirección de entrega (no nula ni vacía).
     * @param distanciaKm      distancia estimada del reparto en kilómetros.
     */
    public PedidoEncomienda(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm, TipoPedido.ENCOMIENDA);
    }

    /**
     * Calcula el tiempo estimado de entrega para un pedido de encomienda.
     * <p>
     * Regla aplicada: 20 minutos base + 1.5 minutos por cada kilómetro.
     * El resultado se ajusta a entero para entregar un valor final en minutos.
     * </p>
     *
     * @return tiempo estimado de entrega en minutos.
     */
    @Override
    public int calcularTiempoEntrega() {
        return (int) (20 + (1.5 * getDistanciaKm()));
    }
}
