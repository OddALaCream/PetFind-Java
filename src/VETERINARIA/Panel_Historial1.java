package VETERINARIA;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Panel_Historial1 extends JPanel {

    private Panel_Main panelMain;

    public Panel_Historial1(Panel_Main panelMain) {
        this.panelMain = panelMain;
    }

    public JPanel Crear_Panel() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBackground(Color.BLACK);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.BLACK);
        JLabel lblTitulo = new JLabel("VER HISTORIAL MEDICO:");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        panelTitulo.add(lblTitulo);
        panelIzquierdo.add(panelTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(Color.BLACK);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 20)); 

  
        CampoTextoPersonalizado txtCodigo = new CampoTextoPersonalizado("Codigo");

        
        
        JLabel lblCodigo = new JLabel("Codigo:");
        lblCodigo.setForeground(Color.WHITE);
        lblCodigo.setFont(new Font("Arial", Font.PLAIN, 20));
        lblCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);

     

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); 
        panelBotones.setBackground(Color.BLACK);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT); 

        JButton btnFinalizar = BotonPersonalizado.crearBoton("Finalizar");
        JButton btnAtras = BotonPersonalizado.crearBoton("Atrás");
        
        btnFinalizar.addActionListener(e -> panelMain.mostrarPanel("historial2")); 
        btnAtras.addActionListener(e -> panelMain.mostrarPanel("veterinario")); 
        

        panelBotones.add(btnFinalizar);
        panelBotones.add(btnAtras);

        panelCentro.add(lblCodigo);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtCodigo);
        panelCentro.add(Box.createVerticalStrut(10));



        panelCentro.add(panelBotones);

        JScrollPane scrollIzquierdo = new JScrollPane(panelCentro);
        scrollIzquierdo.setBackground(Color.BLACK);
        scrollIzquierdo.setBorder(null);
        scrollIzquierdo.getViewport().setBackground(Color.BLACK);

        panelIzquierdo.add(scrollIzquierdo, BorderLayout.CENTER);

        ///////////////////////////////////////DERECHO
        

       
        // --------------------------------------------------------------------------------
        panel.add(panelIzquierdo);

        
        return panel;
    }

}