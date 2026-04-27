package VETERINARIA;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import CLASES.Ficha;
import CLASES.Mascota;
import CLASES.Propietario;

public class Panel_Agendar4 extends JPanel {

    private Panel_Main panelMain;
    private DefaultTableModel modeloTabla;
    private JTable tablaMascotas;
    private int idPropietarioActual = -1;
    private JLabel lblInfoPropietario;
    
    private Ficha ficha;
    private Mascota mascota;
    private Propietario propietario;

    public Panel_Agendar4(Panel_Main panelMain) {
        this.panelMain = panelMain;
        this.ficha = new Ficha();
        this.propietario = new Propietario();
        this.mascota = new Mascota();
    }

    public JPanel Crear_Panel() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(0, 20));
        panelPrincipal.setBackground(Color.BLACK);


        JPanel panelArriba = new JPanel();
        panelArriba.setBackground(Color.BLACK);
        JLabel lblTitulo = new JLabel("REGISTRAR FICHA DE ATENCIÓN");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        panelArriba.add(lblTitulo);
        panelPrincipal.add(panelArriba, BorderLayout.NORTH);

        // ------------------- PANEL CENTRO -------------------
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BorderLayout(0, 10));
        panelCentro.setBackground(Color.BLACK);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // ===== SECCIÓN: BUSCAR PROPIETARIO =====
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.Y_AXIS));
        panelBusqueda.setBackground(new Color(30, 30, 30));
        panelBusqueda.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 255), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel lblBusqueda = new JLabel("Buscar Propietario por CI:");
        lblBusqueda.setForeground(new Color(0, 150, 255));
        lblBusqueda.setFont(new Font("Arial", Font.BOLD, 15));
        lblBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel filaBusqueda = new JPanel();
        filaBusqueda.setLayout(new BoxLayout(filaBusqueda, BoxLayout.X_AXIS));
        filaBusqueda.setBackground(new Color(30, 30, 30));
        filaBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);

        CampoTextoPersonalizado txtCI = new CampoTextoPersonalizado("Carnet de Identidad");
        txtCI.setMaximumSize(new Dimension(220, 32));
        txtCI.setPreferredSize(new Dimension(220, 32));
        
        JButton btnBuscar = BotonPersonalizado.crearBoton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(100, 32));

        lblInfoPropietario = new JLabel("No seleccionado");
        lblInfoPropietario.setForeground(Color.YELLOW);
        lblInfoPropietario.setFont(new Font("Arial", Font.PLAIN, 13));
        lblInfoPropietario.setAlignmentX(Component.LEFT_ALIGNMENT);

        filaBusqueda.add(txtCI);
        filaBusqueda.add(Box.createHorizontalStrut(8));
        filaBusqueda.add(btnBuscar);

        panelBusqueda.add(lblBusqueda);
        panelBusqueda.add(Box.createVerticalStrut(6));
        panelBusqueda.add(filaBusqueda);
        panelBusqueda.add(Box.createVerticalStrut(6));
        panelBusqueda.add(lblInfoPropietario);

        // ===== PANEL DE BOTONES (ARRIBA) =====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelBotones.setBackground(Color.BLACK);
        
        JButton btnAtras = BotonPersonalizado.crearBoton("Atrás");
        JButton btnSeleccionar = BotonPersonalizado.crearBoton("Registrar Ficha");
        JButton btnAgregarMascota = BotonPersonalizado.crearBoton("Agregar Mascota");
        
        panelBotones.add(btnAtras);
        panelBotones.add(btnSeleccionar);
        panelBotones.add(btnAgregarMascota);

        // Panel superior que contiene búsqueda y botones
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(Color.BLACK);
        panelSuperior.add(panelBusqueda);
        panelSuperior.add(Box.createVerticalStrut(10));
        panelSuperior.add(panelBotones);
        
        panelCentro.add(panelSuperior, BorderLayout.NORTH);

        // ===== SECCIÓN: TABLA DE MASCOTAS (COMPACTA) =====
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Color.BLACK);
        panelTabla.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 255), 2),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        panelTabla.setVisible(false);
        panelTabla.setPreferredSize(new Dimension(0, 180)); // Altura total reducida

        JLabel lblTituloTabla = new JLabel("Mascotas:");
        lblTituloTabla.setForeground(new Color(0, 150, 255));
        lblTituloTabla.setFont(new Font("Arial", Font.BOLD, 14));

        String[] columnas = {"#", "Nombre", "Especie", "Raza", "Edad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaMascotas = new JTable(modeloTabla);
        tablaMascotas.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaMascotas.setRowHeight(22);
        tablaMascotas.setForeground(Color.WHITE);
        tablaMascotas.setBackground(Color.BLACK);
        tablaMascotas.setGridColor(new Color(0, 0, 255));
        tablaMascotas.setSelectionBackground(new Color(0, 100, 255));
        tablaMascotas.setSelectionForeground(Color.WHITE);

        JTableHeader header = tablaMascotas.getTableHeader();
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 1));

        JScrollPane scroll = new JScrollPane(tablaMascotas);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 1));
        scroll.getViewport().setBackground(Color.BLACK);
        scroll.setPreferredSize(new Dimension(0, 130)); // Scroll más pequeño

        JPanel panelContenido = new JPanel(new BorderLayout(0, 3));
        panelContenido.setBackground(Color.BLACK);
        panelContenido.add(lblTituloTabla, BorderLayout.NORTH);
        panelContenido.add(scroll, BorderLayout.CENTER);

        panelTabla.add(panelContenido, BorderLayout.CENTER);
        panelCentro.add(panelTabla, BorderLayout.CENTER);
        
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // ===== LISTENER DEL BOTÓN BUSCAR =====
        btnBuscar.addActionListener(e -> {
            String ciStr = txtCI.getText().trim();
            if (ciStr.isEmpty()) {
                JOptionPane.showMessageDialog(panelPrincipal, 
                    "Por favor ingrese un CI",
                    "Campo vacío",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int ci = Integer.parseInt(ciStr);
                
                
                // Buscar propietario por CI
                int idPropietario = propietario.obtenerIdPropietarioPorCI(ci);
                
                if (idPropietario != -1) {
                    idPropietarioActual = idPropietario;
                    
                    // Obtener nombre del propietario
                    String nombrePropietario = propietario.obtenerNombrePropietario(idPropietario);
                    lblInfoPropietario.setText("✓ " + nombrePropietario);
                    lblInfoPropietario.setForeground(Color.GREEN);
                    
                    // Cargar mascotas del propietario
                    cargarMascotasPropietario(idPropietario);
                    panelTabla.setVisible(true);
                    
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal, 
                        "No se encontró propietario con CI: " + ci,
                        "Propietario no encontrado",
                        JOptionPane.WARNING_MESSAGE);
                    lblInfoPropietario.setText("✗ No encontrado");
                    lblInfoPropietario.setForeground(Color.RED);
                    panelTabla.setVisible(false);
                    idPropietarioActual = -1;
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panelPrincipal, 
                    "El CI debe ser numérico",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panelPrincipal, 
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // ===== LISTENER DE LA TABLA (DOBLE CLICK) =====
        tablaMascotas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Doble click
                    int filaSeleccionada = tablaMascotas.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        registrarFichaYMostrar(filaSeleccionada);
                    }
                }
            }
        });

        // ===== LISTENER DEL BOTÓN ATRÁS =====
        btnAtras.addActionListener(e -> {
            limpiarDatos(txtCI, panelTabla);
            panelMain.mostrarPanel("menuRecepcionista");
        });

        // ===== LISTENER DEL BOTÓN REGISTRAR FICHA =====
        btnSeleccionar.addActionListener(e -> {
            int filaSeleccionada = tablaMascotas.getSelectedRow();
            if (filaSeleccionada != -1) {
                registrarFichaYMostrar(filaSeleccionada);
            } else {
                JOptionPane.showMessageDialog(panelPrincipal, 
                    "Por favor seleccione una mascota",
                    "Ninguna mascota seleccionada",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // ===== LISTENER DEL BOTÓN AGREGAR MASCOTA =====
        btnAgregarMascota.addActionListener(e -> {
            if (idPropietarioActual != -1) {
                // Guardar ID del propietario actual para el siguiente panel
                panelMain.setDatoCompartido("idPropietarioParaMascota", idPropietarioActual);
                panelMain.setDatoCompartido("nombrePropietarioParaMascota", lblInfoPropietario.getText().substring(2)); // Quitar el "✓ "
                panelMain.mostrarPanel("agendar5");
            } else {
                JOptionPane.showMessageDialog(panelPrincipal, 
                    "Primero debe buscar un propietario",
                    "Propietario no seleccionado",
                    JOptionPane.WARNING_MESSAGE);
            }
        });

        return panelPrincipal;
    }

    /**
     * Carga las mascotas de un propietario en la tabla
     */
    private void cargarMascotasPropietario(int idPropietario) {
        modeloTabla.setRowCount(0); // Limpiar tabla
        
        try {
            BD bd = new BD();
            java.util.List<Mascota> mascotas = mascota.obtenerMascotasPorPropietario(idPropietario);
            
            if (mascotas != null && !mascotas.isEmpty()) {
                int contador = 1;
                for (Mascota mascota : mascotas) {
                    Object[] fila = {
                        contador++,
                        mascota.getNombre(),
                        mascota.getEspecie(),
                        mascota.getRaza(),
                        mascota.getEdadAproximada() + " años"
                    };
                    modeloTabla.addRow(fila);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Este propietario no tiene mascotas registradas",
                    "Sin mascotas",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar mascotas:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarFichaYMostrar(int filaSeleccionada) {
        try {

            
            // Obtener ID de la mascota seleccionada
            String nombreMascota = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            int idMascota = mascota.obtenerIdMascotaPorNombreYPropietario(nombreMascota, idPropietarioActual);
            
            if (idMascota == -1) {
                JOptionPane.showMessageDialog(this, 
                    "Error al obtener ID de la mascota",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Generar código de ficha
            int codigoFicha = ficha.generarCodigoFicha();
            
            // Fecha actual
            LocalDate fechaActual = LocalDate.now();
            Date fechaSQL = Date.valueOf(fechaActual);
            
            // ID del recepcionista actual
            int idRecepcionista = panelMain.getIdPersonaActual();
            
            // Registrar la ficha en la BD
            ficha.registrarFicha(idRecepcionista, codigoFicha, fechaSQL, idMascota);
            
            // Obtener datos completos de la mascota
            mascota = mascota.obtenerDatosMascota(idMascota);
            String nombrePropietario = propietario.obtenerNombrePropietario(idPropietarioActual);
            
            // Guardar datos en Panel_Main para compartir
            panelMain.setDatoCompartido("codigoFicha", codigoFicha);
            panelMain.setDatoCompartido("fechaFicha", fechaActual);
            panelMain.setDatoCompartido("idMascota", idMascota);
            panelMain.setDatoCompartido("nombreMascota", mascota.getNombre());
            panelMain.setDatoCompartido("especieMascota", mascota.getEspecie());
            panelMain.setDatoCompartido("nombrePropietario", nombrePropietario);
            
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                "Ficha registrada exitosamente\nCódigo: " + codigoFicha,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Mostrar panel de ficha
            panelMain.mostrarPanel("ficha");
            
            // Limpiar datos
            limpiarDatos(null, null);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al registrar ficha:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarDatos(CampoTextoPersonalizado txtCI, JPanel panelTabla) {
        if (txtCI != null) {
            txtCI.setText("");
        }
        modeloTabla.setRowCount(0);
        if (panelTabla != null) {
            panelTabla.setVisible(false);
        }
        lblInfoPropietario.setText("No seleccionado");
        lblInfoPropietario.setForeground(Color.YELLOW);
        idPropietarioActual = -1;
    }
}