package com.model;

import com.interfaces.Cancelable;
import com.interfaces.Despachable;
import java.util.Objects;

public abstract class Pedido implements Despachable, Cancelable {

    public enum TipoPedido {
        COMIDA,
        ENCOMIENDA,
        EXPRESS
    }

    private int idPedido; // Se usa como ID en la BD
    private String direccionEntrega;
    private double distanciaKm;
    private TipoPedido tipoPedido;

    // NUEVO: Atributo exigido por la base de datos
    private EstadoPedido estado;

    private String repartidor;
    private boolean cancelado;

    public Pedido(int idPedido, String direccionEntrega, double distanciaKm, TipoPedido tipoPedido) {
        if (idPedido <= 0) throw new IllegalArgumentException("idPedido debe ser mayor que 0.");
        if (direccionEntrega == null || direccionEntrega.trim().isEmpty()) throw new IllegalArgumentException("direccionEntrega no puede ser nula o vacía.");
        if (distanciaKm < 0) throw new IllegalArgumentException("distanciaKm no puede ser negativa.");
        if (tipoPedido == null) throw new IllegalArgumentException("tipoPedido no puede ser nulo.");

        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega.trim();
        this.distanciaKm = distanciaKm;
        this.tipoPedido = tipoPedido;

        // Todo pedido nace como PENDIENTE para coincidir con la BD
        this.estado = EstadoPedido.PENDIENTE;
        this.repartidor = null;
        this.cancelado = false;
    }

    public void mostrarResumen() {
        System.out.println("Pedido #" + String.format("%03d", idPedido));
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + formatearKm(distanciaKm) + " km");
        System.out.println("Repartidor asignado: " + (repartidor == null ? "Sin asignar" : repartidor));
        System.out.println("Estado actual: " + estado);
        System.out.println("Tiempo estimado: " + calcularTiempoEntrega() + " minutos");
    }

    public abstract void asignarRepartidor();

    public void asignarRepartidor(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("El nombre no puede ser vacío.");
        this.repartidor = nombre.trim();
    }

    public abstract int calcularTiempoEntrega();

    @Override
    public void despachar() {
        if (cancelado) {
            System.out.println("No se puede despachar: el pedido #" + idPedido + " está cancelado.");
            return;
        }
        if (repartidor == null) {
            System.out.println("No se puede despachar: no hay repartidor asignado al pedido #" + idPedido + ".");
            return;
        }
        this.estado = EstadoPedido.EN_REPARTO; // Actualizamos estado
        System.out.println("Pedido despachado correctamente. Estado: " + this.estado);
    }

    @Override
    public void cancelar() {
        this.cancelado = true;
        System.out.println("→ Pedido cancelado exitosamente.");
    }

    // Getters
    public int getIdPedido() { return idPedido; }
    public String getDireccionEntrega() { return direccionEntrega; }
    public double getDistanciaKm() { return distanciaKm; }
    public TipoPedido getTipoPedido() { return tipoPedido; }
    public String getRepartidor() { return repartidor; }
    public boolean isCancelado() { return cancelado; }
    public EstadoPedido getEstado() { return estado; } // NUEVO GETTER

    // Setters necesarios para armar el objeto desde la BD (DAO)
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }

    protected void setRepartidor(String repartidor) {
        if (repartidor == null || repartidor.trim().isEmpty()) throw new IllegalArgumentException("El nombre no puede ser nulo.");
        this.repartidor = repartidor.trim();
    }

    protected String formatearKm(double km) {
        if (km == (int) km) return String.valueOf((int) km);
        return String.valueOf(km);
    }

    @Override
    public String toString() {
        return "Pedido{id=" + idPedido + ", direccion='" + direccionEntrega + "', tipo=" + tipoPedido + ", estado=" + estado + '}';
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