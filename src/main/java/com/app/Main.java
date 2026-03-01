package com.app;

import com.ui.VentanaPrincipal;
import javax.swing.SwingUtilities;

/**
 * Punto de entrada del sistema SpeedFast (Semana 8).
 * Inicia la interfaz gráfica de usuario conectada a la base de datos.
 */
public class Main {

    public static void main(String[] args) {
        // Ejecutamos la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });

        /* * Nota: La simulación por consola de las semanas 3 y 4 ha sido
         * reemplazada por la interfaz gráfica (CRUD completo) de la Semana 8.
         */
    }
}