package com.interfaces;

/**
 * Interfaz que define la capacidad de cancelar una operación o pedido.
 * <p>
 * Permite desacoplar la lógica de cancelación del resto del sistema,
 * favoreciendo un diseño flexible y extensible.
 * </p>
 */
public interface Cancelable {

    /**
     * Cancela la operación asociada.
     * <p>
     * La implementación concreta debe definir cómo se gestiona el estado
     * de cancelación y sus efectos.
     * </p>
     */
    void cancelar();
}
