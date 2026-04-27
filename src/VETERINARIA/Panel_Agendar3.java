package VETERINARIA;

import java.awt.*;
import javax.swing.*;

public class Panel_Agendar3 {

    private Panel_Main panelMain;

    public Panel_Agendar3(Panel_Main panelMain) {
        this.panelMain = panelMain;
    }

    public JPanel Crear_Panel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);


        // ---------- TÍTULO ----------
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitulo.setBackground(Color.BLACK);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JLabel lblTitulo = new JLabel("PROPIETARIO");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));

        panelTitulo.add(lblTitulo);


        // ---------- BOTONES CENTRALES ----------
        JPanel panelCentral = new JPanel(new GridLayout(2, 1, 0, 40));
        panelCentral.setBackground(Color.BLACK);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(80, 200, 80, 200));

        JButton btnNuevo = new JButton("Propietario Nuevo");
        btnNuevo.setFont(new Font("Arial", Font.BOLD, 22));
        btnNuevo.setBackground(new Color(40, 167, 69));
        btnNuevo.setForeground(Color.WHITE);
        btnNuevo.setFocusPainted(false);
        btnNuevo.setPreferredSize(new Dimension(300, 80));

        btnNuevo.addActionListener(e -> {
            panelMain.mostrarPanel("registrar1"); 
        });

        JButton btnRegistrado = new JButton("Propietario Registrado");
        btnRegistrado.setFont(new Font("Arial", Font.BOLD, 22));
        btnRegistrado.setBackground(new Color(0, 123, 255));
        btnRegistrado.setForeground(Color.WHITE);
        btnRegistrado.setFocusPainted(false);
        btnRegistrado.setPreferredSize(new Dimension(300, 80));

        btnRegistrado.addActionListener(e -> {
            panelMain.mostrarPanel("agendar4"); 
        });

        panelCentral.add(btnNuevo);
        panelCentral.add(btnRegistrado);


        // ---------- REGRESAR ----------
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(Color.BLACK);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(40, 20, 60, 20));

        JButton btnAtras = new JButton("Cancelar");
        btnAtras.setFont(new Font("Arial", Font.BOLD, 18));
        btnAtras.setBackground(new Color(220, 53, 69));
        btnAtras.setForeground(Color.WHITE);
        btnAtras.setFocusPainted(false);
        btnAtras.setPreferredSize(new Dimension(180, 50));

        btnAtras.addActionListener(e -> {
            panelMain.mostrarPanel("agendar2");
        });

        panelInferior.add(btnAtras);


        // ---------- ARMADO FINAL ----------
        panel.add(panelTitulo, BorderLayout.NORTH);
        panel.add(panelCentral, BorderLayout.CENTER);
        panel.add(panelInferior, BorderLayout.SOUTH);

        return panel;
    }
}
