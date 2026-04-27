package VETERINARIA;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import CLASES.HorarioDisponible;

public class Panel_Agendar2 {

    private Panel_Main panelMain;
    private BD bd;
    private HorarioDisponible horario;

    public Panel_Agendar2(Panel_Main panelMain) {
        this.panelMain = panelMain;
        this.horario = new HorarioDisponible();
        bd = new BD();
    }

    public JPanel Crear_Panel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);


        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitulo.setBackground(Color.BLACK);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        JLabel lblTitulo = new JLabel("AGENDAR:");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        panelTitulo.add(lblTitulo);

 
        JLabel lblHorarios = new JLabel("Horarios disponibles:");
        lblHorarios.setForeground(Color.WHITE);
        lblHorarios.setFont(new Font("Arial", Font.PLAIN, 24));
        lblHorarios.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        String[] columnas = {"#", "Médico Responsable", "Fecha", "Horario"}; // columnas visibles

        Object[][] datos = horario.obtenerHorarios(); // incluye id real en la columna 0
        Object[][] datosTabla = new Object[datos.length][4];

        for (int i = 0; i < datos.length; i++) {
            datosTabla[i][0] = i + 1;            // índice visual
            datosTabla[i][1] = datos[i][1];    // médico
            datosTabla[i][2] = datos[i][2];    // fecha
            datosTabla[i][3] = datos[i][3];    // horario
        }



        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla1 = new JTable(modelo);
        tabla1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla1.setRowHeight(30);
        tabla1.setBackground(Color.BLACK);
        tabla1.setForeground(Color.WHITE);
        tabla1.setFont(new Font("Arial", Font.PLAIN, 16));
        tabla1.getTableHeader().setBackground(Color.BLACK);
        tabla1.getTableHeader().setForeground(Color.WHITE);
        tabla1.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        tabla1.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabla1.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla1.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla1.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scrollTabla = new JScrollPane(tabla1);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        scrollTabla.setBackground(Color.BLACK);
        scrollTabla.getViewport().setBackground(Color.BLACK);


        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(Color.BLACK);
        panelCentral.add(lblHorarios, BorderLayout.NORTH);
        panelCentral.add(scrollTabla, BorderLayout.CENTER);


        JPanel panelInferiorContenedor = new JPanel(new BorderLayout());
        panelInferiorContenedor.setBackground(Color.BLACK);
        

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 30));
        panelBotones.setBackground(Color.BLACK);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 20, 60, 20));

  
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 18));
        btnCancelar.setBackground(new Color(220, 53, 69));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setPreferredSize(new Dimension(180, 50));
        btnCancelar.addActionListener(e -> {
            panelMain.mostrarPanel("recepcionista");
        });


        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 18));
        btnConfirmar.setBackground(new Color(40, 167, 69));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setPreferredSize(new Dimension(180, 50));
        btnConfirmar.addActionListener(e -> {
            int filaSeleccionada = tabla1.getSelectedRow();
            if (filaSeleccionada != -1) {
                String medico = (String) tabla1.getValueAt(filaSeleccionada, 1);
                String fecha = (String) tabla1.getValueAt(filaSeleccionada, 2);
                String horarioString = (String) tabla1.getValueAt(filaSeleccionada, 3);

                JOptionPane.showMessageDialog(panel, 
                    "Cita confirmada con:\n" + medico + "\nFecha: " + fecha + "\nHorario: " + horarioString,
                    "Confirmación",
                    JOptionPane.INFORMATION_MESSAGE);

                horario.eliminarHorario(medico, fecha, horarioString);


                modelo.removeRow(filaSeleccionada);

                panelMain.mostrarPanel("agendar3");
            } else {
                JOptionPane.showMessageDialog(panel, 
                    "Por favor, seleccione un horario",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            }
        });



        panelBotones.add(btnCancelar);
        panelBotones.add(btnConfirmar);
        
        panelInferiorContenedor.add(panelBotones, BorderLayout.CENTER);


        panel.add(panelTitulo, BorderLayout.NORTH);
        panel.add(panelCentral, BorderLayout.CENTER);
        panel.add(panelInferiorContenedor, BorderLayout.SOUTH);

        return panel;
    }
}