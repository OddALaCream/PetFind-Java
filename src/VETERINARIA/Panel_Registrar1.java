package VETERINARIA;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;

import CLASES.Ficha;
import CLASES.Propietario;
import CLASES.Mascota;

public class Panel_Registrar1 extends JPanel {
    private Panel_Main panelMain;
    
    private Propietario propietario;
    private Ficha ficha;
    private Mascota mascota;
    
    public Panel_Registrar1(Panel_Main panelMain) {
        this.panelMain = panelMain;
        this.propietario = new Propietario();
        this.ficha = new Ficha();
    }
    
    public JPanel Crear_Panel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 0, 0));
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBackground(Color.BLACK);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.BLACK);
        JLabel lblTitulo = new JLabel("REGISTRAR CLIENTE:");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        panelTitulo.add(lblTitulo);
        panelIzquierdo.add(panelTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(Color.BLACK);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 20)); 

        CampoTextoPersonalizado txtpropietario = new CampoTextoPersonalizado("Nombre del propietario");
        CampoTextoPersonalizado txtapellidos = new CampoTextoPersonalizado("Apellidos del propietario");
        CampoTextoPersonalizado txtCI = new CampoTextoPersonalizado("CI");
        CampoTextoPersonalizado txtTelefono = new CampoTextoPersonalizado("Teléfono");
        CampoTextoPersonalizado txtFechaNacimiento = new CampoTextoPersonalizado("Fecha de nacimiento");

        JLabel lblpropietario = new JLabel("Nombre del propietario:");
        lblpropietario.setForeground(Color.WHITE);
        lblpropietario.setFont(new Font("Arial", Font.PLAIN, 20));
        lblpropietario.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblapellidos = new JLabel("Apellidos del propietario:");
        lblapellidos.setForeground(Color.WHITE);
        lblapellidos.setFont(new Font("Arial", Font.PLAIN, 20));
        lblapellidos.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblCI = new JLabel("CI del propietario:");
        lblCI.setForeground(Color.WHITE);
        lblCI.setFont(new Font("Arial", Font.PLAIN, 20));
        lblCI.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setForeground(Color.WHITE);
        lblTelefono.setFont(new Font("Arial", Font.PLAIN, 20));
        lblTelefono.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento:");
        lblFechaNacimiento.setForeground(Color.WHITE);
        lblFechaNacimiento.setFont(new Font("Arial", Font.PLAIN, 20));
        lblFechaNacimiento.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); 
        panelBotones.setBackground(Color.BLACK);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT); 

        panelCentro.add(lblpropietario);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtpropietario);
        panelCentro.add(Box.createVerticalStrut(10));
        panelCentro.add(lblapellidos);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtapellidos);
        panelCentro.add(Box.createVerticalStrut(10));
        panelCentro.add(lblCI);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtCI);
        panelCentro.add(Box.createVerticalStrut(10));
        panelCentro.add(lblTelefono);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtTelefono);
        panelCentro.add(Box.createVerticalStrut(10));
        panelCentro.add(lblFechaNacimiento);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtFechaNacimiento);
        panelCentro.add(Box.createVerticalStrut(20));
        panelCentro.add(panelBotones);

        // Agregar ScrollPane al panel izquierdo
        JScrollPane scrollIzquierdo = new JScrollPane(panelCentro);
        scrollIzquierdo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollIzquierdo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollIzquierdo.setBorder(null);
        scrollIzquierdo.getVerticalScrollBar().setUnitIncrement(16);
        scrollIzquierdo.setBackground(Color.BLACK);
        scrollIzquierdo.getViewport().setBackground(Color.BLACK);

        panelIzquierdo.add(scrollIzquierdo, BorderLayout.CENTER);

        // Panel Derecho
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(Color.BLACK);

        JPanel panelCentroDer = new JPanel();
        panelCentroDer.setBackground(Color.BLACK);
        panelCentroDer.setLayout(new BoxLayout(panelCentroDer, BoxLayout.Y_AXIS));
        panelCentroDer.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 20));

        CampoTextoPersonalizado txtNombreMascota = new CampoTextoPersonalizado("Nombre de la mascota");
        CampoTextoPersonalizado txtEspecie = new CampoTextoPersonalizado("Especie");
        CampoTextoPersonalizado txtRaza = new CampoTextoPersonalizado("Raza");
        CampoTextoPersonalizado txtSexo = new CampoTextoPersonalizado("Sexo");
        CampoTextoPersonalizado txtEdadAproximada = new CampoTextoPersonalizado("Edad aproximada");

        JLabel lblNombreMascota = new JLabel("Nombre de la mascota:");
        lblNombreMascota.setForeground(Color.WHITE);
        lblNombreMascota.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNombreMascota.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblEspecie = new JLabel("Especie:");
        lblEspecie.setForeground(Color.WHITE);
        lblEspecie.setFont(new Font("Arial", Font.PLAIN, 20));
        lblEspecie.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblRaza = new JLabel("Raza:");
        lblRaza.setForeground(Color.WHITE);
        lblRaza.setFont(new Font("Arial", Font.PLAIN, 20));
        lblRaza.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSexo = new JLabel("Sexo:");
        lblSexo.setForeground(Color.WHITE);
        lblSexo.setFont(new Font("Arial", Font.PLAIN, 20));
        lblSexo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblEdadAproximada = new JLabel("Edad aproximada:");
        lblEdadAproximada.setForeground(Color.WHITE);
        lblEdadAproximada.setFont(new Font("Arial", Font.PLAIN, 20));
        lblEdadAproximada.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelCentroDer.add(lblNombreMascota);
        panelCentroDer.add(Box.createVerticalStrut(5));
        panelCentroDer.add(txtNombreMascota);
        panelCentroDer.add(Box.createVerticalStrut(10));

        panelCentroDer.add(lblEspecie);
        panelCentroDer.add(Box.createVerticalStrut(5));
        panelCentroDer.add(txtEspecie);
        panelCentroDer.add(Box.createVerticalStrut(10));

        panelCentroDer.add(lblRaza);
        panelCentroDer.add(Box.createVerticalStrut(5));
        panelCentroDer.add(txtRaza);
        panelCentroDer.add(Box.createVerticalStrut(10));

        panelCentroDer.add(lblSexo);
        panelCentroDer.add(Box.createVerticalStrut(5));
        panelCentroDer.add(txtSexo);
        panelCentroDer.add(Box.createVerticalStrut(10));

        panelCentroDer.add(lblEdadAproximada);
        panelCentroDer.add(Box.createVerticalStrut(5));
        panelCentroDer.add(txtEdadAproximada);

        panelDerecho.add(panelCentroDer, BorderLayout.CENTER);
        
        // ===== BOTÓN REGISTRAR CON NUEVO SISTEMA =====
        JButton btnIngresar = BotonPersonalizado.crearBoton("Registrar");
        btnIngresar.addActionListener(e -> {
            try {
                String nombreProp = txtpropietario.getText();
                String apellidos = txtapellidos.getText();
                String CI = txtCI.getText();
                String telefono = txtTelefono.getText();
                String fechaNac = txtFechaNacimiento.getText();

                String nombreMasc = txtNombreMascota.getText();
                String especie = txtEspecie.getText();
                String raza = txtRaza.getText();
                String sexo = txtSexo.getText();
                int edad = Integer.parseInt(txtEdadAproximada.getText());

                BD bd = new BD();

                // 1. Registrar propietario CON TELÉFONO
                int idPropietario = propietario.registrarPropietario(nombreProp, apellidos, CI, telefono, fechaNac);

                if (idPropietario != -1) {
                    // 2. Registrar mascota y obtener su ID
                    int idMascota = mascota.registrarMascotaConRetorno(idPropietario, nombreMasc, especie, raza, sexo, edad);

                    // 3. Generar ficha
                    int codigoFicha = ficha.generarCodigoFicha();
                    java.sql.Date fechaHoy = java.sql.Date.valueOf(LocalDate.now());

                    // 4. Registrar ficha en la base de datos con id_mascota
                    int idRecep = panelMain.getIdPersonaActual();
                    ficha.registrarFicha(idRecep, codigoFicha, fechaHoy, idMascota);

                    // ===== GUARDAR DATOS EN EL SISTEMA COMPARTIDO =====
                    System.out.println("=== GUARDANDO DATOS ===");
                    System.out.println("Código Ficha: " + codigoFicha);
                    System.out.println("Fecha: " + LocalDate.now());
                    System.out.println("Propietario: " + nombreProp + " " + apellidos);
                    System.out.println("Mascota: " + nombreMasc);
                    System.out.println("Especie: " + especie);
                    System.out.println("ID Mascota: " + idMascota);
                    System.out.println("ID Propietario: " + idPropietario);
                    
                    panelMain.setDatoCompartido("codigoFicha", codigoFicha);
                    panelMain.setDatoCompartido("fechaFicha", LocalDate.now());
                    panelMain.setDatoCompartido("nombrePropietario", nombreProp + " " + apellidos);
                    panelMain.setDatoCompartido("nombreMascota", nombreMasc);
                    panelMain.setDatoCompartido("especieMascota", especie);
                    panelMain.setDatoCompartido("idMascota", idMascota);
                    panelMain.setDatoCompartido("idPropietario", idPropietario);
                    
                    System.out.println("=== DATOS GUARDADOS ===");

                    // 6. Mostrar mensaje y cambiar de panel
                    JOptionPane.showMessageDialog(panel, "Cliente, mascota y ficha registrados correctamente");
                    panelMain.mostrarPanel("ficha");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panel, "Error al registrar: " + ex.getMessage());
            }
        });

        panelBotones.add(btnIngresar);

        panel.add(panelIzquierdo);
        panel.add(panelDerecho);

        return panel;
    }
}