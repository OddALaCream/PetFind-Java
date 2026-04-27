package VETERINARIA;

import javax.swing.*;

import CLASES.Cuenta;

import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


public class Panel_Login extends JPanel {

    private Panel_Main panelMain;
    private Cuenta cuenta;

    public Panel_Login(Panel_Main panelMain) {
        this.panelMain = panelMain;
        this.cuenta = new Cuenta();
    }

    public JPanel Crear_Panel() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 0, 0));

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBackground(Color.BLACK);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.BLACK);
        JLabel lblTitulo = new JLabel("INICIAR SESIÓN:");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        panelTitulo.add(lblTitulo);
        panelIzquierdo.add(panelTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(Color.BLACK);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 20));

        CampoTextoPersonalizado txtUsuario = new CampoTextoPersonalizado("Usuario");
        CampoPasswordPersonalizado txtContrasenia = new CampoPasswordPersonalizado("Contraseña");

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 24));
        lblUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblContrasenia = new JLabel("Contraseña:");
        lblContrasenia.setForeground(Color.WHITE);
        lblContrasenia.setFont(new Font("Arial", Font.PLAIN, 24));
        lblContrasenia.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        panelBotones.setBackground(Color.BLACK);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnIngresar = BotonPersonalizado.crearBoton("Ingresar");
        btnIngresar.addActionListener(e -> {
            String usuario = txtUsuario.getText().trim();
            String contrasenia = new String(txtContrasenia.getPassword()).trim();
            
            if(usuario.isEmpty() || contrasenia.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar usuario y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int tipo = cuenta.validarCuenta(usuario, contrasenia);
                if(tipo == -1) {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idPersona = cuenta.obtenerIdPorUsuario(usuario);

                panelMain.setIdPersonaActual(idPersona);
                panelMain.setUsuarioActual(usuario);
                panelMain.setTipoActual(tipo);

                // 0=Administrador, 1=Veterinario, 2=Recepcionista
                if(tipo == 0) panelMain.mostrarPanel("cuenta");
                else if(tipo == 1) panelMain.mostrarPanel("veterinario");
                else if(tipo == 2) panelMain.mostrarPanel("recepcionista");

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al iniciar sesión:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al validar la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnCancelar = BotonPersonalizado.crearBoton("Cancelar");
        btnCancelar.addActionListener(e -> System.exit(0));


        panelBotones.add(btnIngresar);
        panelBotones.add(btnCancelar);


        panelCentro.add(lblUsuario);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtUsuario);
        panelCentro.add(Box.createVerticalStrut(15));
        panelCentro.add(lblContrasenia);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtContrasenia);
        panelCentro.add(Box.createVerticalStrut(30));
        panelCentro.add(panelBotones);

        panelIzquierdo.add(panelCentro, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setBackground(Color.BLACK);
        JButton btnVeterinario = BotonPersonalizado.crearBoton("Veterinario", RutasImagenes.VETERINARIO);
        btnVeterinario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(btnVeterinario);

        panel.add(panelIzquierdo);
        panel.add(panelDerecho);

        return panel;
    }
}