package com.ui;

import com.dao.EntregaDAO;
import com.dao.PedidoDAO;
import com.dao.RepartidorDAO;
import com.model.Entrega;
import com.model.Pedido;
import com.model.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class VentanaEntregas extends JFrame {

    private EntregaDAO entregaDAO;
    private PedidoDAO pedidoDAO;
    private RepartidorDAO repartidorDAO;

    private JTable tablaEntregas;
    private DefaultTableModel modeloTabla;

    private JTextField txtId;
    private JComboBox<ComboItemPedido> cbPedidos;
    private JComboBox<Repartidor> cbRepartidores;

    public VentanaEntregas() {
        entregaDAO = new EntregaDAO();
        pedidoDAO = new PedidoDAO();
        repartidorDAO = new RepartidorDAO();

        setTitle("Gestión de Entregas - SpeedFast");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarComponentes();
        cargarCombos();
        cargarDatosTabla();
    }

    private void inicializarComponentes() {
        // --- PANEL NORTE (Formulario) ---
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Asignar Entrega"));

        panelFormulario.add(new JLabel("ID Entrega (Solo Lectura):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Seleccione Pedido:"));
        cbPedidos = new JComboBox<>();
        panelFormulario.add(cbPedidos);

        panelFormulario.add(new JLabel("Seleccione Repartidor:"));
        cbRepartidores = new JComboBox<>();
        panelFormulario.add(cbRepartidores);

        add(panelFormulario, BorderLayout.NORTH);

        // --- PANEL CENTRO (Tabla) ---
        modeloTabla = new DefaultTableModel(new String[]{"ID Entrega", "ID Pedido", "ID Repartidor", "Fecha", "Hora"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaEntregas = new JTable(modeloTabla);

        tablaEntregas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaEntregas.getSelectedRow() != -1) {
                int fila = tablaEntregas.getSelectedRow();
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                // Por simplicidad, en este módulo solo permitiremos crear y eliminar,
                // ya que actualizar combos desde la tabla requiere lógica extra de matching.
            }
        });

        add(new JScrollPane(tablaEntregas), BorderLayout.CENTER);

        // --- PANEL SUR (Botones) ---
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Registrar Entrega");
        JButton btnEliminar = new JButton("Eliminar Entrega");
        JButton btnRefrescar = new JButton("Refrescar Listas"); // Refresca los JComboBox

        btnGuardar.addActionListener(e -> registrarEntrega());
        btnEliminar.addActionListener(e -> eliminarEntrega());
        btnRefrescar.addActionListener(e -> { cargarCombos(); cargarDatosTabla(); });

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    // Carga los datos de la base de datos a los JComboBox
    private void cargarCombos() {
        cbPedidos.removeAllItems();
        cbRepartidores.removeAllItems();

        List<Pedido> pedidos = pedidoDAO.readAll();
        for (Pedido p : pedidos) {
            if (p.getEstado().name().equals("PENDIENTE")) { // Solo mostramos pedidos pendientes
                cbPedidos.addItem(new ComboItemPedido(p));
            }
        }

        List<Repartidor> repartidores = repartidorDAO.readAll();
        for (Repartidor r : repartidores) {
            cbRepartidores.addItem(r); // Repartidor ya tiene el toString() formateado
        }
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        List<Entrega> entregas = entregaDAO.readAll();
        for (Entrega e : entregas) {
            modeloTabla.addRow(new Object[]{e.getId(), e.getIdPedido(), e.getIdRepartidor(), e.getFecha(), e.getHora()});
        }
    }

    private void registrarEntrega() {
        if (cbPedidos.getSelectedItem() == null || cbRepartidores.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido y un repartidor.");
            return;
        }

        ComboItemPedido pedidoSeleccionado = (ComboItemPedido) cbPedidos.getSelectedItem();
        Repartidor repartidorSeleccionado = (Repartidor) cbRepartidores.getSelectedItem();

        long timeNow = System.currentTimeMillis();
        Date fechaActual = new Date(timeNow);
        Time horaActual = new Time(timeNow);

        Entrega nuevaEntrega = new Entrega(0, pedidoSeleccionado.getId(), repartidorSeleccionado.getId(), fechaActual, horaActual);

        if (entregaDAO.create(nuevaEntrega)) {
            // Opcional: Actualizar el estado del pedido a EN_REPARTO
            Pedido p = pedidoSeleccionado.getPedido();
            p.setEstado(com.model.EstadoPedido.EN_REPARTO);
            pedidoDAO.update(p);

            JOptionPane.showMessageDialog(this, "Entrega registrada exitosamente.");
            txtId.setText("");
            cargarCombos(); // Recarga para quitar el pedido asignado
            cargarDatosTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar la entrega.");
        }
    }

    private void eliminarEntrega() {
        if (txtId.getText().isEmpty()) return;

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar entrega?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            if (entregaDAO.delete(id)) {
                JOptionPane.showMessageDialog(this, "Entrega eliminada.");
                txtId.setText("");
                cargarCombos();
                cargarDatosTabla();
            }
        }
    }

    /**
     * Clase interna (Wrapper) para dar formato al texto del Pedido en el JComboBox.
     */
    private class ComboItemPedido {
        private Pedido pedido;

        public ComboItemPedido(Pedido pedido) {
            this.pedido = pedido;
        }

        public int getId() {
            return pedido.getIdPedido();
        }

        public Pedido getPedido() {
            return pedido;
        }

        @Override
        public String toString() {
            return pedido.getIdPedido() + " - " + pedido.getDireccionEntrega() + " (" + pedido.getTipoPedido() + ")";
        }
    }
}