package com.model;

/**
 * Representa un pedido de compra express.
 * <p>
 * Este tipo de pedido prioriza rapidez en la entrega mediante un tiempo base reducido,
 * aplicando un recargo fijo cuando la distancia supera un umbral definido.
 * Además, posee una asignación automática de repartidor acorde a su alta prioridad.
 * </p>
 */
public class PedidoCompraXpress extends Pedido {

    /**
     * Crea un pedido de compra express.
     *
     * @param idPedido         identificador del pedido (debe ser mayor que 0).
     * @param direccionEntrega dirección de entrega (no nula ni vacía).
     * @param distanciaKm      distancia estimada del reparto en kilómetros (no negativa).
     * @throws IllegalArgumentException si algún parámetro no cumple validación.
     */
    public PedidoCompraXpress(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm, TipoPedido.EXPRESS);
    }

    /**
     * Asigna un repartidor automáticamente para pedidos express.
     * <p>
     * Regla sugerida: los pedidos express se asignan a repartidores de alta prioridad.
     * La validación del nombre del repartidor se centraliza en {@link #setRepartidor(String)}.
     * </p>
     */
    @Override
    public void asignarRepartidor() {
        setRepartidor("Carlos Vega");
    }

    /**
     * Calcula el tiempo estimado de entrega para un pedido express.
     * <p>
     * Regla aplicada: 10 minutos base.
     * Si la distancia es mayor a 5 km, se agregan 5 minutos adicionales.
     * </p>
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
