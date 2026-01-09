package com.model;

/**
 * Pedido de comida.
 * <p>
 * Regla del caso: requiere repartidor con mochila térmica.
 */
public class PedidoComida extends Pedido {

    /**
     * Crea un pedido de comida.
     *
     * @param idPedido         identificador (debe ser &gt; 0).
     * @param direccionEntrega dirección de entrega (no nula ni vacía).
     */
    public PedidoComida(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, TipoPedido.COMIDA);
    }

    @Override
    public void asignarRepartidor() {
        imprimirEncabezado("Pedido Comida");
        System.out.println("→ Verificando mochila térmica... OK");
    }

    /**
     * Sobrescritura de la versión con nombre para incluir validaciones del tipo comida.
     *
     * @param nombreRepartidor nombre del repartidor (no nulo ni vacío).
     */
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        validarNombre(nombreRepartidor);
        asignarRepartidor(); // reutiliza lógica específica (mochila térmica)
        System.out.println("→ Pedido asignado a " + nombreRepartidor.trim());
    }
}
