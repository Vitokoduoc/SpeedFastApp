package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Repartidor implements Runnable {

    private int id; // NUEVO: ID autoincremental de la base de datos
    private String nombre;
    private List<Pedido> pedidosAsignados;
    private final Random random = new Random();

    // NUEVO: Constructor para usar al listar repartidores desde la BD (no traemos los pedidos de inmediato)
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.pedidosAsignados = new ArrayList<>();
    }

    // Constructor original adaptado
    public Repartidor(int id, String nombre, List<Pedido> pedidosAsignados) {
        this.id = id;
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("El nombre no puede ser nulo.");
        this.nombre = nombre.trim();
        this.pedidosAsignados = Objects.requireNonNull(pedidosAsignados, "pedidosAsignados no puede ser null.");
    }

    // Getters y Setters para uso en DAO y JTable
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<Pedido> getPedidosAsignados() { return pedidosAsignados; }

    @Override
    public void run() {
        System.out.println("[Repartidor-Hilo: " + nombre + "] Iniciando ruta. Pedidos: " + pedidosAsignados.size());

        for (Pedido pedido : pedidosAsignados) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("[Repartidor-Hilo: " + nombre + "] Interrumpido. Finalizando hilo.");
                return;
            }

            if (pedido.isCancelado()) {
                continue;
            }

            if (pedido.getRepartidor() == null) {
                pedido.asignarRepartidor();
            }

            System.out.println("[Repartidor-Hilo: " + nombre + "] Entregando Pedido #" + String.format("%03d", pedido.getIdPedido())
                    + " (" + pedido.getTipoPedido() + ") -> " + pedido.getDireccionEntrega());

            pedido.despachar();

            int pausaMs = 800 + random.nextInt(1201);
            try {
                Thread.sleep(pausaMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            pedido.setEstado(EstadoPedido.ENTREGADO); // NUEVO: Marcamos como entregado al finalizar el sleep
            System.out.println("[Repartidor-Hilo: " + nombre + "] Pedido completado #" + String.format("%03d", pedido.getIdPedido())
                    + " | Estado actual: " + pedido.getEstado());
        }

        System.out.println("[Repartidor-Hilo: " + nombre + "] Ruta finalizada.");
    }

    // Sobrescribimos toString para que en el JComboBox se vea bonito
    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}