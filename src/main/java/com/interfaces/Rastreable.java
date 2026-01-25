package com.interfaces;

/**
 * Interfaz que define la capacidad de rastrear información histórica.
 * <p>
 * Permite desacoplar la gestión del historial de entregas del resto
 * del sistema, facilitando su reutilización en distintos contextos.
 * </p>
 */
public interface Rastreable {

    /**
     * Muestra el historial de registros asociados.
     * <p>
     * La implementación concreta define el formato y origen de los datos mostrados.
     * </p>
     */
    void verHistorial();
}
