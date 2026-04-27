package VETERINARIA;

import java.awt.*;
import javax.swing.*;

import CLASES.HorarioDisponible;

public class Panel_Horario extends JPanel {

    private Panel_Main panelMain;
    private BD base_datos;
    private int idVeterinario; // Id del veterinario que inició sesión
    private HorarioDisponible horario;


    
    public Panel_Horario(Panel_Main panelMain) {
        this.panelMain = panelMain;
        this.base_datos = new BD();
        this.horario = new HorarioDisponible();
    }


    public JPanel Crear_Panel() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 0, 0));

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBackground(Color.BLACK);

        // Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.BLACK);
        JLabel lblTitulo = new JLabel("REGISTRO DE HORARIOS:");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        panelTitulo.add(lblTitulo);
        panelIzquierdo.add(panelTitulo, BorderLayout.NORTH);

        // Panel central
        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(Color.BLACK);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 20)); 

        CampoTextoPersonalizado txtFechaDisponible = new CampoTextoPersonalizado("Fecha Disponible");
        CampoTextoPersonalizado txtHorarioDisponible = new CampoTextoPersonalizado("Horario Disponible");

        JLabel lblFechaDisponible = new JLabel("Fecha Disponible:");
        lblFechaDisponible.setForeground(Color.WHITE);
        lblFechaDisponible.setFont(new Font("Arial", Font.PLAIN, 24));
        lblFechaDisponible.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblHorarioDisponible = new JLabel("Horario Disponible:");
        lblHorarioDisponible.setForeground(Color.WHITE);
        lblHorarioDisponible.setFont(new Font("Arial", Font.PLAIN, 24));
        lblHorarioDisponible.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); 
        panelBotones.setBackground(Color.BLACK);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT); 

        JButton btnGuardar = BotonPersonalizado.crearBoton("Guardar");
        btnGuardar.addActionListener(e -> {
            int idVeterinario = panelMain.getIdPersonaActual(); // OBTIENE EL ID DEL VETERINARIO LOGUEADO
            if(idVeterinario <= 0) {
                JOptionPane.showMessageDialog(panelMain, 
                    "Error: No hay veterinario logueado.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insertar en la BD
            horario.insertarHorarios(idVeterinario, txtFechaDisponible.getText(), txtHorarioDisponible.getText());
            JOptionPane.showMessageDialog(panelMain, 
                "Horario registrado exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

            panelMain.mostrarPanel("veterinario");
        });


        JButton btnCancelar = BotonPersonalizado.crearBoton("Cancelar");
        btnCancelar.addActionListener(e -> panelMain.mostrarPanel("login")); 

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        // Agregar campos al panel
        panelCentro.add(lblFechaDisponible);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtFechaDisponible);
        panelCentro.add(Box.createVerticalStrut(15));
        panelCentro.add(lblHorarioDisponible);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtHorarioDisponible);
        panelCentro.add(Box.createVerticalStrut(30));
        panelCentro.add(panelBotones);

        panelIzquierdo.add(panelCentro, BorderLayout.CENTER);

        // Panel derecho con botón de regreso
        JPanel panelDerecho = new JPanel();
        panelDerecho.setBackground(Color.BLACK);
        JButton btnVeterinario = BotonPersonalizado.crearBoton("Veterinario", RutasImagenes.VETERINARIO);
        btnVeterinario.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVeterinario.addActionListener(e -> panelMain.mostrarPanel("login"));
        panelDerecho.add(btnVeterinario);

        panel.add(panelIzquierdo);
        panel.add(panelDerecho);

        return panel;
    }

}
