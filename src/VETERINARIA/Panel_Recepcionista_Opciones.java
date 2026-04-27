package VETERINARIA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel_Recepcionista_Opciones {

    private Panel_Main panelMain;

    public Panel_Recepcionista_Opciones(Panel_Main panelMain) {
        this.panelMain = panelMain;
    }

    public JPanel Crear_Panel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.BLACK);

        JLabel lblTitulo = new JLabel("Elige una opcion:");
        lblTitulo.setForeground(new Color(0, 255, 255));
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        panelTitulo.add(lblTitulo);
        panel.add(panelTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.BLACK);
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
        
        
        JButton btnRegistrar = BotonPersonalizado.crearBoton(
                "Registrar Cita", RutasImagenes.VETERINARIO
        );
        JButton btnValidar = BotonPersonalizado.crearBoton(
                "Buscar Dueño", RutasImagenes.VETERINARIO
        );


     
        btnRegistrar.addActionListener(e -> panelMain.mostrarPanel("agendar2"));
        btnValidar.addActionListener(e -> panelMain.mostrarPanel("buscar"));

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnValidar);
        panel.add(panelBotones, BorderLayout.CENTER);

        return panel;
    }
}
