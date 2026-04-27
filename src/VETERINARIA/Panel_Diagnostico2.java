package VETERINARIA;

import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import CLASES.Medicamento;
import CLASES.Receta;


public class Panel_Diagnostico2 extends JPanel {

    private Panel_Main panelMain;
    
    // Lista para almacenar las recetas antes de guardar
    private List<RecetaTemporal> listaRecetas;

	private Receta receta;



    public Panel_Diagnostico2(Panel_Main panelMain) {
        this.panelMain = panelMain;
        this.listaRecetas = new ArrayList<>();
        this.receta = new Receta();
    }

    public JPanel Crear_Panel() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBackground(Color.BLACK);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.BLACK);
        JLabel lblTitulo = new JLabel("GENERAR RECETA:");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        panelTitulo.add(lblTitulo);
        panelIzquierdo.add(panelTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(Color.BLACK);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 20)); 

        CampoTextoPersonalizado txtMedicamento = new CampoTextoPersonalizado("Medicamento");
        CampoTextoPersonalizado txtDosis = new CampoTextoPersonalizado("Dosis");
        CampoTextoPersonalizado txtDuracion = new CampoTextoPersonalizado("Duración (días)");
        CampoTextoPersonalizado txtFrecuencia = new CampoTextoPersonalizado("Frecuencia (horas)");
        CampoTextoPersonalizado txtFecha = new CampoTextoPersonalizado("Fecha (YYYY-MM-DD)");
        CampoTextoPersonalizado txtHora = new CampoTextoPersonalizado("Hora (0-23)");
        
        // Etiquetas
        JLabel lblMedicamento = new JLabel("Medicamento:");
        lblMedicamento.setForeground(Color.WHITE);
        lblMedicamento.setFont(new Font("Arial", Font.PLAIN, 20));
        lblMedicamento.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDosis = new JLabel("Dosis:");
        lblDosis.setForeground(Color.WHITE);
        lblDosis.setFont(new Font("Arial", Font.PLAIN, 20));
        lblDosis.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDuracion = new JLabel("Duración (días):");
        lblDuracion.setForeground(Color.WHITE);
        lblDuracion.setFont(new Font("Arial", Font.PLAIN, 20));
        lblDuracion.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblFrecuencia = new JLabel("Frecuencia (horas):");
        lblFrecuencia.setForeground(Color.WHITE);
        lblFrecuencia.setFont(new Font("Arial", Font.PLAIN, 20));
        lblFrecuencia.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPrimeraDosis = new JLabel("PRIMERA DOSIS (OPCIONAL):");
        lblPrimeraDosis.setForeground(new Color(0, 255, 255));
        lblPrimeraDosis.setFont(new Font("Arial", Font.BOLD, 22));
        lblPrimeraDosis.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 20));
        lblFecha.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setForeground(Color.WHITE);
        lblHora.setFont(new Font("Arial", Font.PLAIN, 20));
        lblHora.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); 
        panelBotones.setBackground(Color.BLACK);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT); 

        JButton btnAnadirReceta = BotonPersonalizado.crearBoton("Añadir Medicamento");
        JButton btnFinalizar = BotonPersonalizado.crearBoton("Finalizar");
        JButton btnAtras = BotonPersonalizado.crearBoton("Atrás");
        
        panelBotones.add(btnAnadirReceta);
        panelBotones.add(btnFinalizar);
        panelBotones.add(btnAtras);

        panelCentro.add(lblMedicamento);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtMedicamento);
        panelCentro.add(Box.createVerticalStrut(10));

        panelCentro.add(lblDosis);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtDosis);
        panelCentro.add(Box.createVerticalStrut(10));

        panelCentro.add(lblDuracion);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtDuracion);
        panelCentro.add(Box.createVerticalStrut(10));

        panelCentro.add(lblFrecuencia);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtFrecuencia);
        panelCentro.add(Box.createVerticalStrut(20));

        panelCentro.add(lblPrimeraDosis);
        panelCentro.add(Box.createVerticalStrut(10));

        panelCentro.add(lblFecha);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtFecha);
        panelCentro.add(Box.createVerticalStrut(10));

        panelCentro.add(lblHora);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtHora);
        panelCentro.add(Box.createVerticalStrut(20));

        panelCentro.add(panelBotones);

        JScrollPane scrollIzquierdo = new JScrollPane(panelCentro);
        scrollIzquierdo.setBackground(Color.BLACK);
        scrollIzquierdo.setBorder(null);
        scrollIzquierdo.getViewport().setBackground(Color.BLACK);

        panelIzquierdo.add(scrollIzquierdo, BorderLayout.CENTER);

        ///////////////////////////////////////DERECHO
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(Color.BLACK);

        JPanel panelTituloDerecho = new JPanel();
        panelTituloDerecho.setBackground(Color.BLACK);
        JLabel lblTituloDerecho = new JLabel("MEDICAMENTOS AGREGADOS:");
        lblTituloDerecho.setForeground(Color.WHITE);
        lblTituloDerecho.setFont(new Font("Arial", Font.BOLD, 24));
        panelTituloDerecho.add(lblTituloDerecho);
        panelDerecho.add(panelTituloDerecho, BorderLayout.NORTH);

        String[] columnas = {"Campo", "Valor"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        JTable tablaRecetas = new JTable(modelo);
        tablaRecetas.setFont(new Font("Arial", Font.PLAIN, 16));
        tablaRecetas.setRowHeight(28);
        tablaRecetas.setForeground(Color.WHITE);
        tablaRecetas.setBackground(Color.BLACK); 
        tablaRecetas.setGridColor(new Color(0, 0, 255)); 
        tablaRecetas.setSelectionBackground(Color.DARK_GRAY);
        tablaRecetas.setSelectionForeground(new Color(0, 0, 255));

        JTableHeader header = tablaRecetas.getTableHeader();
        header.setBackground(Color.BLACK);  
        header.setForeground(Color.WHITE);  
        header.setFont(new Font("Arial", Font.BOLD, 18)); 
        header.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 2)); 
        
        JScrollPane scroll = new JScrollPane(tablaRecetas);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 3));
        scroll.getViewport().setBackground(Color.BLACK); 

        panelDerecho.add(scroll, BorderLayout.CENTER);

        // ===== ACCIÓN: AÑADIR MEDICAMENTO =====
        btnAnadirReceta.addActionListener(e -> {
            try {
                String medicamento = txtMedicamento.getText().trim();
                String dosis = txtDosis.getText().trim();
                String duracionStr = txtDuracion.getText().trim();
                String frecuenciaStr = txtFrecuencia.getText().trim();
                String fecha = txtFecha.getText().trim();
                String horaStr = txtHora.getText().trim();
                
                // Validar campos obligatorios
                if (medicamento.isEmpty() || dosis.isEmpty() || duracionStr.isEmpty() || frecuenciaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, 
                        "Complete todos los campos obligatorios:\nMedicamento, Dosis, Duración, Frecuencia", 
                        "Campos incompletos", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Convertir a números
                int duracion = Integer.parseInt(duracionStr);
                int frecuencia = Integer.parseInt(frecuenciaStr);
                
                // Validar recordatorio (si se ingresó alguno)
                Integer hora = null;
                if (!fecha.isEmpty() || !horaStr.isEmpty()) {
                    if (fecha.isEmpty() || horaStr.isEmpty()) {
                        JOptionPane.showMessageDialog(panel, 
                            "Si ingresa recordatorio, complete FECHA y HORA", 
                            "Recordatorio incompleto", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    hora = Integer.parseInt(horaStr);
                    if (hora < 0 || hora > 23) {
                        JOptionPane.showMessageDialog(panel, 
                            "La hora debe estar entre 0 y 23", 
                            "Hora inválida", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                
                // Agregar a la lista temporal
                RecetaTemporal receta = new RecetaTemporal(
                    medicamento, dosis, duracion, frecuencia, 
                    fecha.isEmpty() ? null : fecha, 
                    hora
                );
                listaRecetas.add(receta);
                
                // Mostrar en tabla
                modelo.addRow(new Object[]{"--- MEDICAMENTO #" + listaRecetas.size() + " ---", ""});
                modelo.addRow(new Object[]{"Medicamento", medicamento});
                modelo.addRow(new Object[]{"Dosis", dosis});
                modelo.addRow(new Object[]{"Duración", duracion + " días"});
                modelo.addRow(new Object[]{"Frecuencia", "Cada " + frecuencia + " horas"});
                
                if (fecha != null && hora != null) {
                    modelo.addRow(new Object[]{"Primera Dosis - Fecha", fecha});
                    modelo.addRow(new Object[]{"Primera Dosis - Hora", hora + ":00"});
                }
                
                modelo.addRow(new Object[]{"", ""});
                
                // Limpiar campos
                txtMedicamento.setText("");
                txtDosis.setText("");
                txtDuracion.setText("");
                txtFrecuencia.setText("");
                txtFecha.setText("");
                txtHora.setText("");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, 
                    "Duración, Frecuencia y Hora deben ser números", 
                    "Error de formato", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // ===== ACCIÓN: FINALIZAR (GUARDAR TODO) =====
        btnFinalizar.addActionListener(e -> {
            try {
                if (listaRecetas.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, 
                        "Agregue al menos un medicamento antes de finalizar", 
                        "Sin medicamentos", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Obtener el ID del diagnóstico del panel anterior
                Object idDiagObj = panelMain.getDatoCompartido("idDiagnostico");
                if (idDiagObj == null) {
                    JOptionPane.showMessageDialog(panel, 
                        "Error: No se encontró el diagnóstico asociado", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int idDiagnostico = (Integer) idDiagObj;
                
                BD bd = new BD();
                
                // Preparar lista de medicamentos
                List<Medicamento> medicamentos = new ArrayList<>();
 
                for (RecetaTemporal receta : listaRecetas) {
                    // Crear medicamento usando tu clase CLASES.Medicamento
                    Medicamento med = new Medicamento();
                    med.setNombre(receta.medicamento);
                    med.setDosis(receta.dosis);
                    med.setDuracion(receta.duracion);
                    med.setFrecuencia(receta.frecuencia);
                    medicamentos.add(med);

                }
                
                // Guardar en BD
                int idReceta = receta.registrarReceta(idDiagnostico, medicamentos);
                
                if (idReceta != -1) {
                    JOptionPane.showMessageDialog(panel, 
                        "Receta guardada correctamente\nID: " + idReceta, 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Limpiar todo
                    listaRecetas.clear();
                    modelo.setRowCount(0);
                    
                    // Volver al menú principal
                    panelMain.mostrarPanel("veterinario");
                } else {
                    JOptionPane.showMessageDialog(panel, 
                        "Error al guardar la receta", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panel, 
                    "Error: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // ===== ACCIÓN: ATRÁS =====
        btnAtras.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(panel,
                "¿Desea volver sin guardar?\nSe perderán los medicamentos agregados",
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (respuesta == JOptionPane.YES_OPTION) {
                listaRecetas.clear();
                modelo.setRowCount(0);
                panelMain.mostrarPanel("diagnostico1");
            }
        });

        panel.add(panelIzquierdo);
        panel.add(panelDerecho);
        
        return panel;
    }
    
    // ===== CLASE AUXILIAR PARA ALMACENAR RECETAS TEMPORALMENTE =====
    private static class RecetaTemporal {
        String medicamento;
        String dosis;
        int duracion;
        int frecuencia;
        String fecha;
        Integer hora;
        
        RecetaTemporal(String medicamento, String dosis, int duracion, int frecuencia, 
                      String fecha, Integer hora) {
            this.medicamento = medicamento;
            this.dosis = dosis;
            this.duracion = duracion;
            this.frecuencia = frecuencia;
            this.fecha = fecha;
            this.hora = hora;
        }
    }
}