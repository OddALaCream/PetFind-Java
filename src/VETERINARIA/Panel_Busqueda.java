package VETERINARIA;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import CLASES.Mascota;

public class Panel_Busqueda extends JPanel {

    private Panel_Main panelMain;
    private DefaultTableModel modeloTabla;
    private JTable tablaMascotas;
    private Mascota mascota;

    public Panel_Busqueda(Panel_Main panelMain) {
        this.panelMain = panelMain;
        this.mascota = new Mascota();
    }

    public JPanel Crear_Panel() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(0, 15));
        panelPrincipal.setBackground(Color.BLACK);

        // ------------------- PANEL ARRIBA -------------------
        JPanel panelArriba = new JPanel();
        panelArriba.setBackground(Color.BLACK);
        JLabel lblTitulo = new JLabel("BÚSQUEDA DE MASCOTAS");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        panelArriba.add(lblTitulo);
        panelPrincipal.add(panelArriba, BorderLayout.NORTH);

        // ------------------- PANEL CENTRO -------------------
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BorderLayout(0, 15));
        panelCentro.setBackground(Color.BLACK);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        // ===== PANEL DE BÚSQUEDA =====
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBackground(new Color(30, 30, 30));
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.Y_AXIS));
        panelBusqueda.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 255), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel lblTituloBusqueda = new JLabel("Buscar por:");
        lblTituloBusqueda.setForeground(new Color(0, 150, 255));
        lblTituloBusqueda.setFont(new Font("Arial", Font.BOLD, 16));
        lblTituloBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel con opciones de búsqueda
        JPanel panelOpciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelOpciones.setBackground(new Color(30, 30, 30));
        panelOpciones.setAlignmentX(Component.LEFT_ALIGNMENT);

        JRadioButton rbID = new JRadioButton("ID Mascota");
        JRadioButton rbNombre = new JRadioButton("Nombre Mascota");
        JRadioButton rbPropietario = new JRadioButton("Propietario");
        JRadioButton rbCI = new JRadioButton("CI Propietario");

        rbID.setForeground(Color.WHITE);
        rbNombre.setForeground(Color.WHITE);
        rbPropietario.setForeground(Color.WHITE);
        rbCI.setForeground(Color.WHITE);

        rbID.setBackground(new Color(30, 30, 30));
        rbNombre.setBackground(new Color(30, 30, 30));
        rbPropietario.setBackground(new Color(30, 30, 30));
        rbCI.setBackground(new Color(30, 30, 30));

        rbID.setFont(new Font("Arial", Font.PLAIN, 14));
        rbNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        rbPropietario.setFont(new Font("Arial", Font.PLAIN, 14));
        rbCI.setFont(new Font("Arial", Font.PLAIN, 14));

        ButtonGroup grupoBusqueda = new ButtonGroup();
        grupoBusqueda.add(rbID);
        grupoBusqueda.add(rbNombre);
        grupoBusqueda.add(rbPropietario);
        grupoBusqueda.add(rbCI);

        rbNombre.setSelected(true); // Por defecto

        panelOpciones.add(rbID);
        panelOpciones.add(rbNombre);
        panelOpciones.add(rbPropietario);
        panelOpciones.add(rbCI);

        // Campo de búsqueda y botones
        JPanel panelCampoBusqueda = new JPanel();
        panelCampoBusqueda.setLayout(new BoxLayout(panelCampoBusqueda, BoxLayout.X_AXIS));
        panelCampoBusqueda.setBackground(new Color(30, 30, 30));
        panelCampoBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);

        CampoTextoPersonalizado txtBusqueda = new CampoTextoPersonalizado("Ingrese término de búsqueda");
        txtBusqueda.setMaximumSize(new Dimension(350, 35));
        txtBusqueda.setPreferredSize(new Dimension(350, 35));

        JButton btnBuscar = BotonPersonalizado.crearBoton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(100, 35));

        JButton btnMostrarTodas = BotonPersonalizado.crearBoton("Mostrar Todas");
        btnMostrarTodas.setPreferredSize(new Dimension(130, 35));

        JButton btnLimpiar = BotonPersonalizado.crearBoton("Limpiar");
        btnLimpiar.setPreferredSize(new Dimension(100, 35));

        panelCampoBusqueda.add(txtBusqueda);
        panelCampoBusqueda.add(Box.createHorizontalStrut(10));
        panelCampoBusqueda.add(btnBuscar);
        panelCampoBusqueda.add(Box.createHorizontalStrut(10));
        panelCampoBusqueda.add(btnMostrarTodas);
        panelCampoBusqueda.add(Box.createHorizontalStrut(10));
        panelCampoBusqueda.add(btnLimpiar);

        panelBusqueda.add(lblTituloBusqueda);
        panelBusqueda.add(Box.createVerticalStrut(8));
        panelBusqueda.add(panelOpciones);
        panelBusqueda.add(Box.createVerticalStrut(10));
        panelBusqueda.add(panelCampoBusqueda);

        panelCentro.add(panelBusqueda, BorderLayout.NORTH);

        // ===== TABLA DE RESULTADOS =====
        String[] columnas = {"ID", "Mascota", "Especie", "Raza", "Edad", "Propietario", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaMascotas = new JTable(modeloTabla);
        tablaMascotas.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaMascotas.setRowHeight(30);
        tablaMascotas.setForeground(Color.WHITE);
        tablaMascotas.setBackground(Color.BLACK);
        tablaMascotas.setGridColor(new Color(0, 0, 255));
        tablaMascotas.setSelectionBackground(new Color(0, 100, 255));
        tablaMascotas.setSelectionForeground(Color.WHITE);

        // Ajustar anchos de columnas
        tablaMascotas.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID
        tablaMascotas.getColumnModel().getColumn(1).setPreferredWidth(120); // Mascota
        tablaMascotas.getColumnModel().getColumn(2).setPreferredWidth(80);  // Especie
        tablaMascotas.getColumnModel().getColumn(3).setPreferredWidth(100); // Raza
        tablaMascotas.getColumnModel().getColumn(4).setPreferredWidth(50);  // Edad
        tablaMascotas.getColumnModel().getColumn(5).setPreferredWidth(150); // Propietario
        tablaMascotas.getColumnModel().getColumn(6).setPreferredWidth(100); // Teléfono

        JTableHeader header = tablaMascotas.getTableHeader();
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 2));

        JScrollPane scroll = new JScrollPane(tablaMascotas);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 2));
        scroll.getViewport().setBackground(Color.BLACK);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Color.BLACK);
        
        JLabel lblResultados = new JLabel("Resultados: 0 registros");
        lblResultados.setForeground(new Color(0, 150, 255));
        lblResultados.setFont(new Font("Arial", Font.BOLD, 14));
        
        JPanel panelTituloTabla = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTituloTabla.setBackground(Color.BLACK);
        panelTituloTabla.add(lblResultados);
        
        panelTabla.add(panelTituloTabla, BorderLayout.NORTH);
        panelTabla.add(scroll, BorderLayout.CENTER);

        panelCentro.add(panelTabla, BorderLayout.CENTER);

        // ===== BOTÓN ATRÁS =====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        panelBotones.setBackground(Color.BLACK);

        JButton btnAtras = BotonPersonalizado.crearBoton("Atrás");
        btnAtras.addActionListener(e -> {
            limpiarTabla();
            txtBusqueda.setText("");
            // Ajustar según desde dónde se accede
            panelMain.mostrarPanel("menuRecepcionista"); // o "veterinario"
        });

        panelBotones.add(btnAtras);
        panelCentro.add(panelBotones, BorderLayout.SOUTH);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // ===== LISTENERS =====

        // Botón Buscar
        btnBuscar.addActionListener(e -> {
            String termino = txtBusqueda.getText().trim();
            
            if (termino.isEmpty()) {
                JOptionPane.showMessageDialog(panelPrincipal,
                    "Por favor ingrese un término de búsqueda",
                    "Campo vacío",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tipoBusqueda = "";
            if (rbID.isSelected()) tipoBusqueda = "ID";
            else if (rbNombre.isSelected()) tipoBusqueda = "NOMBRE";
            else if (rbPropietario.isSelected()) tipoBusqueda = "PROPIETARIO";
            else if (rbCI.isSelected()) tipoBusqueda = "CI";

            buscarMascotas(termino, tipoBusqueda, lblResultados);
        });

        // Botón Mostrar Todas
        btnMostrarTodas.addActionListener(e -> {
            cargarTodasLasMascotas(lblResultados);
        });

        // Botón Limpiar
        btnLimpiar.addActionListener(e -> {
            limpiarTabla();
            txtBusqueda.setText("");
            lblResultados.setText("Resultados: 0 registros");
        });

        // Enter en el campo de texto
        txtBusqueda.addActionListener(e -> btnBuscar.doClick());

        // Doble click en la tabla para ver detalles
        tablaMascotas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int filaSeleccionada = tablaMascotas.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        mostrarDetallesMascota(filaSeleccionada);
                    }
                }
            }
        });

        return panelPrincipal;
    }

    /**
     * Busca mascotas según el criterio seleccionado
     */
    private void buscarMascotas(String termino, String tipo, JLabel lblResultados) {
        limpiarTabla();
        
        try {

            List<CLASES.MascotaCompleta> resultados = mascota.buscarMascotas(termino, tipo);
            
            if (resultados != null && !resultados.isEmpty()) {
                for (CLASES.MascotaCompleta mascota : resultados) {
                    Object[] fila = {
                        mascota.getIdMascota(),
                        mascota.getNombreMascota(),
                        mascota.getEspecie(),
                        mascota.getRaza(),
                        mascota.getEdad() + " años",
                        mascota.getNombrePropietario(),
                        mascota.getTelefono()
                    };
                    modeloTabla.addRow(fila);
                }
                lblResultados.setText("Resultados: " + resultados.size() + " registro(s)");
            } else {
                lblResultados.setText("Resultados: 0 registros");
                JOptionPane.showMessageDialog(this,
                    "No se encontraron resultados para: " + termino,
                    "Sin resultados",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error al buscar: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga todas las mascotas registradas
     */
    private void cargarTodasLasMascotas(JLabel lblResultados) {
        limpiarTabla();
        
        try {
 
            List<CLASES.MascotaCompleta> mascotas = mascota.obtenerTodasLasMascotas();
            
            if (mascotas != null && !mascotas.isEmpty()) {
                for (CLASES.MascotaCompleta mascota : mascotas) {
                    Object[] fila = {
                        mascota.getIdMascota(),
                        mascota.getNombreMascota(),
                        mascota.getEspecie(),
                        mascota.getRaza(),
                        mascota.getEdad() + " años",
                        mascota.getNombrePropietario(),
                        mascota.getTelefono()
                    };
                    modeloTabla.addRow(fila);
                }
                lblResultados.setText("Resultados: " + mascotas.size() + " registro(s)");
            } else {
                lblResultados.setText("Resultados: 0 registros");
                JOptionPane.showMessageDialog(this,
                    "No hay mascotas registradas",
                    "Sin registros",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error al cargar mascotas: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra los detalles completos de una mascota
     */
    private void mostrarDetallesMascota(int fila) {
        int idMascota = (int) modeloTabla.getValueAt(fila, 0);
        String nombreMascota = (String) modeloTabla.getValueAt(fila, 1);
        String especie = (String) modeloTabla.getValueAt(fila, 2);
        String raza = (String) modeloTabla.getValueAt(fila, 3);
        String nombrePropietario = (String) modeloTabla.getValueAt(fila, 5);
        String telefono = (String) modeloTabla.getValueAt(fila, 6);

        String mensaje = String.format(
            "═══════════════════════════════════\n" +
            "           DETALLES DE MASCOTA\n" +
            "═══════════════════════════════════\n\n" +
            "ID: %d\n" +
            "Nombre: %s\n" +
            "Especie: %s\n" +
            "Raza: %s\n" +
            "Edad: %s\n\n" +
            "Propietario: %s\n" +
            "Teléfono: %s\n",
            idMascota,
            nombreMascota,
            especie,
            raza,
            modeloTabla.getValueAt(fila, 4),
            nombrePropietario,
            telefono
        );

        JTextArea textArea = new JTextArea(mensaje);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);

        JOptionPane.showMessageDialog(this,
            textArea,
            "Información de Mascota",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Limpia la tabla
     */
    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    // ===== CLASE INTERNA PARA DATOS COMPLETOS =====
    public static class MascotaCompleta {
        private int idMascota;
        private String nombreMascota;
        private String especie;
        private String raza;
        private int edad;
        private String nombrePropietario;
        private String telefono;

        // Getters y Setters
        public int getIdMascota() { return idMascota; }
        public void setIdMascota(int idMascota) { this.idMascota = idMascota; }

        public String getNombreMascota() { return nombreMascota; }
        public void setNombreMascota(String nombreMascota) { this.nombreMascota = nombreMascota; }

        public String getEspecie() { return especie; }
        public void setEspecie(String especie) { this.especie = especie; }

        public String getRaza() { return raza; }
        public void setRaza(String raza) { this.raza = raza; }

        public int getEdad() { return edad; }
        public void setEdad(int edad) { this.edad = edad; }

        public String getNombrePropietario() { return nombrePropietario; }
        public void setNombrePropietario(String nombrePropietario) { 
            this.nombrePropietario = nombrePropietario; 
        }

        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
    }
}