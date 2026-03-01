package com.ui;

import com.dao.PedidoDAO;
import com.model.EstadoPedido;
import com.model.Pedido;
import com.model.PedidoComida;
import com.model.PedidoCompraXpress;
import com.model.PedidoEncomienda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaPedidos extends JFrame {

    private PedidoDAO pedidoDAO;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    private JTextField txtId;
    private JTextField txtDireccion;
    private JComboBox<String> cbTipo;
    private JComboBox<String> cbEstado;

    public VentanaPedidos() {
        pedidoDAO = new PedidoDAO();

        setTitle("Gestión de Pedidos - SpeedFast");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarComponentes();
        cargarDatosTabla();
    }

    private void inicializarComponentes() {
        // --- PANEL NORTE (Formulario) ---
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Pedido"));

        panelFormulario.add(new JLabel("ID (Solo Lectura):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Dirección de Entrega:"));
        txtDireccion = new JTextField();
        panelFormulario.add(txtDireccion);

        panelFormulario.add(new JLabel("Tipo de Pedido:"));
        cbTipo = new JComboBox<>(new String[]{"COMIDA", "ENCOMIENDA", "EXPRESS"});
        panelFormulario.add(cbTipo);

        panelFormulario.add(new JLabel("Estado:"));
        cbEstado = new JComboBox<>(new String[]{"PENDIENTE", "EN_REPARTO", "ENTREGADO"});
        panelFormulario.add(cbEstado);

        add(panelFormulario, BorderLayout.NORTH);

        // --- PANEL CENTRO (Tabla) ---
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Dirección", "Tipo", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaPedidos = new JTable(modeloTabla);

        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaPedidos.getSelectedRow() != -1) {
                int fila = tablaPedidos.getSelectedRow();
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtDireccion.setText(modeloTabla.getValueAt(fila, 1).toString());
                cbTipo.setSelectedItem(modeloTabla.getValueAt(fila, 2).toString());
                cbEstado.setSelectedItem(modeloTabla.getValueAt(fila, 3).toString());
            }
        });

        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        // --- PANEL SUR (Botones) ---
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnGuardar.addActionListener(e -> registrarPedido());
        btnActualizar.addActionListener(e -> actualizarPedido());
        btnEliminar.addActionListener(e -> eliminarPedido());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        List<Pedido> pedidos = pedidoDAO.readAll();
        for (Pedido p : pedidos) {
            modeloTabla.addRow(new Object[]{p.getIdPedido(), p.getDireccionEntrega(), p.getTipoPedido().name(), p.getEstado().name()});
        }
    }

    private void registrarPedido() {
        String direccion = txtDireccion.getText().trim();
        String tipo = cbTipo.getSelectedItem().toString();

        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La dirección es obligatoria.");
            return;
        }

        Pedido nuevoPedido = null;
        switch (tipo) {
            case "COMIDA": nuevoPedido = new PedidoComida(1, direccion, 0.0); break;
            case "ENCOMIENDA": nuevoPedido = new PedidoEncomienda(1, direccion, 0.0); break;
            case "EXPRESS": nuevoPedido = new PedidoCompraXpress(1, direccion, 0.0); break;
        }

        nuevoPedido.setEstado(EstadoPedido.valueOf(cbEstado.getSelectedItem().toString()));

        if (pedidoDAO.create(nuevoPedido)) {
            JOptionPane.showMessageDialog(this, "Pedido registrado exitosamente.");
            limpiarFormulario();
            cargarDatosTabla();
        }
    }

    private void actualizarPedido() {
        if (txtId.getText().isEmpty()) return;

        int id = Integer.parseInt(txtId.getText());
        String direccion = txtDireccion.getText().trim();
        String tipo = cbTipo.getSelectedItem().toString();

        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La dirección es obligatoria.");
            return;
        }

        Pedido pedidoActualizado = null;
        switch (tipo) {
            case "COMIDA": pedidoActualizado = new PedidoComida(id, direccion, 0.0); break;
            case "ENCOMIENDA": pedidoActualizado = new PedidoEncomienda(id, direccion, 0.0); break;
            case "EXPRESS": pedidoActualizado = new PedidoCompraXpress(id, direccion, 0.0); break;
        }

        pedidoActualizado.setEstado(EstadoPedido.valueOf(cbEstado.getSelectedItem().toString()));

        if (pedidoDAO.update(pedidoActualizado)) {
            JOptionPane.showMessageDialog(this, "Pedido actualizado.");
            limpiarFormulario();
            cargarDatosTabla();
        }
    }

    private void eliminarPedido() {
        if (txtId.getText().isEmpty()) return;

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar pedido?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            if (pedidoDAO.delete(id)) {
                JOptionPane.showMessageDialog(this, "Pedido eliminado.");
                limpiarFormulario();
                cargarDatosTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error. Puede estar asociado a una entrega.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtDireccion.setText("");
        cbTipo.setSelectedIndex(0);
        cbEstado.setSelectedIndex(0);
        tablaPedidos.clearSelection();
    }
}