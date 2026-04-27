package VETERINARIA;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import CLASES.Diagnostico;
import CLASES.Ficha;
import CLASES.Mascota;

public class Panel_Diagnostico1 extends JPanel {

    private Panel_Main panelMain;
    
    // Listas para almacenar los datos antes de guardar
    private List<String> listaEnfermedades;
    private List<String> listaAlergias;
    private List<String> listaObservaciones;
    
    // Variable para guardar el ID de la mascota encontrada
    private int idMascotaActual = -1;
    private Diagnostico diagnostico;
    private Ficha ficha;
    
    private Mascota mascota;

    public Panel_Diagnostico1(Panel_Main panelMain) {
        this.panelMain = panelMain;
        this.listaEnfermedades = new ArrayList<>();
        this.listaAlergias = new ArrayList<>();
        this.listaObservaciones = new ArrayList<>();
        this.diagnostico = new Diagnostico();
        this.mascota= new Mascota(); 
        this.ficha = new Ficha();
    }

    public JPanel Crear_Panel() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBackground(Color.BLACK);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.BLACK);
        JLabel lblTitulo = new JLabel("REGISTRAR DIAGNÓSTICO:");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        panelTitulo.add(lblTitulo);
        panelIzquierdo.add(panelTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(Color.BLACK);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); 

        // ===== SECCIÓN: BUSCAR MASCOTA (MÁS COMPACTA) =====
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.Y_AXIS));
        panelBusqueda.setBackground(new Color(30, 30, 30));
        panelBusqueda.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 255), 2),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        panelBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblBusqueda = new JLabel("Buscar Mascota:");
        lblBusqueda.setForeground(new Color(0, 150, 255));
        lblBusqueda.setFont(new Font("Arial", Font.BOLD, 14));
        lblBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel filaBusqueda = new JPanel();
        filaBusqueda.setLayout(new BoxLayout(filaBusqueda, BoxLayout.X_AXIS));
        filaBusqueda.setBackground(new Color(30, 30, 30));
        filaBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);

        CampoTextoPersonalizado txtCodigoFicha = new CampoTextoPersonalizado("Código");
        txtCodigoFicha.setMaximumSize(new Dimension(200, 30));
        txtCodigoFicha.setPreferredSize(new Dimension(200, 30));
        
        JButton btnBuscar = BotonPersonalizado.crearBoton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(100, 30));

        JLabel lblInfoMascota = new JLabel("No seleccionada");
        lblInfoMascota.setForeground(Color.YELLOW);
        lblInfoMascota.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInfoMascota.setAlignmentX(Component.LEFT_ALIGNMENT);

        filaBusqueda.add(txtCodigoFicha);
        filaBusqueda.add(Box.createHorizontalStrut(5));
        filaBusqueda.add(btnBuscar);

        panelBusqueda.add(lblBusqueda);
        panelBusqueda.add(Box.createVerticalStrut(5));
        panelBusqueda.add(filaBusqueda);
        panelBusqueda.add(Box.createVerticalStrut(5));
        panelBusqueda.add(lblInfoMascota);

        // ===== LISTENER DEL BOTÓN BUSCAR =====
     // ===== LISTENER DEL BOTÓN BUSCAR =====
        btnBuscar.addActionListener(e -> {
            String codigoFichaStr = txtCodigoFicha.getText().trim();
            if (codigoFichaStr.isEmpty()) {
                JOptionPane.showMessageDialog(panel, 
                    "Por favor ingrese un código de ficha",
                    "Campo vacío",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int codigoFicha = Integer.parseInt(codigoFichaStr);
                
                // ✅ CREAR NUEVA INSTANCIA DE FICHA PARA CADA BÚSQUEDA
                Ficha fichaTemp = new Ficha();
                fichaTemp = fichaTemp.obtenerFichaPorCodigo1(codigoFicha);
                
                if (fichaTemp != null) {
                    // ✅ OBTENER ID DE MASCOTA DIRECTAMENTE DE LA FICHA
                    idMascotaActual = fichaTemp.getIdMascota();
                    
                    if (idMascotaActual > 0) {
                        // ✅ ACTUALIZAR LA FICHA DE INSTANCIA
                        this.ficha = fichaTemp;
                        
                        lblInfoMascota.setText("✓ " + fichaTemp.getNombreMascota() + " - " + fichaTemp.getEspecieMascota());
                        lblInfoMascota.setForeground(Color.GREEN);
                        
                        // Guardar datos compartidos
                        panelMain.setDatoCompartido("idMascota", idMascotaActual);
                        panelMain.setDatoCompartido("nombreMascota", fichaTemp.getNombreMascota());
                        panelMain.setDatoCompartido("especieMascota", fichaTemp.getEspecieMascota());
                        
                        System.out.println("DEBUG Diagnostico1: ID Mascota = " + idMascotaActual + ", Nombre = " + fichaTemp.getNombreMascota());
                    } else {
                        lblInfoMascota.setText("✗ La ficha no tiene mascota asociada");
                        lblInfoMascota.setForeground(Color.RED);
                        idMascotaActual = -1;
                    }
                } else {
                    lblInfoMascota.setText("✗ Ficha no encontrada");
                    lblInfoMascota.setForeground(Color.RED);
                    idMascotaActual = -1;
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, 
                    "El código debe ser numérico",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panel, 
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        panelCentro.add(panelBusqueda);
        panelCentro.add(Box.createVerticalStrut(10));

        // ===== CAMPOS DEL DIAGNÓSTICO (MÁS COMPACTOS) =====
        CampoTextoPersonalizado txtTemperatura = new CampoTextoPersonalizado("Temperatura");
        CampoTextoPersonalizado txtFrecuenciaCardiaca = new CampoTextoPersonalizado("F. Cardiaca");
        CampoTextoPersonalizado txtFrecuenciaRespiratoria = new CampoTextoPersonalizado("F. Respiratoria");
        CampoTextoPersonalizado txtEnfermedadesDetectadas = new CampoTextoPersonalizado("Enfermedad");
        CampoTextoPersonalizado txtAlergiasDetectadas = new CampoTextoPersonalizado("Alergia");
        CampoTextoPersonalizado txtObservaciones = new CampoTextoPersonalizado("Observación");
        
        // Labels más pequeños
        Font fontLabel = new Font("Arial", Font.PLAIN, 16);
        
        JLabel lblTemperatura = new JLabel("Temperatura:");
        lblTemperatura.setForeground(Color.WHITE);
        lblTemperatura.setFont(fontLabel);
        lblTemperatura.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblFrecuenciaCardiaca = new JLabel("Frecuencia Cardiaca:");
        lblFrecuenciaCardiaca.setForeground(Color.WHITE);
        lblFrecuenciaCardiaca.setFont(fontLabel);
        lblFrecuenciaCardiaca.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblFrecuenciaRespiratoria = new JLabel("Frecuencia Respiratoria:");
        lblFrecuenciaRespiratoria.setForeground(Color.WHITE);
        lblFrecuenciaRespiratoria.setFont(fontLabel);
        lblFrecuenciaRespiratoria.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblEnfermedadesDetectadas = new JLabel("Enfermedades:");
        lblEnfermedadesDetectadas.setForeground(Color.WHITE);
        lblEnfermedadesDetectadas.setFont(fontLabel);
        lblEnfermedadesDetectadas.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblAlergiasDetectadas = new JLabel("Alergias:");
        lblAlergiasDetectadas.setForeground(Color.WHITE);
        lblAlergiasDetectadas.setFont(fontLabel);
        lblAlergiasDetectadas.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblObservaciones = new JLabel("Observaciones:");
        lblObservaciones.setForeground(Color.WHITE);
        lblObservaciones.setFont(fontLabel);
        lblObservaciones.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Crear filas más compactas
        JButton btnEnfermedadesDetectadas = BotonPersonalizado.crearBoton("+");
        btnEnfermedadesDetectadas.setPreferredSize(new Dimension(50, 30));
        
        JButton btnAlergiasDetectadas = BotonPersonalizado.crearBoton("+");
        btnAlergiasDetectadas.setPreferredSize(new Dimension(50, 30));
        
        JButton btnObservaciones = BotonPersonalizado.crearBoton("+");
        btnObservaciones.setPreferredSize(new Dimension(50, 30));

        // Crear grid de 3 columnas para los primeros 3 campos
        JPanel panelGrid = new JPanel(new GridLayout(3, 2, 10, 8));
        panelGrid.setBackground(Color.BLACK);
        panelGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        
        panelGrid.add(lblTemperatura);
        panelGrid.add(txtTemperatura);
        panelGrid.add(lblFrecuenciaCardiaca);
        panelGrid.add(txtFrecuenciaCardiaca);
        panelGrid.add(lblFrecuenciaRespiratoria);
        panelGrid.add(txtFrecuenciaRespiratoria);

        panelCentro.add(panelGrid);
        panelCentro.add(Box.createVerticalStrut(10));

        // Enfermedades
        panelCentro.add(lblEnfermedadesDetectadas);
        panelCentro.add(Box.createVerticalStrut(3));
        JPanel filaEnf = crearFilaConBoton(txtEnfermedadesDetectadas, btnEnfermedadesDetectadas);
        panelCentro.add(filaEnf);
        panelCentro.add(Box.createVerticalStrut(8));

        // Alergias
        panelCentro.add(lblAlergiasDetectadas);
        panelCentro.add(Box.createVerticalStrut(3));
        JPanel filaAler = crearFilaConBoton(txtAlergiasDetectadas, btnAlergiasDetectadas);
        panelCentro.add(filaAler);
        panelCentro.add(Box.createVerticalStrut(8));

        // Observaciones
        panelCentro.add(lblObservaciones);
        panelCentro.add(Box.createVerticalStrut(3));
        JPanel filaObs = crearFilaConBoton(txtObservaciones, btnObservaciones);
        panelCentro.add(filaObs);
        panelCentro.add(Box.createVerticalStrut(15));

        // BOTONES PRINCIPALES
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0)); 
        panelBotones.setBackground(Color.BLACK);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnGuardar = BotonPersonalizado.crearBoton("Guardar");
        JButton btnCancelar = BotonPersonalizado.crearBoton("Atrás");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        panelCentro.add(panelBotones);

        JScrollPane scrollIzquierdo = new JScrollPane(panelCentro);
        scrollIzquierdo.setBackground(Color.BLACK);
        scrollIzquierdo.setBorder(null);
        scrollIzquierdo.getViewport().setBackground(Color.BLACK);

        panelIzquierdo.add(scrollIzquierdo, BorderLayout.CENTER);

        // PANEL DERECHO - TABLA RESUMEN
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(Color.BLACK);

        JPanel panelTituloDerecho = new JPanel();
        panelTituloDerecho.setBackground(Color.BLACK);
        JLabel lblTituloDerecho = new JLabel("RESUMEN:");
        lblTituloDerecho.setForeground(Color.WHITE);
        lblTituloDerecho.setFont(new Font("Arial", Font.BOLD, 20));
        panelTituloDerecho.add(lblTituloDerecho);
        panelDerecho.add(panelTituloDerecho, BorderLayout.NORTH);

        String[] columnas = {"Tipo", "Detalle"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        JTable tablaHistorial = new JTable(modelo);
        tablaHistorial.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaHistorial.setRowHeight(25);
        tablaHistorial.setForeground(Color.WHITE);
        tablaHistorial.setBackground(Color.BLACK); 
        tablaHistorial.setGridColor(new Color(0, 0, 255)); 
        tablaHistorial.setSelectionBackground(Color.DARK_GRAY);
        tablaHistorial.setSelectionForeground(new Color(0, 0, 255));

        JTableHeader header = tablaHistorial.getTableHeader();
        header.setBackground(Color.BLACK);  
        header.setForeground(Color.WHITE);  
        header.setFont(new Font("Arial", Font.BOLD, 16)); 
        header.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 2)); 
        
        JScrollPane scroll = new JScrollPane(tablaHistorial);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 3));
        scroll.getViewport().setBackground(Color.BLACK); 

        panelDerecho.add(scroll, BorderLayout.CENTER);

        // LISTENERS PARA AÑADIR A LA TABLA Y LISTAS
        btnEnfermedadesDetectadas.addActionListener(e -> {
            String texto = txtEnfermedadesDetectadas.getText().trim();
            if (!texto.isEmpty()) {
                listaEnfermedades.add(texto);
                modelo.addRow(new Object[]{"Enfermedad", texto});
                txtEnfermedadesDetectadas.setText("");
            }
        });

        btnAlergiasDetectadas.addActionListener(e -> {
            String texto = txtAlergiasDetectadas.getText().trim();
            if (!texto.isEmpty()) {
                listaAlergias.add(texto);
                modelo.addRow(new Object[]{"Alergia", texto});
                txtAlergiasDetectadas.setText("");
            }
        });

        btnObservaciones.addActionListener(e -> {
            String texto = txtObservaciones.getText().trim();
            if (!texto.isEmpty()) {
                listaObservaciones.add(texto);
                modelo.addRow(new Object[]{"Observación", texto});
                txtObservaciones.setText("");
            }
        });

        // BOTÓN GUARDAR
        btnGuardar.addActionListener(e -> {
            try {
                if (idMascotaActual == -1) {
                    JOptionPane.showMessageDialog(panel, 
                        "Busque una mascota primero",
                        "Mascota no seleccionada",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                String temperatura = txtTemperatura.getText().trim();
                String frecCardiaca = txtFrecuenciaCardiaca.getText().trim();
                String frecRespiratoria = txtFrecuenciaRespiratoria.getText().trim();
                
                if (temperatura.isEmpty() || frecCardiaca.isEmpty() || frecRespiratoria.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, 
                        "Complete todos los campos obligatorios",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                int idVeterinario = panelMain.getIdPersonaActual();
                
                int idDiagnostico = diagnostico.registrarDiagnostico(
                    idVeterinario, 
                    idMascotaActual, 
                    temperatura, 
                    frecCardiaca, 
                    frecRespiratoria,
                    listaEnfermedades,
                    listaAlergias,
                    listaObservaciones
                );
                
                if (idDiagnostico != -1) {
                    JOptionPane.showMessageDialog(panel, 
                        "Diagnóstico guardado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    panelMain.setDatoCompartido("idDiagnostico", idDiagnostico);
                    panelMain.mostrarPanel("diagnostico2");
                    
                    limpiarFormulario(txtCodigoFicha, txtTemperatura, txtFrecuenciaCardiaca, 
                                    txtFrecuenciaRespiratoria, modelo, lblInfoMascota);
                } else {
                    JOptionPane.showMessageDialog(panel, 
                        "Error al guardar",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panel, 
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> {
            limpiarFormulario(txtCodigoFicha, txtTemperatura, txtFrecuenciaCardiaca, 
                            txtFrecuenciaRespiratoria, modelo, lblInfoMascota);
            panelMain.mostrarPanel("veterinario");
        });

        panel.add(panelIzquierdo);
        panel.add(panelDerecho);
        
        return panel;
    }
    
    private JPanel crearFilaConBoton(CampoTextoPersonalizado campo, JButton boton) {
        JPanel fila = new JPanel();
        fila.setLayout(new BoxLayout(fila, BoxLayout.X_AXIS));
        fila.setBackground(Color.BLACK);
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        fila.add(campo);
        fila.add(Box.createHorizontalStrut(5));
        fila.add(boton);
        return fila;
    }
    
    private void limpiarFormulario(CampoTextoPersonalizado txtCodigo,
                                   CampoTextoPersonalizado txtTemp, 
                                   CampoTextoPersonalizado txtCard,
                                   CampoTextoPersonalizado txtResp,
                                   DefaultTableModel modelo,
                                   JLabel lblInfo) {
        txtCodigo.setText("");
        txtTemp.setText("");
        txtCard.setText("");
        txtResp.setText("");
        listaEnfermedades.clear();
        listaAlergias.clear();
        listaObservaciones.clear();
        modelo.setRowCount(0);
        lblInfo.setText("No seleccionada");
        lblInfo.setForeground(Color.YELLOW);
        idMascotaActual = -1;
    }
}