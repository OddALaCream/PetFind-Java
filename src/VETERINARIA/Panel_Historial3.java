package VETERINARIA;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import CLASES.HistorialDiagnostico;

import java.util.List;

public class Panel_Historial3 extends JPanel {

    private Panel_Main panelMain;
    private int idMascotaActual = -1;
    private HistorialDiagnostico historial;

    public Panel_Historial3(Panel_Main panelMain) {
        this.panelMain = panelMain;
        this.historial = new HistorialDiagnostico();
    }

    public JPanel Crear_Panel() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 20));
        panelPrincipal.setBackground(Color.BLACK);

        // ------------------- PANEL ARRIBA -------------------
        JPanel panelArriba = new JPanel();
        panelArriba.setBackground(Color.BLACK);
        JLabel lblTitulo = new JLabel("VER HISTORIAL MÉDICO");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        panelArriba.add(lblTitulo);
        panelPrincipal.add(panelArriba, BorderLayout.NORTH);

        // ------------------- PANEL CENTRO -------------------
        JPanel panelCentro = new JPanel(new BorderLayout(0, 15));
        panelCentro.setBackground(Color.BLACK);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        // ===== PANEL BOTONES =====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 10));
        panelBotones.setBackground(Color.BLACK);

        JButton btnDatosGenerales = BotonPersonalizado.crearBoton("Datos Generales");
        JButton btnHistorial = BotonPersonalizado.crearBoton("Historial");
        JButton btnAnterior = BotonPersonalizado.crearBoton("Anterior");

        btnHistorial.setEnabled(false);
        btnDatosGenerales.addActionListener(e -> panelMain.mostrarPanel("historial2"));
        btnAnterior.addActionListener(e -> panelMain.mostrarPanel("historial2"));

        panelBotones.add(btnDatosGenerales);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnAnterior);

        panelCentro.add(panelBotones, BorderLayout.NORTH);

        // ===== INFO DE LA MASCOTA =====
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        panelInfo.setBackground(new Color(30, 30, 30));
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 255), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel lblInfoMascota = new JLabel("Cargando datos...");
        lblInfoMascota.setForeground(Color.WHITE);
        lblInfoMascota.setFont(new Font("Arial", Font.BOLD, 18));
        panelInfo.add(lblInfoMascota);

        panelCentro.add(panelInfo, BorderLayout.CENTER);

        // ===== TABLA DE DIAGNÓSTICOS =====
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Color.BLACK);

        JLabel lblTituloTabla = new JLabel("Historial de Consultas:");
        lblTituloTabla.setForeground(new Color(0, 150, 255));
        lblTituloTabla.setFont(new Font("Arial", Font.BOLD, 20));
        lblTituloTabla.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelTabla.add(lblTituloTabla, BorderLayout.NORTH);

        String[] columnas = {"#", "Fecha", "Veterinario", "Temperatura", "F. Cardíaca", "F. Respiratoria"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Arial", Font.PLAIN, 15));
        tabla.setRowHeight(30);
        tabla.setForeground(Color.WHITE);
        tabla.setBackground(Color.BLACK);
        tabla.setGridColor(new Color(0, 0, 255));
        tabla.setSelectionBackground(new Color(0, 50, 150));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajustar anchos de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(120);

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 2));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 3));
        scroll.getViewport().setBackground(Color.BLACK);

        // ===== LISTENER: DOBLE CLIC EN FILA =====
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tabla.getSelectedRow() != -1) {
                    int filaSeleccionada = tabla.getSelectedRow();
                    Object valorCelda = modeloTabla.getValueAt(filaSeleccionada, 0);
                    
                    System.out.println("DEBUG Panel_Historial3: Celda seleccionada = " + valorCelda);
                    
                    int idDiagnostico = (Integer) valorCelda;
                    
                    System.out.println("DEBUG Panel_Historial3: ID a guardar = " + idDiagnostico);
                    
                    panelMain.setDatoCompartido("idDiagnosticoSeleccionado", idDiagnostico);
                    
                    Object verificar = panelMain.getDatoCompartido("idDiagnosticoSeleccionado");
                    System.out.println("DEBUG Panel_Historial3: ID guardado verificado = " + verificar);
                    
                    panelMain.mostrarPanel("historial4");
                }
            }
        });

        // ✅ AGREGAR EL SCROLL AL PANEL DE TABLA (ESTO FALTABA)
        panelTabla.add(scroll, BorderLayout.CENTER);
        panelCentro.add(panelTabla, BorderLayout.SOUTH);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // ✅ CARGAR DATOS AL MOSTRAR EL PANEL (ESTO FALTABA)
        panelPrincipal.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                cargarHistorial(modeloTabla, lblInfoMascota);
            }
        });

        return panelPrincipal;
    }

    /**
     * Carga el historial de diagnósticos de la mascota actual
     */
    private void cargarHistorial(DefaultTableModel modelo, JLabel lblInfo) {
        try {
            Object idMascotaObj = panelMain.getDatoCompartido("idMascota");
            Object nombreMascotaObj = panelMain.getDatoCompartido("nombreMascota");
            
            if (idMascotaObj == null) {
                JOptionPane.showMessageDialog(this,
                    "Error: No se encontró información de la mascota.\nVuelva a buscar desde Datos Generales.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                panelMain.mostrarPanel("historial2");
                return;
            }
            
            idMascotaActual = (Integer) idMascotaObj;
            String nombreMascota = nombreMascotaObj != null ? (String) nombreMascotaObj : "Desconocido";
            
            lblInfo.setText("📋 Historial de: " + nombreMascota + " (ID: " + idMascotaActual + ")");
            
            modelo.setRowCount(0);
            
            List<HistorialDiagnostico> diagnosticos = historial.obtenerHistorialDiagnosticos(idMascotaActual);
            
            if (diagnosticos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Esta mascota no tiene diagnósticos registrados",
                    "Sin historial",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (HistorialDiagnostico diag : diagnosticos) {
                    System.out.println("DEBUG Panel_Historial3: Agregando ID = " + diag.getIdDiagnostico());
                    
                    modelo.addRow(new Object[]{
                        diag.getIdDiagnostico(),
                        diag.getFecha(),
                        diag.getNombreVeterinario(),
                        diag.getTemperatura() + "°C",
                        diag.getFrecuenciaCardiaca() + " bpm",
                        diag.getFrecuenciaRespiratoria() + " rpm"
                    });
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error al cargar historial: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}