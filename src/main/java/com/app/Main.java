package com.app;

import com.controlador.ControladorDeEnvios;
import com.model.Pedido;
import com.model.PedidoComida;
import com.model.PedidoEncomienda;
import com.model.PedidoCompraXpress;

/**
 * Punto de entrada del sistema SpeedFast.
 * <p>
 * Esta clase ejecuta una simulación completa del sistema de entregas,
 * validando el uso de herencia, polimorfismo, abstracción e interfaces.
 * </p>
 *
 * <h2>Simulación</h2>
 * <ul>
 *   <li>Creación de distintos tipos de pedidos.</li>
 *   <li>Asignación automática y manual de repartidores.</li>
 *   <li>Cálculo de tiempo estimado.</li>
 *   <li>Despacho y cancelación de pedidos.</li>
 *   <li>Visualización del historial de envíos.</li>
 * </ul>
 */
public class Main {

    public static void main(String[] args) {

        // Controlador de historial
        ControladorDeEnvios controlador = new ControladorDeEnvios();

        // Creación de pedidos
        PedidoComida comida = new PedidoComida(101, "Av. Central 123", 4);
        PedidoEncomienda encomienda = new PedidoEncomienda(102, "Calle Norte 456", 6);
        PedidoCompraXpress express = new PedidoCompraXpress(103, "Pasaje Sur 789", 7);

        // Polimorfismo: referencias de tipo Pedido
        Pedido[] pedidos = { comida, encomienda, express };

        System.out.println("=== Simulación de pedidos SpeedFast ===\n");

        // Asignación automática + despacho
        for (Pedido p : pedidos) {
            System.out.println("[" + p.getClass().getSimpleName() + "]");
            p.asignarRepartidor();              // sobrescritura
            p.mostrarResumen();
            p.despachar();
            controlador.registrarEntrega(p);
            System.out.println();
        }

        // Asignación manual (sobrecarga)
        System.out.println("=== Asignación manual de repartidor ===\n");
        comida.asignarRepartidor("Pedro Morales");
        comida.mostrarResumen();
        System.out.println();

        // Cancelación de pedido
        System.out.println("Cancelando Pedido Express #103...");
        express.cancelar();
        express.despachar(); // prueba de validación
        System.out.println();

        // Visualización de historial
        controlador.verHistorial();
    }
}
