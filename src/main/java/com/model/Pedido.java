package com.model;

import java.util.Objects;

/**
 * Representa un pedido genérico de SpeedFast.
 * <p>
 * Superclase de pedidos especializados, diseñada para aplicar polimorfismo:
 * <ul>
 *   <li><b>Sobrescritura</b>: las subclases personalizan {@link #asignarRepartidor()}.</li>
 *   <li><b>Sobrecarga</b>: el mismo comportamiento se ofrece con distintas firmas
 *       mediante {@link #asignarRepartidor()} y {@link #asignarRepartidor(String)}.</li>
 * </ul>
 */
public class Pedido {

    /**
     * Tipos soportados por SpeedFast para evitar strings "mágicos".
     */
    public enum TipoPedido {
        COMIDA,
        ENCOMIENDA,
        COMPRA_XPRESS
    }

    private final int idPedido;
    private final String direccionEntrega;
    private final TipoPedido tipoPedido;

    /**
     * Crea un pedido genérico.
     *
     * @param idPedido         identificador del pedido (debe ser &gt; 0).
     * @param direccionEntrega dirección de entrega (no nula ni vacía).
     * @param tipoPedido       tipo de pedido (no nulo).
     * @throws IllegalArgumentException si algún argumento no cumple lo requerido.
     */
    public Pedido(int idPedido, String direccionEntrega, TipoPedido tipoPedido) {
        if (idPedido <= 0) {
            throw new IllegalArgumentException("idPedido debe ser mayor que 0.");
        }
        if (direccionEntrega == null || direccionEntrega.trim().isEmpty()) {
            throw new IllegalArgumentException("direccionEntrega no puede ser nula o vacía.");
        }
        if (tipoPedido == null) {
            throw new IllegalArgumentException("tipoPedido no puede ser nulo.");
        }

        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega.trim();
        this.tipoPedido = tipoPedido;
    }

    /**
     * Asigna un repartidor al pedido (comportamiento genérico).
     * <p>
     * Las subclases sobrescriben este método para personalizar la lógica
     * según el tipo de pedido.
     */
    public void asignarRepartidor() {
        System.out.println("Asignando repartidor al pedido...");
    }

    /**
     * Sobrecarga: asigna un repartidor indicando su nombre (comportamiento genérico).
     * <p>
     * Las subclases pueden sobrescribir este método para aplicar validaciones
     * propias del tipo de pedido.
     *
     * @param nombreRepartidor nombre del repartidor (no nulo ni vacío).
     * @throws IllegalArgumentException si el nombre es nulo o vacío.
     */
    public void asignarRepartidor(String nombreRepartidor) {
        validarNombre(nombreRepartidor);
        asignarRepartidor();
        System.out.println("→ Pedido asignado a " + nombreRepartidor.trim());
    }

    /**
     * Imprime un encabezado estándar por consola para mantener salida consistente.
     *
     * @param titulo etiqueta del tipo de pedido, por ejemplo: {@code "Pedido Comida"}.
     */
    protected void imprimirEncabezado(String titulo) {
        System.out.println("[" + titulo + "]");
        System.out.println("Asignando repartidor...");
    }

    /** @return ID del pedido. */
    public int getIdPedido() {
        return idPedido;
    }

    /** @return dirección de entrega. */
    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    /** @return tipo de pedido. */
    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    /**
     * Valida el nombre del repartidor.
     *
     * @param nombreRepartidor nombre del repartidor.
     * @throws IllegalArgumentException si el nombre es nulo o vacío.
     */
    protected void validarNombre(String nombreRepartidor) {
        if (nombreRepartidor == null || nombreRepartidor.trim().isEmpty()) {
            throw new IllegalArgumentException("nombreRepartidor no puede ser nulo o vacío.");
        }
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", direccionEntrega='" + direccionEntrega + '\'' +
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
