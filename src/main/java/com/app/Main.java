package com.app;

import com.model.Pedido;
import com.model.PedidoComida;
import com.model.PedidoCompraXpress;
import com.model.PedidoEncomienda;

/**
 * Punto de entrada del sistema SpeedFast.
 * <p>
 * Esta clase ejecuta una simulación simple creando distintos tipos de pedidos y
 * mostrando por consola:
 * </p>
 * <ul>
 *   <li>Resumen del pedido (datos base).</li>
 *   <li>Tiempo estimado de entrega según el tipo de pedido.</li>
 * </ul>
 *
 * <p>
 * El objetivo es validar el comportamiento del sistema a través de herencia y
 * polimorfismo mediante la llamada a métodos definidos en la clase base.
 * </p>
 */
public class Main {

    public static void main(String[] args) {

        PedidoComida comida = new PedidoComida(1, "Av. Central 123", 4);
        PedidoEncomienda encomienda = new PedidoEncomienda(2, "Calle Norte 456", 6);
        PedidoCompraXpress xpress = new PedidoCompraXpress(3, "Pasaje Sur 789", 7);

        // Polimorfismo: referencias de tipo Pedido ejecutan comportamiento concreto de cada subclase
        Pedido[] pedidos = { comida, encomienda, xpress };

        System.out.println("=== Simulación de pedidos ===\n");

        for (Pedido p : pedidos) {
            System.out.println("[" + p.getClass().getSimpleName() + "]");
            p.mostrarResumen();
            System.out.println("Tiempo estimado de entrega: " + p.calcularTiempoEntrega() + " minutos");
            System.out.println();
        }
    }
}
