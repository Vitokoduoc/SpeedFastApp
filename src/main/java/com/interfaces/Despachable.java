package com.interfaces;

/**
 * Interfaz que define el comportamiento de despacho de un pedido.
 * <p>
 * Permite desacoplar la lógica de envío del resto del sistema,
 * facilitando la reutilización y mantenibilidad.
 * </p>
 */
public interface Despachable {

    /**
     * Ejecuta el proceso de despacho del pedido.
     * <p>
     * La implementación concreta define las validaciones y acciones necesarias
     * para completar el despacho.
     * </p>
     */
    void despachar();
}
