package VETERINARIA;

import java.awt.*;
import javax.swing.*;

import CLASES.Ficha;
import CLASES.Mascota;

public class Panel_Historial2 extends JPanel {

    private Panel_Main panelMain;
    private int idMascotaActual = -1;
    private Mascota mascota;
    private Ficha ficha;

    public Panel_Historial2(Panel_Main panelMain) {
        this.panelMain = panelMain;
        this.mascota = new Mascota();
        this.ficha = new Ficha();
    }

    public JPanel Crear_Panel() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(0, 20));
        panelPrincipal.setBackground(Color.BLACK);

        // ------------------- PANEL ARRIBA -------------------
        JPanel panelArriba = new JPanel();
        panelArriba.setBackground(Color.BLACK);
        JLabel lblTitulo = new JLabel("VER HISTORIAL MÉDICO");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        panelArriba.add(lblTitulo);
        panelPrincipal.add(panelArriba, BorderLayout.NORTH);

        // ------------------- PANEL CENTRO (contenedor) -------------------
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BorderLayout(0, 20));
        panelCentro.setBackground(Color.BLACK);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        // ===== SECCIÓN: BUSCAR MASCOTA =====
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.Y_AXIS));
        panelBusqueda.setBackground(new Color(30, 30, 30));
        panelBusqueda.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 255), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panelBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblBusqueda = new JLabel("Buscar Mascota por Código de Ficha:");
        lblBusqueda.setForeground(new Color(0, 150, 255));
        lblBusqueda.setFont(new Font("Arial", Font.BOLD, 18));
        lblBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel filaBusqueda = new JPanel();
        filaBusqueda.setLayout(new BoxLayout(filaBusqueda, BoxLayout.X_AXIS));
        filaBusqueda.setBackground(new Color(30, 30, 30));
        filaBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);

        CampoTextoPersonalizado txtCodigoFicha = new CampoTextoPersonalizado("Código de Ficha");
        txtCodigoFicha.setMaximumSize(new Dimension(250, 35));
        txtCodigoFicha.setPreferredSize(new Dimension(250, 35));
        
        JButton btnBuscar = BotonPersonalizado.crearBoton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(120, 35));

        filaBusqueda.add(txtCodigoFicha);
        filaBusqueda.add(Box.createHorizontalStrut(10));
        filaBusqueda.add(btnBuscar);

        panelBusqueda.add(lblBusqueda);
        panelBusqueda.add(Box.createVerticalStrut(10));
        panelBusqueda.add(filaBusqueda);

        panelCentro.add(panelBusqueda, BorderLayout.NORTH);

        // ===== PANEL DATOS GENERALES (inicialmente oculto) =====
        JPanel panelDatosContainer = new JPanel();
        panelDatosContainer.setLayout(new BorderLayout(0, 15));
        panelDatosContainer.setBackground(Color.BLACK);
        panelDatosContainer.setVisible(false);

        // Botones de navegación
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 10));
        panelBotones.setBackground(Color.BLACK);

        JButton btnDatosGenerales = BotonPersonalizado.crearBoton("Datos Generales");
        JButton btnHistorial = BotonPersonalizado.crearBoton("Historial");
        JButton btnAnterior = BotonPersonalizado.crearBoton("Anterior");

        btnDatosGenerales.setEnabled(false);
        
        // ✅ CONFIGURAR LISTENER UNA SOLA VEZ AQUÍ
        btnHistorial.addActionListener(e -> panelMain.mostrarPanel("historial3"));
        
        btnAnterior.addActionListener(e -> {
            limpiarDatos(panelDatosContainer, txtCodigoFicha);
            panelMain.mostrarPanel("historial1");
        });

        panelBotones.add(btnDatosGenerales);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnAnterior);

        panelDatosContainer.add(panelBotones, BorderLayout.NORTH);

        // ===== DATOS DE LA MASCOTA =====
        JPanel panelDatos = new JPanel();
        panelDatos.setBackground(Color.BLACK);
        panelDatos.setLayout(new GridLayout(1, 5, 40, 0));
        panelDatos.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 255), 2),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        // Labels que se actualizarán con los datos
        JLabel lblDatoNombre = crearLabelDato("Sin datos");
        JLabel lblDatoEspecie = crearLabelDato("Sin datos");
        JLabel lblDatoRaza = crearLabelDato("Sin datos");
        JLabel lblDatoSexo = crearLabelDato("Sin datos");
        JLabel lblDatoEdad = crearLabelDato("Sin datos");

        panelDatos.add(crearPanelCampo("Nombre:", lblDatoNombre));
        panelDatos.add(crearPanelCampo("Especie:", lblDatoEspecie));
        panelDatos.add(crearPanelCampo("Raza:", lblDatoRaza));
        panelDatos.add(crearPanelCampo("Sexo:", lblDatoSexo));
        panelDatos.add(crearPanelCampo("Edad:", lblDatoEdad));

        panelDatosContainer.add(panelDatos, BorderLayout.CENTER);

        panelCentro.add(panelDatosContainer, BorderLayout.CENTER);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // ===== LISTENER DEL BOTÓN BUSCAR =====
        btnBuscar.addActionListener(e -> {
            String codigoFichaStr = txtCodigoFicha.getText().trim();
            if (codigoFichaStr.isEmpty()) {
                JOptionPane.showMessageDialog(panelPrincipal, 
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
                    int idMascota = fichaTemp.getIdMascota();
                    
                    if (idMascota > 0) {
                        // ✅ CREAR NUEVA INSTANCIA DE MASCOTA Y OBTENER DATOS
                        Mascota mascotaTemp = new Mascota();
                        mascotaTemp = mascotaTemp.obtenerDatosMascota(idMascota);
                        
                        if (mascotaTemp != null) {
                            // ✅ ACTUALIZAR VARIABLES DE INSTANCIA
                            this.ficha = fichaTemp;
                            this.mascota = mascotaTemp;
                            this.idMascotaActual = idMascota;
                            
                            // Actualizar labels
                            lblDatoNombre.setText(mascotaTemp.getNombre());
                            lblDatoEspecie.setText(mascotaTemp.getEspecie());
                            lblDatoRaza.setText(mascotaTemp.getRaza());
                            lblDatoSexo.setText(mascotaTemp.getSexo() != null ? mascotaTemp.getSexo() : "No especificado");
                            lblDatoEdad.setText(mascotaTemp.getEdadAproximada() + " años");
                            
                            // Mostrar panel de datos
                            panelDatosContainer.setVisible(true);
                            
                            // ✅ GUARDAR NUEVOS DATOS COMPARTIDOS
                            panelMain.setDatoCompartido("idMascota", idMascota);
                            panelMain.setDatoCompartido("nombreMascota", mascotaTemp.getNombre());
                            panelMain.setDatoCompartido("codigoFicha", codigoFicha);
                            
                            // ✅ HABILITAR BOTÓN HISTORIAL
                            btnHistorial.setEnabled(true);
                            
                            System.out.println("DEBUG: Nueva mascota cargada - ID: " + idMascota + ", Nombre: " + mascotaTemp.getNombre());
                            
                        } else {
                            JOptionPane.showMessageDialog(panelPrincipal, 
                                "No se encontraron datos de la mascota",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                            limpiarDatos(panelDatosContainer, txtCodigoFicha);
                        }
                    } else {
                        JOptionPane.showMessageDialog(panelPrincipal, 
                            "La ficha no tiene mascota asociada",
                            "Mascota no encontrada",
                            JOptionPane.WARNING_MESSAGE);
                        limpiarDatos(panelDatosContainer, txtCodigoFicha);
                    }
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal, 
                        "No se encontró ninguna ficha con ese código",
                        "Ficha no encontrada",
                        JOptionPane.WARNING_MESSAGE);
                    limpiarDatos(panelDatosContainer, txtCodigoFicha);
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panelPrincipal, 
                    "El código debe ser numérico",
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
        
        return panelPrincipal;
    }

    // ===== MÉTODOS AUXILIARES =====
    private JPanel crearPanelCampo(String etiqueta, JLabel lblDato) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setForeground(new Color(0, 150, 255));
        lblEtiqueta.setFont(new Font("Arial", Font.PLAIN, 18));
        lblEtiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        lblDato.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(lblEtiqueta);
        panel.add(Box.createVerticalStrut(8));
        panel.add(lblDato);
        
        return panel;
    }

    private JLabel crearLabelDato(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        return label;
    }

    private void limpiarDatos(JPanel panelDatos, CampoTextoPersonalizado txtCodigo) {
        panelDatos.setVisible(false);
        txtCodigo.setText("");
        idMascotaActual = -1;
        
        // ✅ LIMPIAR DATOS COMPARTIDOS
        panelMain.setDatoCompartido("idMascota", null);
        panelMain.setDatoCompartido("nombreMascota", null);
        panelMain.setDatoCompartido("codigoFicha", null);
    }
}