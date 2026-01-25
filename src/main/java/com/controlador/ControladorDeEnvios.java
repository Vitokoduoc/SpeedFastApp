package com.controlador;

import com.interfaces.Rastreable;
import com.model.Pedido;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controlador de envíos del sistema SpeedFast.
 * <p>
 * Esta clase se encarga de gestionar el historial de pedidos procesados,
 * desacoplando esta responsabilidad de las clases de dominio.
 * </p>
 *
 * <h2>Responsabilidades</h2>
 * <ul>
 *   <li>Registrar pedidos gestionados en un historial interno.</li>
 *   <li>Visualizar el historial de envíos por consola.</li>
 * </ul>
 */
public class ControladorDeEnvios implements Rastreable {

    /** Historial de pedidos registrados. */
    private final List<Pedido> historial;

    /**
     * Construye un controlador con historial vacío.
     */
    public ControladorDeEnvios() {
        this.historial = new ArrayList<>();
    }

    /**
     * Registra un pedido en el historial del sistema.
     *
     * @param pedido pedido a registrar (no nulo).
     * @throws IllegalArgumentException si el pedido es nulo.
     */
    public void registrarEntrega(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
        historial.add(pedido);
    }

    /**
     * Retorna una vista inmodificable del historial.
     *
     * @return lista de pedidos registrados (solo lectura).
     */
    public List<Pedido> getHistorial() {
        return Collections.unmodifiableList(historial);
    }

    /**
     * Muestra por consola el historial de pedidos gestionados.
     * <p>
     * Implementación del contrato {@link Rastreable}.
     * El formato de salida es claro y consistente con la pauta de evaluación.
     * </p>
     */
    @Override
    public void verHistorial() {
        System.out.println("\nHistorial:");
        if (historial.isEmpty()) {
            System.out.println("- (sin registros)");
            return;
        }

        for (Pedido p : historial) {
            String tipo = p.getClass().getSimpleName();
            String id = String.format("%03d", p.getIdPedido());
            String repartidor = (p.getRepartidor() == null)
                    ? "Sin asignar"
                    : p.getRepartidor();

            System.out.println("- " + tipo + " #" + id + " – entregado por " + repartidor);
        }
    }
}
