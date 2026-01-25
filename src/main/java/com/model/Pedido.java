package com.model;

import com.interfaces.Cancelable;
import com.interfaces.Despachable;

import java.util.Objects;

/**
 * Representa un pedido genérico dentro del sistema SpeedFast.
 * <p>
 * Esta clase define la información común a todos los pedidos y establece un contrato
 * para que cada tipo concreto implemente su propio cálculo de tiempo estimado
 * y su forma de asignación de repartidor.
 * </p>
 *
 * <h2>Responsabilidades</h2>
 * <ul>
 *   <li>Centralizar los atributos base del pedido: id, dirección, distancia y tipo.</li>
 *   <li>Entregar una salida estándar mediante {@link #mostrarResumen()}.</li>
 *   <li>Forzar a las subclases a implementar {@link #calcularTiempoEntrega()}.</li>
 *   <li>Permitir asignación de repartidor automática (polimorfismo) y manual (sobrecarga).</li>
 * </ul>
 *
 * <p>
 * Punto de extensión: futuros comportamientos comunes relacionados con la gestión del pedido
 * pueden incorporarse aquí manteniendo el diseño modular y reutilizable.
 * </p>
 */
public abstract class Pedido implements Despachable, Cancelable {

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

    /** Nombre del repartidor asignado. Puede ser null si aún no se asigna. */
    private String repartidor;

    /** Indica si el pedido fue cancelado. */
    private boolean cancelado;

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

        this.repartidor = null;
        this.cancelado = false;
    }

    /**
     * Imprime un resumen básico del pedido por consola.
     * <p>
     * Mantiene un formato consistente para facilitar lectura y comparación entre pedidos.
     * Incluye repartidor y tiempo estimado para apoyar la simulación en Main.
     * </p>
     */
    public void mostrarResumen() {
        System.out.println("Pedido #" + String.format("%03d", idPedido));
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + formatearKm(distanciaKm) + " km");
        System.out.println("Repartidor asignado: " + (repartidor == null ? "Sin asignar" : repartidor));
        System.out.println("Tiempo estimado: " + calcularTiempoEntrega() + " minutos");
    }

    /**
     * Asigna un repartidor automáticamente.
     * <p>
     * Este método debe ser sobrescrito por las subclases para aplicar la regla de negocio
     * correspondiente al tipo de pedido.
     * </p>
     */
    public abstract void asignarRepartidor();

    /**
     * Asigna un repartidor manualmente (sobrecarga).
     *
     * @param nombre nombre del repartidor (no nulo ni vacío).
     * @throws IllegalArgumentException si el nombre es nulo o vacío.
     */
    public void asignarRepartidor(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del repartidor no puede ser nulo o vacío.");
        }
        this.repartidor = nombre.trim();
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

    /**
     * Despacha el pedido si cumple las condiciones mínimas del flujo.
     * <p>
     * Valida que el pedido no esté cancelado y que tenga un repartidor asignado.
     * </p>
     */
    @Override
    public void despachar() {
        if (cancelado) {
            System.out.println("No se puede despachar: el pedido #" + String.format("%03d", idPedido) + " está cancelado.");
            return;
        }
        if (repartidor == null) {
            System.out.println("No se puede despachar: no hay repartidor asignado al pedido #" + String.format("%03d", idPedido) + ".");
            return;
        }
        System.out.println("Pedido despachado correctamente.");
    }

    /**
     * Cancela el pedido cambiando su estado.
     * <p>
     * Este método forma parte del contrato {@link Cancelable}.
     * </p>
     */
    @Override
    public void cancelar() {
        this.cancelado = true;
        System.out.println("→ Pedido cancelado exitosamente.");
    }

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

    /** @return repartidor asignado o null si no existe asignación. */
    public String getRepartidor() {
        return repartidor;
    }

    /** @return true si el pedido fue cancelado. */
    public boolean isCancelado() {
        return cancelado;
    }

    /**
     * Permite que las subclases asignen repartidor automático de forma controlada.
     *
     * @param repartidor nombre del repartidor (no nulo ni vacío).
     * @throws IllegalArgumentException si el nombre es nulo o vacío.
     */
    protected void setRepartidor(String repartidor) {
        if (repartidor == null || repartidor.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del repartidor no puede ser nulo o vacío.");
        }
        this.repartidor = repartidor.trim();
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
                ", repartidor='" + repartidor + '\'' +
                ", cancelado=" + cancelado +
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
