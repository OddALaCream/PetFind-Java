package VETERINARIA;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import javax.swing.*;

import CLASES.Ficha;
import CLASES.Mascota;
import CLASES.Propietario;

public class Panel_Agendar5 extends JPanel {

    private Panel_Main panelMain;
    private int idPropietarioActual = -1;
    private Ficha ficha;
    private Propietario propietario;
    
    Mascota mascota;

    public Panel_Agendar5(Panel_Main panelMain) {
        this.panelMain = panelMain;
        this.ficha = new Ficha();
        this.mascota = new Mascota();
        this.propietario = new Propietario();
    }

    public JPanel Crear_Panel() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(0, 20));
        panelPrincipal.setBackground(Color.BLACK);

        // ------------------- PANEL ARRIBA -------------------
        JPanel panelArriba = new JPanel();
        panelArriba.setBackground(Color.BLACK);
        JLabel lblTitulo = new JLabel("AGREGAR NUEVA MASCOTA");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        panelArriba.add(lblTitulo);
        panelPrincipal.add(panelArriba, BorderLayout.NORTH);

        // ------------------- PANEL CENTRO -------------------
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BorderLayout(0, 20));
        panelCentro.setBackground(Color.BLACK);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 100, 50, 100));

        // Info del propietario
        JPanel panelInfoPropietario = new JPanel();
        panelInfoPropietario.setBackground(new Color(30, 30, 30));
        panelInfoPropietario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 255), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel lblInfoPropietario = new JLabel("Propietario: Cargando...");
        lblInfoPropietario.setForeground(new Color(0, 150, 255));
        lblInfoPropietario.setFont(new Font("Arial", Font.BOLD, 18));
        panelInfoPropietario.add(lblInfoPropietario);

        panelCentro.add(panelInfoPropietario, BorderLayout.NORTH);

        // Formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setBackground(Color.BLACK);
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        Font fontLabel = new Font("Arial", Font.PLAIN, 18);

        // Nombre
        JLabel lblNombre = new JLabel("Nombre de la Mascota:");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(fontLabel);
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        CampoTextoPersonalizado txtNombre = new CampoTextoPersonalizado("Nombre");
        txtNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtNombre.setMaximumSize(new Dimension(500, 35));

        // Especie
        JLabel lblEspecie = new JLabel("Especie:");
        lblEspecie.setForeground(Color.WHITE);
        lblEspecie.setFont(fontLabel);
        lblEspecie.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] especies = {"Perro", "Gato", "Ave", "Roedor", "Reptil", "Otro"};
        JComboBox<String> cbEspecie = new JComboBox<>(especies);
        cbEspecie.setFont(new Font("Arial", Font.PLAIN, 16));
        cbEspecie.setBackground(Color.WHITE);
        cbEspecie.setAlignmentX(Component.LEFT_ALIGNMENT);
        cbEspecie.setMaximumSize(new Dimension(500, 35));

        // Raza
        JLabel lblRaza = new JLabel("Raza:");
        lblRaza.setForeground(Color.WHITE);
        lblRaza.setFont(fontLabel);
        lblRaza.setAlignmentX(Component.LEFT_ALIGNMENT);

        CampoTextoPersonalizado txtRaza = new CampoTextoPersonalizado("Raza");
        txtRaza.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtRaza.setMaximumSize(new Dimension(500, 35));

        // Sexo
        JLabel lblSexo = new JLabel("Sexo:");
        lblSexo.setForeground(Color.WHITE);
        lblSexo.setFont(fontLabel);
        lblSexo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel panelSexo = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        panelSexo.setBackground(Color.BLACK);
        panelSexo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelSexo.setMaximumSize(new Dimension(500, 40));

        JRadioButton rbMacho = new JRadioButton("Macho");
        rbMacho.setForeground(Color.WHITE);
        rbMacho.setBackground(Color.BLACK);
        rbMacho.setFont(new Font("Arial", Font.PLAIN, 16));

        JRadioButton rbHembra = new JRadioButton("Hembra");
        rbHembra.setForeground(Color.WHITE);
        rbHembra.setBackground(Color.BLACK);
        rbHembra.setFont(new Font("Arial", Font.PLAIN, 16));

        ButtonGroup grupoSexo = new ButtonGroup();
        grupoSexo.add(rbMacho);
        grupoSexo.add(rbHembra);

        panelSexo.add(rbMacho);
        panelSexo.add(rbHembra);

        // Edad
        JLabel lblEdad = new JLabel("Edad Aproximada (años):");
        lblEdad.setForeground(Color.WHITE);
        lblEdad.setFont(fontLabel);
        lblEdad.setAlignmentX(Component.LEFT_ALIGNMENT);

        CampoTextoPersonalizado txtEdad = new CampoTextoPersonalizado("Edad");
        txtEdad.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtEdad.setMaximumSize(new Dimension(500, 35));

        // Agregar al formulario
        panelFormulario.add(lblNombre);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(txtNombre);
        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(lblEspecie);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(cbEspecie);
        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(lblRaza);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(txtRaza);
        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(lblSexo);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(panelSexo);
        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(lblEdad);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(txtEdad);

        JScrollPane scrollFormulario = new JScrollPane(panelFormulario);
        scrollFormulario.setBackground(Color.BLACK);
        scrollFormulario.setBorder(null);
        scrollFormulario.getViewport().setBackground(Color.BLACK);

        panelCentro.add(scrollFormulario, BorderLayout.CENTER);

        // ===== BOTONES =====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelBotones.setBackground(Color.BLACK);

        JButton btnGuardarYRegistrarFicha = BotonPersonalizado.crearBoton("Guardar y Registrar Ficha");
        JButton btnCancelar = BotonPersonalizado.crearBoton("Cancelar");

        btnCancelar.addActionListener(e -> {
            limpiarFormulario(txtNombre, txtRaza, txtEdad, cbEspecie, grupoSexo);
            panelMain.mostrarPanel("agendar4");
        });

        btnGuardarYRegistrarFicha.addActionListener(e -> {
            try {
                // Validar campos
                String nombre = txtNombre.getText().trim();
                String especie = (String) cbEspecie.getSelectedItem();
                String raza = txtRaza.getText().trim();
                String edadStr = txtEdad.getText().trim();

                if (nombre.isEmpty() || raza.isEmpty() || edadStr.isEmpty()) {
                    JOptionPane.showMessageDialog(panelPrincipal,
                        "Por favor complete todos los campos",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!rbMacho.isSelected() && !rbHembra.isSelected()) {
                    JOptionPane.showMessageDialog(panelPrincipal,
                        "Por favor seleccione el sexo",
                        "Campo faltante",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int edad = Integer.parseInt(edadStr);
                if (edad < 0 || edad > 50) {
                    JOptionPane.showMessageDialog(panelPrincipal,
                        "La edad debe estar entre 0 y 50 años",
                        "Edad inválida",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String sexo = rbMacho.isSelected() ? "Macho" : "Hembra";

                // Obtener ID del propietario
                Object objIdProp = panelMain.getDatoCompartido("idPropietarioParaMascota");
                if (objIdProp == null) {
                    JOptionPane.showMessageDialog(panelPrincipal,
                        "Error: No se encontró el propietario",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idPropietario = (Integer) objIdProp;

                // Registrar mascota y obtener su ID
                BD bd = new BD();
                int idMascota = mascota.registrarMascotaConRetorno(idPropietario, nombre, especie, raza, sexo, edad);

                if (idMascota == -1) {
                    JOptionPane.showMessageDialog(panelPrincipal,
                        "Error al registrar la mascota",
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

                // Registrar la ficha
                ficha.registrarFicha(idRecepcionista, codigoFicha, fechaSQL, idMascota);

                // Obtener nombre del propietario
                String nombrePropietario = propietario.obtenerNombrePropietario(idPropietario);

                // Guardar datos para el panel de ficha
                panelMain.setDatoCompartido("codigoFicha", codigoFicha);
                panelMain.setDatoCompartido("fechaFicha", fechaActual);
                panelMain.setDatoCompartido("idMascota", idMascota);
                panelMain.setDatoCompartido("nombreMascota", nombre);
                panelMain.setDatoCompartido("especieMascota", especie);
                panelMain.setDatoCompartido("nombrePropietario", nombrePropietario);

                // Mensaje de éxito
                JOptionPane.showMessageDialog(panelPrincipal,
                    "Mascota registrada exitosamente\nFicha creada con código: " + codigoFicha,
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

                // Limpiar y mostrar ficha
                limpiarFormulario(txtNombre, txtRaza, txtEdad, cbEspecie, grupoSexo);
                panelMain.mostrarPanel("ficha");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panelPrincipal,
                    "La edad debe ser un número válido",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panelPrincipal,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        panelBotones.add(btnGuardarYRegistrarFicha);
        panelBotones.add(btnCancelar);

        panelCentro.add(panelBotones, BorderLayout.SOUTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // Cargar datos del propietario al mostrar el panel
        actualizarInfoPropietario(lblInfoPropietario);

        return panelPrincipal;
    }

    /**
     * Actualiza la información del propietario cuando se carga el panel
     */
    private void actualizarInfoPropietario(JLabel lblInfo) {
        Object objIdProp = panelMain.getDatoCompartido("idPropietarioParaMascota");
        Object objNombreProp = panelMain.getDatoCompartido("nombrePropietarioParaMascota");

        if (objIdProp != null && objNombreProp != null) {
            idPropietarioActual = (Integer) objIdProp;
            String nombrePropietario = (String) objNombreProp;
            lblInfo.setText("Propietario: " + nombrePropietario);
        } else {
            lblInfo.setText("Propietario: No seleccionado");
        }
    }

    /**
     * Limpia el formulario
     */
    private void limpiarFormulario(CampoTextoPersonalizado txtNombre,
                                   CampoTextoPersonalizado txtRaza,
                                   CampoTextoPersonalizado txtEdad,
                                   JComboBox<String> cbEspecie,
                                   ButtonGroup grupoSexo) {
        txtNombre.setText("");
        txtRaza.setText("");
        txtEdad.setText("");
        cbEspecie.setSelectedIndex(0);
        grupoSexo.clearSelection();
    }
}