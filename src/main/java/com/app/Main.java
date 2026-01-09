package com.app;

import com.model.Pedido;
import com.model.PedidoComida;
import com.model.PedidoCompraXpress;
import com.model.PedidoEncomienda;

/**
 * Punto de entrada del sistema SpeedFast (Semana 1).
 * <p>
 * Demuestra:
 * <ul>
 *   <li>Polimorfismo por sobrescritura: referencias {@link Pedido} ejecutan comportamiento de subclases.</li>
 *   <li>Sobrecarga: uso de {@code asignarRepartidor()} y {@code asignarRepartidor(String)}.</li>
 * </ul>
 */
public class Main {

    public static void main(String[] args) {

        PedidoComida comida = new PedidoComida(1, "Av. Central 123");
        PedidoEncomienda encomienda = new PedidoEncomienda(2, "Calle Norte 456");
        PedidoCompraXpress xpress = new PedidoCompraXpress(3, "Pasaje Sur 789");

        // Polimorfismo (sobrescritura)
        Pedido[] pedidos = { comida, encomienda, xpress };

        System.out.println("=== Polimorfismo (Sobrescritura) ===\n");
        for (Pedido p : pedidos) {
            p.asignarRepartidor();
            System.out.println();
        }

        // Sobrecarga (misma acción, distinta firma)
        System.out.println("=== Sobrecarga (asignarRepartidor(String)) ===\n");

        comida.asignarRepartidor("Juan Pérez");
        System.out.println();

        encomienda.asignarRepartidor("Camila Soto");
        System.out.println();

        xpress.asignarRepartidor("Luis Díaz");
        System.out.println();
    }
}
