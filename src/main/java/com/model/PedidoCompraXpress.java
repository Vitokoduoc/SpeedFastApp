package com.model;

/**
 * Pedido de compra express.
 * <p>
 * Regla del caso: se asigna al repartidor más cercano con disponibilidad inmediata.
 */
public class PedidoCompraXpress extends Pedido {

    /**
     * Crea un pedido de compra express.
     *
     * @param idPedido         identificador (debe ser &gt; 0).
     * @param direccionEntrega dirección de entrega (no nula ni vacía).
     */
    public PedidoCompraXpress(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, TipoPedido.COMPRA_XPRESS);
    }

    @Override
    public void asignarRepartidor() {
        imprimirEncabezado("Pedido Compra Express");
        System.out.println("→ Repartidor más cercano con disponibilidad inmediata encontrado.");
    }

    /**
     * Sobrescritura de la versión con nombre para incluir validaciones del tipo compra express.
     *
     * @param nombreRepartidor nombre del repartidor (no nulo ni vacío).
     */
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        validarNombre(nombreRepartidor);
        asignarRepartidor(); // reutiliza lógica específica (cercanía/disponibilidad)
        System.out.println("→ Pedido asignado a " + nombreRepartidor.trim());
    }
}
