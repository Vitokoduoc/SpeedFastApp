package com.ui;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("SpeedFast - Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnGestionarPedidos = new JButton("1. Gestionar Pedidos");
        JButton btnGestionarRepartidores = new JButton("2. Gestionar Repartidores");
        JButton btnGestionarEntregas = new JButton("3. Asignar Entregas");

        Font fuente = new Font("Arial", Font.BOLD, 14);
        btnGestionarPedidos.setFont(fuente);
        btnGestionarRepartidores.setFont(fuente);
        btnGestionarEntregas.setFont(fuente);

        // Eventos para abrir las otras ventanas
        btnGestionarRepartidores.addActionListener(e -> new VentanaRepartidores().setVisible(true));
        btnGestionarPedidos.addActionListener(e -> new VentanaPedidos().setVisible(true));
        btnGestionarRepartidores.addActionListener(e -> new VentanaRepartidores().setVisible(true));
        btnGestionarEntregas.addActionListener(e -> new VentanaEntregas().setVisible(true));

        // Dejamos estos con un mensaje temporal mientras te paso el código en el siguiente paso
        btnGestionarPedidos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Módulo de Pedidos listo para integrar..."));
        btnGestionarEntregas.addActionListener(e -> JOptionPane.showMessageDialog(this, "Módulo de Entregas listo para integrar..."));

        add(new JLabel(" Seleccione un módulo:", SwingConstants.CENTER));
        add(btnGestionarPedidos);
        add(btnGestionarRepartidores);
        add(btnGestionarEntregas);
    }
}