package com.model;

/**
 * Pedido de encomienda.
 * <p>
 * Regla del caso: requiere validación de peso y embalaje.
 */
public class PedidoEncomienda extends Pedido {

    /**
     * Crea un pedido de encomienda.
     *
     * @param idPedido         identificador (debe ser &gt; 0).
     * @param direccionEntrega dirección de entrega (no nula ni vacía).
     */
    public PedidoEncomienda(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, TipoPedido.ENCOMIENDA);
    }

    @Override
    public void asignarRepartidor() {
        imprimirEncabezado("Pedido Encomienda");
        System.out.println("→ Validando peso y embalaje... OK");
    }

    /**
     * Sobrescritura de la versión con nombre para incluir validaciones del tipo encomienda.
     *
     * @param nombreRepartidor nombre del repartidor (no nulo ni vacío).
     */
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        validarNombre(nombreRepartidor);
        asignarRepartidor(); // reutiliza lógica específica (peso/embalaje)
        System.out.println("→ Pedido asignado a " + nombreRepartidor.trim());
    }
}

