package com.ui;

import com.dao.RepartidorDAO;
import com.model.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaRepartidores extends JFrame {

    private RepartidorDAO repartidorDAO;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtId;
    private JTextField txtNombre;

    public VentanaRepartidores() {
        repartidorDAO = new RepartidorDAO();

        setTitle("Gestión de Repartidores");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {
        // Formulario Superior
        JPanel panelNorte = new JPanel(new GridLayout(2, 2, 5, 5));
        panelNorte.setBorder(BorderFactory.createTitledBorder("Datos del Repartidor"));

        panelNorte.add(new JLabel("ID (Solo lectura):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelNorte.add(txtId);

        panelNorte.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelNorte.add(txtNombre);

        add(panelNorte, BorderLayout.NORTH);

        // Tabla Central
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                txtId.setText(modeloTabla.getValueAt(tabla.getSelectedRow(), 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(tabla.getSelectedRow(), 1).toString());
            }
        });
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botones Inferiores
        JPanel panelSur = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnGuardar.addActionListener(e -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());

        panelSur.add(btnGuardar);
        panelSur.add(btnActualizar);
        panelSur.add(btnEliminar);
        panelSur.add(btnLimpiar);
        add(panelSur, BorderLayout.SOUTH);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Repartidor> lista = repartidorDAO.readAll();
        for (Repartidor r : lista) {
            modeloTabla.addRow(new Object[]{r.getId(), r.getNombre()});
        }
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.");
            return;
        }
        if (repartidorDAO.create(new Repartidor(0, nombre))) {
            JOptionPane.showMessageDialog(this, "Guardado exitoso.");
            limpiar();
            cargarDatos();
        }
    }

    private void actualizar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un repartidor de la tabla.");
            return;
        }
        int id = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText().trim();

        if (repartidorDAO.update(new Repartidor(id, nombre))) {
            JOptionPane.showMessageDialog(this, "Actualizado exitosamente.");
            limpiar();
            cargarDatos();
        }
    }

    private void eliminar() {
        if (txtId.getText().isEmpty()) return;
        int id = Integer.parseInt(txtId.getText());
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar repartidor?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION && repartidorDAO.delete(id)) {
            JOptionPane.showMessageDialog(this, "Eliminado.");
            limpiar();
            cargarDatos();
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        tabla.clearSelection();
    }
}