package com.model;

/**
 * Pedido de compra express.
 * <p>
 * Prioriza rapidez en la entrega mediante un tiempo base reducido,
 * agregando un recargo fijo cuando la distancia supera un umbral definido.
 * </p>
 */
public class PedidoCompraXpress extends Pedido {

    /**
     * Crea un pedido de compra express.
     *
     * @param idPedido         identificador del pedido (debe ser mayor que 0).
     * @param direccionEntrega dirección de entrega (no nula ni vacía).
     * @param distanciaKm      distancia estimada del reparto en kilómetros (no negativa).
     */
    public PedidoCompraXpress(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm, TipoPedido.EXPRESS);
    }

    /**
     * Calcula el tiempo estimado de entrega.
     * Regla aplicada: 10 minutos base. Si la distancia es mayor a 5 km, se agregan 5 minutos.
     *
     * @return tiempo estimado de entrega en minutos.
     */
    @Override
    public int calcularTiempoEntrega() {
        int tiempo = 10;
        if (getDistanciaKm() > 5) {
            tiempo += 5;
        }
        return tiempo;
    }
}
