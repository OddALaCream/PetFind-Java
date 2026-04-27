package VETERINARIA;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import CLASES.Administrador;
import CLASES.Cuenta;
import CLASES.Persona;
import CLASES.Recepcionista;
import CLASES.Veterinario;

public class Panel_Cuenta extends JPanel {

    private Panel_Main panelMain;
    private Cuenta cuenta;
    private Veterinario veterinario;
    private Recepcionista recepcionista;
	private Persona persona;
	private Administrador administrador;

    public Panel_Cuenta(Panel_Main panelMain) {
        this.panelMain = panelMain;
        this.cuenta = new Cuenta();
        this.veterinario = new Veterinario();
        this.recepcionista = new Recepcionista();
        this.persona = new Persona();
        this.administrador = new Administrador();
        
    }

    public JPanel Crear_Panel() {
        // Panel principal con 2 columnas
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));

        // Panel izquierdo - Crear cuenta
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBackground(Color.BLACK);

        // Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.BLACK);
        JLabel lblTitulo = new JLabel("CREAR CUENTA:");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        panelTitulo.add(lblTitulo);
        panelIzquierdo.add(panelTitulo, BorderLayout.NORTH);

        // Panel central
        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(Color.BLACK);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Campos de texto
        CampoTextoPersonalizado txtNombre = new CampoTextoPersonalizado("Nombre");
        CampoTextoPersonalizado txtApellidoP = new CampoTextoPersonalizado("Apellido Paterno");
        CampoTextoPersonalizado txtApellidoM = new CampoTextoPersonalizado("Apellido Materno");
        CampoTextoPersonalizado txtCI = new CampoTextoPersonalizado("CI");
        CampoTextoPersonalizado txtTelefono = new CampoTextoPersonalizado("Teléfono");
        CampoTextoPersonalizado txtFechaNacimiento = new CampoTextoPersonalizado("Fecha de nacimiento (YYYY-MM-DD)");
        CampoTextoPersonalizado txtUsuario = new CampoTextoPersonalizado("Usuario");
        CampoPasswordPersonalizado txtContrasenia = new CampoPasswordPersonalizado("Contraseña");
        CampoPasswordPersonalizado txtRepetirContrasenia = new CampoPasswordPersonalizado("Repetir contraseña");

        JLabel lblTipo = new JLabel("Tipo de usuario:");
        lblTipo.setForeground(Color.WHITE);
        lblTipo.setFont(new Font("Arial", Font.PLAIN, 20));
        lblTipo.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] tipos = {"Administrador", "Veterinario", "Recepcionista"};
        JComboBox<String> comboTipo = new JComboBox<>(tipos);
        comboTipo.setMaximumSize(new Dimension(300, 35));
        comboTipo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Agregar campos al panel con espaciado reducido
        panelCentro.add(crearLabel("Nombre:"));
        panelCentro.add(txtNombre);
        panelCentro.add(Box.createVerticalStrut(10));

        panelCentro.add(crearLabel("Apellido Paterno:"));
        panelCentro.add(txtApellidoP);
        panelCentro.add(Box.createVerticalStrut(10));

        panelCentro.add(crearLabel("Apellido Materno:"));
        panelCentro.add(txtApellidoM);
        panelCentro.add(Box.createVerticalStrut(10));

        panelCentro.add(crearLabel("CI:"));
        panelCentro.add(txtCI);
        panelCentro.add(Box.createVerticalStrut(10));
        
        panelCentro.add(crearLabel("Teléfono:"));
        panelCentro.add(txtTelefono);
        panelCentro.add(Box.createVerticalStrut(10));

        panelCentro.add(crearLabel("Fecha de nacimiento:"));
        panelCentro.add(txtFechaNacimiento);
        panelCentro.add(Box.createVerticalStrut(10));

        panelCentro.add(crearLabel("Usuario:"));
        panelCentro.add(txtUsuario);
        panelCentro.add(Box.createVerticalStrut(10));

        panelCentro.add(crearLabel("Contraseña:"));
        panelCentro.add(txtContrasenia);
        panelCentro.add(Box.createVerticalStrut(10));

        panelCentro.add(crearLabel("Repetir contraseña:"));
        panelCentro.add(txtRepetirContrasenia);
        panelCentro.add(Box.createVerticalStrut(10));

        panelCentro.add(lblTipo);
        panelCentro.add(comboTipo);
        panelCentro.add(Box.createVerticalStrut(20));

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        panelBotones.setBackground(Color.BLACK);

        JButton btnRegistrar = BotonPersonalizado.crearBoton("Registrar");
        btnRegistrar.addActionListener(e -> {
            String pass1 = new String(txtContrasenia.getPassword());
            String pass2 = new String(txtRepetirContrasenia.getPassword());

            // Validar campos vacíos
            if (txtNombre.getText().trim().isEmpty() || txtApellidoP.getText().trim().isEmpty() ||
                txtCI.getText().trim().isEmpty() || txtUsuario.getText().trim().isEmpty() ||
                pass1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar contraseñas
            if (!pass1.equals(pass2)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (pass1.length() < 6) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Crear objeto Persona
                Persona persona = new Persona();
                persona.setNombre(txtNombre.getText().trim());
                persona.setApellidoPaterno(txtApellidoP.getText().trim());
                persona.setApellidoMaterno(txtApellidoM.getText().trim());
                persona.setCi(Integer.parseInt(txtCI.getText().trim()));
                persona.setTelefono(txtTelefono.getText().trim());
                persona.setFechaNacimiento(txtFechaNacimiento.getText().trim());

                // Insertar persona en la BD
                int idPersona = persona.insertarPersona(persona);

                // Cifrar contraseña
                String contrasenaCifrada = cifrarContrasenia(pass1);

                // Crear cuenta con contraseña cifrada
                Cuenta cuenta = new Cuenta();
                cuenta.setIdPersona(idPersona);
                cuenta.setUsuario(txtUsuario.getText().trim());
                cuenta.setContrasenia(contrasenaCifrada);
                
                // Determinar tipo de usuario: 0=Admin, 1=Veterinario, 2=Recepcionista
                String tipoSeleccionado = (String) comboTipo.getSelectedItem();
                int tipo = 0;
                if (tipoSeleccionado.equals("Veterinario")) {
                    tipo = 1;
                } else if (tipoSeleccionado.equals("Recepcionista")) {
                    tipo = 2;
                }
                cuenta.setTipo(tipo);

                cuenta.insertarCuenta(cuenta);

                // Insertar en tabla de roles correspondiente
                if (tipoSeleccionado.equals("Administrador")) {
                    administrador.insertarAdministrador(idPersona);
                } else if (tipoSeleccionado.equals("Veterinario")) {
                    veterinario.insertarVeterinario(idPersona);
                } else {
                    recepcionista.insertarRecepcionista(idPersona);
                }

                JOptionPane.showMessageDialog(this, "Cuenta creada exitosamente.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                // Limpiar campos
                limpiarCampos(txtNombre, txtApellidoP, txtApellidoM, txtCI, txtTelefono, 
                              txtFechaNacimiento, txtUsuario, txtContrasenia, txtRepetirContrasenia);
                
                panelMain.mostrarPanel("login");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El CI debe ser un número válido.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al crear la cuenta:\n" + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cifrar la contraseña.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnAtras = BotonPersonalizado.crearBoton("Atrás");
        btnAtras.addActionListener(e -> panelMain.mostrarPanel("login"));

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnAtras);
        panelCentro.add(panelBotones);

        // Agregar ScrollPane para permitir desplazamiento
        JScrollPane scrollPane = new JScrollPane(panelCentro);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().setBackground(Color.BLACK);

        panelIzquierdo.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelIzquierdo);

        // Panel derecho - Lista de cuentas
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(Color.BLACK);

        // Título del panel derecho
        JPanel panelTituloDerecho = new JPanel();
        panelTituloDerecho.setBackground(Color.BLACK);
        JLabel lblTituloDerecho = new JLabel("CUENTAS REGISTRADAS:");
        lblTituloDerecho.setForeground(Color.WHITE);
        lblTituloDerecho.setFont(new Font("Arial", Font.BOLD, 28));
        panelTituloDerecho.add(lblTituloDerecho);
        panelDerecho.add(panelTituloDerecho, BorderLayout.NORTH);

        // Panel para la lista de cuentas
        JPanel panelListaCuentas = new JPanel();
        panelListaCuentas.setBackground(Color.BLACK);
        panelListaCuentas.setLayout(new BoxLayout(panelListaCuentas, BoxLayout.Y_AXIS));
        panelListaCuentas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Cargar cuentas desde la base de datos
        cargarCuentas(panelListaCuentas);

        // ScrollPane para la lista de cuentas
        JScrollPane scrollCuentas = new JScrollPane(panelListaCuentas);
        scrollCuentas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollCuentas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollCuentas.setBorder(null);
        scrollCuentas.getVerticalScrollBar().setUnitIncrement(16);
        scrollCuentas.setBackground(Color.BLACK);
        scrollCuentas.getViewport().setBackground(Color.BLACK);

        panelDerecho.add(scrollCuentas, BorderLayout.CENTER);

        // Botón para refrescar la lista
        JPanel panelBotonRefrescar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonRefrescar.setBackground(Color.BLACK);
        JButton btnRefrescar = BotonPersonalizado.crearBoton("Refrescar Lista");
        btnRefrescar.addActionListener(e -> {
            panelListaCuentas.removeAll();
            cargarCuentas(panelListaCuentas);
            panelListaCuentas.revalidate();
            panelListaCuentas.repaint();
        });
        panelBotonRefrescar.add(btnRefrescar);
        panelDerecho.add(panelBotonRefrescar, BorderLayout.SOUTH);

        panel.add(panelDerecho);

        return panel;
    }

    /**
     * Método para cifrar contraseñas usando SHA-256
     */
    public static String cifrarContrasenia(String contrasenia) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(contrasenia.getBytes());
        StringBuilder hexString = new StringBuilder();
        
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        
        return hexString.toString();
    }

    /**
     * Método para cargar las cuentas desde la base de datos
     */
    private void cargarCuentas(JPanel panelListaCuentas) {
        try {
            java.util.List<Cuenta> cuentas = cuenta.obtenerTodasLasCuentas();
            
            if (cuentas.isEmpty()) {
                JLabel lblSinCuentas = new JLabel("No hay cuentas registradas");
                lblSinCuentas.setForeground(Color.WHITE);
                lblSinCuentas.setFont(new Font("Arial", Font.ITALIC, 18));
                lblSinCuentas.setAlignmentX(Component.CENTER_ALIGNMENT);
                panelListaCuentas.add(lblSinCuentas);
            } else {
                for (Cuenta cuenta : cuentas) {
                    JPanel panelCuenta = crearPanelCuenta(cuenta, panelListaCuentas);
                    panelListaCuentas.add(panelCuenta);
                    panelListaCuentas.add(Box.createVerticalStrut(10));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JLabel lblError = new JLabel("Error al cargar cuentas");
            lblError.setForeground(Color.RED);
            lblError.setFont(new Font("Arial", Font.PLAIN, 16));
            lblError.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelListaCuentas.add(lblError);
        }
    }

    /**
     * Método para crear el panel de una cuenta individual
     */
    private JPanel crearPanelCuenta(Cuenta cuenta, JPanel panelListaCuentas) {
        JPanel panelCuenta = new JPanel();
        panelCuenta.setLayout(new BorderLayout(10, 10));
        panelCuenta.setBackground(new Color(30, 30, 30));
        panelCuenta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 255), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panelCuenta.setMaximumSize(new Dimension(500, 120));
        panelCuenta.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Información de la cuenta
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(new Color(30, 30, 30));

        try {
            persona = persona.obtenerPersonaPorId(cuenta.getIdPersona());
            
            JLabel lblNombre = new JLabel("👤 " + persona.getNombre() + " " + 
                                         persona.getApellidoPaterno() + " " + 
                                         persona.getApellidoMaterno());
            lblNombre.setForeground(Color.WHITE);
            lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
            
            JLabel lblUsuario = new JLabel("Usuario: " + cuenta.getUsuario());
            lblUsuario.setForeground(new Color(200, 200, 200));
            lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
            
            JLabel lblTipo = new JLabel("Tipo: " + cuenta.getTipoUsuario());
            lblTipo.setForeground(new Color(100, 200, 255));
            lblTipo.setFont(new Font("Arial", Font.PLAIN, 14));
            
            JLabel lblCI = new JLabel("CI: " + persona.getCi());
            lblCI.setForeground(new Color(200, 200, 200));
            lblCI.setFont(new Font("Arial", Font.PLAIN, 14));

            panelInfo.add(lblNombre);
            panelInfo.add(Box.createVerticalStrut(5));
            panelInfo.add(lblUsuario);
            panelInfo.add(lblTipo);
            panelInfo.add(lblCI);

        } catch (SQLException ex) {
            JLabel lblError = new JLabel("Error al cargar información");
            lblError.setForeground(Color.RED);
            panelInfo.add(lblError);
        }

        // Botón eliminar
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(new Color(30, 30, 30));
        
        JButton btnEliminar = new JButton("🗑️ Eliminar");
        btnEliminar.setBackground(new Color(200, 50, 50));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnEliminar.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar la cuenta de " + cuenta.getUsuario() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    // Usamos 0 como idCuenta ya que no lo necesitamos, eliminamos por idPersona
                    cuenta.eliminarCuenta(0, cuenta.getIdPersona(), cuenta.getTipo());
                    JOptionPane.showMessageDialog(
                        this,
                        "Cuenta eliminada exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    
                    // Refrescar la lista
                    panelListaCuentas.removeAll();
                    cargarCuentas(panelListaCuentas);
                    panelListaCuentas.revalidate();
                    panelListaCuentas.repaint();
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                        this,
                        "Error al eliminar la cuenta:\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        panelBoton.add(btnEliminar);

        panelCuenta.add(panelInfo, BorderLayout.CENTER);
        panelCuenta.add(panelBoton, BorderLayout.EAST);

        return panelCuenta;
    }

    private void limpiarCampos(CampoTextoPersonalizado... campos) {
        for (CampoTextoPersonalizado campo : campos) {
            campo.setText("");
        }
    }

    private void limpiarCampos(CampoTextoPersonalizado c1, CampoTextoPersonalizado c2, 
                               CampoTextoPersonalizado c3, CampoTextoPersonalizado c4,
                               CampoTextoPersonalizado c5, CampoTextoPersonalizado c6,
                               CampoTextoPersonalizado c7, CampoPasswordPersonalizado p1, 
                               CampoPasswordPersonalizado p2) {
        c1.setText("");
        c2.setText("");
        c3.setText("");
        c4.setText("");
        c5.setText("");
        c6.setText("");
        c7.setText("");
        p1.setText("");
        p2.setText("");
    }

    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }
}