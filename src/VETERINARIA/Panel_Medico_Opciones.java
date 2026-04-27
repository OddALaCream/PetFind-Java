package VETERINARIA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel_Medico_Opciones extends JPanel {
    private Panel_Main panelMain;

    public Panel_Medico_Opciones(Panel_Main panelMain) {
        this.panelMain = panelMain;
    }

    public JPanel Crear_Panel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.BLACK);
        JLabel lblTitulo = new JLabel("Iniciar sesión como:");
        lblTitulo.setForeground(new Color(0, 255, 255));
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        panelTitulo.add(lblTitulo);
        panel.add(panelTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(Color.BLACK);
        
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.BLACK);
        panelBotones.setLayout(new GridLayout(1, 3, 20, 0));
        panelBotones.setPreferredSize(new Dimension(900, 200));

        JButton btnVeterinario = BotonPersonalizado.crearBoton(
            "Registrar Diagnóstico",
            RutasImagenes.VETERINARIO
        );

        JButton btnRecepcionista = BotonPersonalizado.crearBoton(
            "Ver historial",
            RutasImagenes.VETERINARIO
        );

        JButton btnRegistrarHorario = BotonPersonalizado.crearBoton(
            "Registrar Horario",
            RutasImagenes.VETERINARIO
        );

        btnRecepcionista.addActionListener(e -> panelMain.mostrarPanel("historial2"));
        btnVeterinario.addActionListener(e -> panelMain.mostrarPanel("diagnostico1"));
        btnRegistrarHorario.addActionListener(e -> panelMain.mostrarPanel("horario"));

        panelBotones.add(btnRecepcionista);
        panelBotones.add(btnVeterinario);
        panelBotones.add(btnRegistrarHorario);

        panelCentro.add(panelBotones, BorderLayout.CENTER);
        panel.add(panelCentro, BorderLayout.CENTER);

        return panel;
    }
}