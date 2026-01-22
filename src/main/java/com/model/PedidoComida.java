package com.model;

/**
 * Representa un pedido asociado a entrega de comida.
 * <p>
 * Este tipo de pedido considera una preparación previa y condiciones especiales
 * de transporte, lo que se refleja en su regla específica de cálculo de tiempo
 * estimado de entrega.
 * </p>
 */
public class PedidoComida extends Pedido {

    /**
     * Crea un pedido de comida.
     *
     * @param idPedido         identificador del pedido (debe ser mayor que 0).
     * @param direccionEntrega dirección de entrega (no nula ni vacía).
     * @param distanciaKm      distancia estimada del reparto en kilómetros.
     */
    public PedidoComida(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm, TipoPedido.COMIDA);
    }

    /**
     * Calcula el tiempo estimado de entrega para un pedido de comida.
     * <p>
     * Regla aplicada: 15 minutos base por preparación del pedido
     * más 2 minutos adicionales por cada kilómetro de distancia.
     * </p>
     *
     * @return tiempo estimado de entrega en minutos.
     */
    @Override
    public int calcularTiempoEntrega() {
        return 15 + (int) (2 * getDistanciaKm());
    }
}
