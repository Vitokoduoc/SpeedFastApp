package com.model;

import java.util.Objects;

/**
 * Representa un pedido genérico dentro del sistema SpeedFast.
 * <p>
 * Esta clase define la información común a todos los pedidos y establece un contrato
 * para que cada tipo concreto implemente su propio cálculo de tiempo estimado.
 * </p>
 *
 * <h2>Responsabilidades</h2>
 * <ul>
 *   <li>Centralizar los atributos base del pedido: id, dirección y distancia.</li>
 *   <li>Entregar una salida estándar mediante {@link #mostrarResumen()}.</li>
 *   <li>Forzar a las subclases a implementar {@link #calcularTiempoEntrega()}.</li>
 * </ul>
 *
 * <p>
 * Punto de extensión: futuros comportamientos comunes relacionados con la gestión del pedido
 * pueden incorporarse aquí manteniendo el diseño modular y reutilizable.
 * </p>
 */
public abstract class Pedido {

    /**
     * Tipos de pedido soportados por el sistema.
     */
    public enum TipoPedido {
        COMIDA,
        ENCOMIENDA,
        EXPRESS
    }

    private final int idPedido;
    private final String direccionEntrega;
    private final double distanciaKm;
    private final TipoPedido tipoPedido;

    /**
     * Construye un pedido validando los datos de entrada para asegurar un estado consistente.
     *
     * @param idPedido         identificador único del pedido (debe ser mayor que 0).
     * @param direccionEntrega dirección de entrega (no nula ni vacía).
     * @param distanciaKm      distancia estimada en kilómetros (no negativa).
     * @param tipoPedido       tipo de pedido (no nulo).
     * @throws IllegalArgumentException si alguno de los parámetros no es válido.
     */
    public Pedido(int idPedido, String direccionEntrega, double distanciaKm, TipoPedido tipoPedido) {
        if (idPedido <= 0) {
            throw new IllegalArgumentException("idPedido debe ser mayor que 0.");
        }
        if (direccionEntrega == null || direccionEntrega.trim().isEmpty()) {
            throw new IllegalArgumentException("direccionEntrega no puede ser nula o vacía.");
        }
        if (distanciaKm < 0) {
            throw new IllegalArgumentException("distanciaKm no puede ser negativa.");
        }
        if (tipoPedido == null) {
            throw new IllegalArgumentException("tipoPedido no puede ser nulo.");
        }

        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega.trim();
        this.distanciaKm = distanciaKm;
        this.tipoPedido = tipoPedido;
    }

    /**
     * Imprime un resumen básico del pedido por consola.
     * <p>
     * Mantiene un formato consistente para facilitar lectura y comparación entre pedidos.
     * </p>
     */
    public void mostrarResumen() {
        System.out.println("Pedido #" + String.format("%03d", idPedido));
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + formatearKm(distanciaKm) + " km");
    }

    /**
     * Calcula el tiempo estimado de entrega en minutos.
     * <p>
     * La lógica es específica del tipo de pedido y debe ser implementada por las subclases.
     * </p>
     *
     * @return tiempo estimado de entrega en minutos.
     */
    public abstract int calcularTiempoEntrega();

    /** @return identificador del pedido. */
    public int getIdPedido() {
        return idPedido;
    }

    /** @return dirección de entrega del pedido. */
    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    /** @return distancia estimada en kilómetros. */
    public double getDistanciaKm() {
        return distanciaKm;
    }

    /** @return tipo de pedido. */
    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    /**
     * Formatea la distancia para una salida más limpia.
     * Si la distancia es un entero exacto, se imprime sin decimales.
     *
     * @param km distancia en kilómetros.
     * @return representación textual de la distancia.
     */
    protected String formatearKm(double km) {
        if (km == (int) km) return String.valueOf((int) km);
        return String.valueOf(km);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", direccionEntrega='" + direccionEntrega + '\'' +
                ", distanciaKm=" + distanciaKm +
                ", tipoPedido=" + tipoPedido +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        Pedido pedido = (Pedido) o;
        return idPedido == pedido.idPedido;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido);
    }
}
