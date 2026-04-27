package VETERINARIA;

import java.awt.*;
import javax.swing.*;

import CLASES.Ficha;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Panel_Ficha extends JPanel {

    private Panel_Main panelMain;
    private JLabel lblFecha;
    private JLabel lblCodigo;
    private JLabel lblPropietario;
    private JLabel lblMascota;
    private JLabel lblEspecie;
    
    private Ficha ficha;

    public Panel_Ficha(Panel_Main panelMain) {
        this.panelMain = panelMain;
        this.ficha = new Ficha();
    }

    public JPanel Crear_Panel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Título
        JLabel lblTitulo = new JLabel("FICHA DE LA MASCOTA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(30));

        // Labels que se actualizarán dinámicamente
        lblFecha = new JLabel("Fecha: --/--/----");
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 24));
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblFecha);
        panel.add(Box.createVerticalStrut(15));

        lblCodigo = new JLabel("Código de ficha: ----");
        lblCodigo.setFont(new Font("Arial", Font.PLAIN, 24));
        lblCodigo.setForeground(Color.WHITE);
        lblCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblCodigo);
        panel.add(Box.createVerticalStrut(15));

        lblMascota = new JLabel("Mascota: ----");
        lblMascota.setFont(new Font("Arial", Font.PLAIN, 24));
        lblMascota.setForeground(Color.WHITE);
        lblMascota.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblMascota);
        panel.add(Box.createVerticalStrut(15));

        lblEspecie = new JLabel("Especie: ----");
        lblEspecie.setFont(new Font("Arial", Font.PLAIN, 24));
        lblEspecie.setForeground(Color.WHITE);
        lblEspecie.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblEspecie);
        panel.add(Box.createVerticalStrut(15));

        lblPropietario = new JLabel("Propietario: ----");
        lblPropietario.setFont(new Font("Arial", Font.PLAIN, 24));
        lblPropietario.setForeground(Color.WHITE);
        lblPropietario.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblPropietario);
        panel.add(Box.createVerticalStrut(30));

        // Botón atrás
        JButton btnAtras = new JButton("Atrás");
        btnAtras.setFont(new Font("Arial", Font.BOLD, 18));
        btnAtras.setBackground(new Color(220, 53, 69));
        btnAtras.setForeground(Color.WHITE);
        btnAtras.setFocusPainted(false);
        btnAtras.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAtras.setPreferredSize(new Dimension(180, 50));
        btnAtras.addActionListener(e -> panelMain.mostrarPanel("recepcionista"));
        panel.add(btnAtras);

        return panel;
    }

    /**
     * Actualiza los datos del panel usando los datos compartidos de Panel_Main
     * Este método se llama automáticamente cuando se muestra el panel
     */
    public void actualizarDatos() {
        System.out.println("=== DEPURACIÓN Panel_Ficha.actualizarDatos() ===");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Primero intentar obtener desde datos compartidos directamente
        Object objCodigoFicha = panelMain.getDatoCompartido("codigoFicha");
        Object objFechaFicha = panelMain.getDatoCompartido("fechaFicha");
        Object objNombreProp = panelMain.getDatoCompartido("nombrePropietario");
        Object objNombreMasc = panelMain.getDatoCompartido("nombreMascota");
        Object objEspecie = panelMain.getDatoCompartido("especieMascota");
        Object objIdMascota = panelMain.getDatoCompartido("idMascota");
        
        System.out.println("Código Ficha: " + objCodigoFicha);
        System.out.println("Fecha Ficha: " + objFechaFicha);
        System.out.println("Nombre Propietario: " + objNombreProp);
        System.out.println("Nombre Mascota: " + objNombreMasc);
        System.out.println("Especie: " + objEspecie);
        System.out.println("ID Mascota: " + objIdMascota);
        
        // Si todos los datos están presentes, usarlos directamente
        if (objCodigoFicha != null && objFechaFicha != null && 
            objNombreProp != null && objNombreMasc != null) {
            
            System.out.println("Usando datos compartidos directamente");
            
            int codigoFicha = (Integer) objCodigoFicha;
            LocalDate fechaFicha = (LocalDate) objFechaFicha;
            String nombrePropietario = (String) objNombreProp;
            String nombreMascota = (String) objNombreMasc;
            String especieMascota = objEspecie != null ? (String) objEspecie : "No especificada";
            
            lblFecha.setText("Fecha: " + fechaFicha.format(formatter));
            lblCodigo.setText("Código de ficha: " + codigoFicha);
            lblMascota.setText("Mascota: " + nombreMascota);
            lblEspecie.setText("Especie: " + especieMascota);
            lblPropietario.setText("Propietario: " + nombrePropietario);
            
            System.out.println("Datos actualizados en labels correctamente");
        }
        // Si solo tenemos el código, cargar desde BD
        else if (objCodigoFicha != null) {
            System.out.println("Cargando desde BD por código");
            int codigoFicha = (Integer) objCodigoFicha;
            cargarDatosPorCodigo(codigoFicha);
        }
        // Si tenemos idMascota, cargar desde BD
        else if (objIdMascota != null) {
            int idMascota = (Integer) objIdMascota;
            System.out.println("Cargando desde BD por ID mascota: " + idMascota);
            cargarDatosPorMascota(idMascota);
        }
        else {
            System.out.println("ERROR: No hay datos disponibles para mostrar");
            JOptionPane.showMessageDialog(this, 
                "No hay datos de ficha disponibles",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
        }
        
        System.out.println("=== FIN DEPURACIÓN ===");
    }

    /**
     * Carga los datos de la ficha desde la base de datos usando el código
     */
    public void cargarDatosPorCodigo(int codigoFicha) {
        System.out.println("cargarDatosPorCodigo: " + codigoFicha);
        try {
            
             ficha = ficha.obtenerFichaPorCodigo(codigoFicha);

            if (ficha != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                
                lblFecha.setText("Fecha: " + ficha.getFecha().format(formatter));
                lblCodigo.setText("Código de ficha: " + ficha.getCodigoFicha());
                lblMascota.setText("Mascota: " + ficha.getNombreMascota());
                lblEspecie.setText("Especie: " + ficha.getEspecieMascota());
                lblPropietario.setText("Propietario: " + ficha.getNombrePropietario());
                
                System.out.println("Ficha cargada exitosamente desde BD");
            } else {
                System.out.println("ERROR: Ficha no encontrada en BD");
                JOptionPane.showMessageDialog(this, 
                    "No se encontró la ficha con código: " + codigoFicha,
                    "Ficha no encontrada",
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("ERROR al cargar ficha: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos de la ficha:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga los datos de la ficha desde la base de datos usando el ID de la mascota
     */
    public void cargarDatosPorMascota(int idMascota) {
        System.out.println("cargarDatosPorMascota: " + idMascota);
        try {
           
            ficha = ficha.obtenerFichaPorMascota(idMascota);

            if (ficha != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                
                lblFecha.setText("Fecha: " + ficha.getFecha().format(formatter));
                lblCodigo.setText("Código de ficha: " + ficha.getCodigoFicha());
                lblMascota.setText("Mascota: " + ficha.getNombreMascota());
                lblEspecie.setText("Especie: " + ficha.getEspecieMascota());
                lblPropietario.setText("Propietario: " + ficha.getNombrePropietario());
                
                System.out.println("Ficha cargada exitosamente desde BD por mascota");
            } else {
                System.out.println("ERROR: Ficha no encontrada para mascota");
                JOptionPane.showMessageDialog(this, 
                    "No se encontró ficha para la mascota con ID: " + idMascota,
                    "Ficha no encontrada",
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("ERROR al cargar ficha por mascota: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos de la ficha:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }


    public void cargarDatosPorPropietario(int idPropietario) {
        System.out.println("cargarDatosPorPropietario: " + idPropietario);
        try {
  
             ficha = ficha.obtenerFichaPorPropietario(idPropietario);

            if (ficha != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                
                lblFecha.setText("Fecha: " + ficha.getFecha().format(formatter));
                lblCodigo.setText("Código de ficha: " + ficha.getCodigoFicha());
                lblMascota.setText("Mascota: " + ficha.getNombreMascota());
                lblEspecie.setText("Especie: " + ficha.getEspecieMascota());
                lblPropietario.setText("Propietario: " + ficha.getNombrePropietario());
                
                System.out.println("Ficha cargada exitosamente desde BD por propietario");
            } else {
                System.out.println("ERROR: Ficha no encontrada para propietario");
                JOptionPane.showMessageDialog(this, 
                    "No se encontró ficha para el propietario con ID: " + idPropietario,
                    "Ficha no encontrada",
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("ERROR al cargar ficha por propietario: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos de la ficha:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}