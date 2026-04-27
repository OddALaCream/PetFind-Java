package VETERINARIA;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import CLASES.Diagnostico;
import CLASES.Receta;

public class Panel_Historial4 extends JPanel {

    private Panel_Main panelMain;
    private Diagnostico diagnostico;
    private Receta receta;

    public Panel_Historial4(Panel_Main panelMain) {
        this.panelMain = panelMain;
        this.diagnostico = new Diagnostico();
        this.receta = new Receta();
    }

    public JPanel Crear_Panel() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 3));
        panelPrincipal.setBackground(Color.BLACK);

        // ==================== PANEL SUPERIOR ====================
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.BLACK);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.BLACK);
        JLabel lblTitulo = new JLabel("REPORTE DE DIAGNÓSTICO");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitulo.add(lblTitulo);
        panelSuperior.add(panelTitulo, BorderLayout.NORTH);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 3));
        panelBoton.setBackground(Color.BLACK);
        JButton btnVolver = BotonPersonalizado.crearBoton("← Volver al Historial");
        btnVolver.addActionListener(e -> panelMain.mostrarPanel("historial3"));
        panelBoton.add(btnVolver);
        panelSuperior.add(panelBoton, BorderLayout.SOUTH);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // ==================== PANEL CENTRAL ====================
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(Color.BLACK);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // ===== INFORMACIÓN GENERAL =====
        JPanel panelInfoGeneral = crearSeccion("📋 INFORMACIÓN GENERAL");
        JPanel gridInfoGeneral = new JPanel(new GridLayout(2, 3, 5, 4));
        gridInfoGeneral.setBackground(new Color(30, 30, 30));

        JLabel lblMascota = crearLabelDato("Cargando...");
        JLabel lblEspecie = crearLabelDato("Cargando...");
        JLabel lblVeterinario = crearLabelDato("Cargando...");
        JLabel lblFecha = crearLabelDato("Cargando...");
        JLabel lblIdDiagnostico = crearLabelDato("Cargando...");

        gridInfoGeneral.add(crearCampoInfo("Mascota:", lblMascota));
        gridInfoGeneral.add(crearCampoInfo("Especie:", lblEspecie));
        gridInfoGeneral.add(crearCampoInfo("Veterinario:", lblVeterinario));
        gridInfoGeneral.add(crearCampoInfo("Fecha:", lblFecha));
        gridInfoGeneral.add(crearCampoInfo("ID Diagnóstico:", lblIdDiagnostico));

        panelInfoGeneral.add(gridInfoGeneral);
        panelContenido.add(panelInfoGeneral);
        panelContenido.add(Box.createVerticalStrut(6));

        // ===== SIGNOS VITALES =====
        JPanel panelVitales = crearSeccion("💓 SIGNOS VITALES");
        JPanel gridVitales = new JPanel(new GridLayout(1, 3, 5, 3));
        gridVitales.setBackground(new Color(30, 30, 30));

        JLabel lblTemperatura = crearLabelDato("--");
        JLabel lblCardiaca = crearLabelDato("--");
        JLabel lblRespiratoria = crearLabelDato("--");

        gridVitales.add(crearCampoInfo("🌡️ Temperatura:", lblTemperatura));
        gridVitales.add(crearCampoInfo("❤️ Frec. Cardíaca:", lblCardiaca));
        gridVitales.add(crearCampoInfo("🫁 Frec. Respiratoria:", lblRespiratoria));

        panelVitales.add(gridVitales);
        panelContenido.add(panelVitales);
        panelContenido.add(Box.createVerticalStrut(6));

        // ===== ENFERMEDADES =====
        JPanel panelEnfer = crearSeccion("🦠 ENFERMEDADES DETECTADAS");
        JTextArea txtEnfermedades = crearTextArea();
        JScrollPane scrollEnf = new JScrollPane(txtEnfermedades);
        scrollEnf.setPreferredSize(new Dimension(0, 60));
        configurarScrollPane(scrollEnf);
        panelEnfer.add(scrollEnf);
        panelContenido.add(panelEnfer);
        panelContenido.add(Box.createVerticalStrut(4));

        // ===== ALERGIAS =====
        JPanel panelAler = crearSeccion("⚠️ ALERGIAS");
        JTextArea txtAlergias = crearTextArea();
        JScrollPane scrollAler = new JScrollPane(txtAlergias);
        scrollAler.setPreferredSize(new Dimension(0, 60));
        configurarScrollPane(scrollAler);
        panelAler.add(scrollAler);
        panelContenido.add(panelAler);
        panelContenido.add(Box.createVerticalStrut(4));

        // ===== OBSERVACIONES =====
        JPanel panelObs = crearSeccion("📝 OBSERVACIONES CLÍNICAS");
        JTextArea txtObservaciones = crearTextArea();
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        scrollObs.setPreferredSize(new Dimension(0, 75));
        configurarScrollPane(scrollObs);
        panelObs.add(scrollObs);
        panelContenido.add(panelObs);
        panelContenido.add(Box.createVerticalStrut(4));

        // ===== RECETA =====
        JPanel panelReceta = crearSeccion("💊 RECETA MÉDICA");
        JTextArea txtReceta = crearTextArea();
        JScrollPane scrollRec = new JScrollPane(txtReceta);
        scrollRec.setPreferredSize(new Dimension(0, 85));
        configurarScrollPane(scrollRec);
        panelReceta.add(scrollRec);
        panelContenido.add(panelReceta);

        JScrollPane scrollPrincipal = new JScrollPane(panelContenido);
        scrollPrincipal.setBorder(null);
        scrollPrincipal.getViewport().setBackground(Color.BLACK);

        panelPrincipal.add(scrollPrincipal, BorderLayout.CENTER);

        // CARGAR DATOS
        panelPrincipal.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent e) {
                cargarDetalleDiagnostico(
                        lblMascota, lblEspecie, lblVeterinario, lblFecha, lblIdDiagnostico,
                        lblTemperatura, lblCardiaca, lblRespiratoria,
                        txtEnfermedades, txtAlergias, txtObservaciones, txtReceta
                );
            }
        });

        return panelPrincipal;
    }

    // =================== MÉTODOS AUXILIARES ===================

    private JPanel crearSeccion(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 255), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        JLabel lbl = new JLabel(titulo);
        lbl.setForeground(new Color(0, 150, 255));
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(3));

        return panel;
    }

    private JPanel crearCampoInfo(String etiqueta, JLabel lblDato) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(30, 30, 30));

        JLabel lbl = new JLabel(etiqueta);
        lbl.setForeground(new Color(120, 160, 255));
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));

        lblDato.setFont(new Font("Arial", Font.BOLD, 13));

        panel.add(lbl);
        panel.add(lblDato);

        return panel;
    }

    private JLabel crearLabelDato(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        return lbl;
    }

    private JTextArea crearTextArea() {
        JTextArea txt = new JTextArea();
        txt.setFont(new Font("Arial", Font.PLAIN, 12));
        txt.setForeground(Color.WHITE);
        txt.setBackground(new Color(20, 20, 20));
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        txt.setEditable(false);
        return txt;
    }

    private void configurarScrollPane(JScrollPane scroll) {
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 200), 1));
        scroll.getViewport().setBackground(new Color(20, 20, 20));
    }

    // ✅ MÉTODOS PARA MANEJAR NULL
    private String obtenerTextoSeguro(String texto, String porDefecto) {
        return (texto != null && !texto.trim().isEmpty()) ? texto : porDefecto;
    }

    private String formatearValor(String valor, String sufijo, String porDefecto) {
        if (valor == null || valor.trim().isEmpty()) return porDefecto;
        return valor + sufijo;
    }

    private String formatearLista(List<String> lista, String porDefecto) {
        if (lista == null || lista.isEmpty()) {
            return porDefecto;
        }
        StringBuilder sb = new StringBuilder();
        for (String item : lista) {
            if (item != null && !item.trim().isEmpty()) {
                sb.append("• ").append(item.trim()).append("\n");
            }
        }
        return sb.length() > 0 ? sb.toString() : porDefecto;
    }

    // ===================== CARGAR DESDE BD =====================
    private void cargarDetalleDiagnostico(
            JLabel lblMascota, JLabel lblEspecie, JLabel lblVeterinario,
            JLabel lblFecha, JLabel lblIdDiagnostico,
            JLabel lblTemperatura, JLabel lblCardiaca, JLabel lblRespiratoria,
            JTextArea txtEnfermedades, JTextArea txtAlergias,
            JTextArea txtObservaciones, JTextArea txtReceta) {

        try {
            Object idObj = panelMain.getDatoCompartido("idDiagnosticoSeleccionado");
            if (idObj == null) {
                JOptionPane.showMessageDialog(this, "No se seleccionó ningún diagnóstico", 
                    "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idDiag = (Integer) idObj;
            
            // ✅ DEBUG - ELIMINA ESTO DESPUÉS DE VERIFICAR
            System.out.println("=== DEBUG INICIO ===");
            System.out.println("ID Diagnóstico: " + idDiag);

            Diagnostico d = diagnostico.obtenerDiagnosticoPorId(idDiag);
            
            // ✅ DEBUG
            System.out.println("Diagnostico obtenido: " + (d != null ? "OK" : "NULL"));
            if (d != null) {
                System.out.println("Temperatura: " + d.getTemperatura());
                System.out.println("Cardiaca: " + d.getFrecuenciaCardiaca());
                System.out.println("Respiratoria: " + d.getFrecuenciaRespiratoria());
                System.out.println("Nombre Mascota: " + d.getNombreMascota());
                System.out.println("Especie: " + d.getEspecieMascota());
                System.out.println("Veterinario: " + d.getNombreVeterinario());
            }
            System.out.println("=== DEBUG FIN ===");
            
            if (d == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el diagnóstico", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ✅ USAR MÉTODOS SEGUROS
            lblMascota.setText(obtenerTextoSeguro(d.getNombreMascota(), "Sin nombre"));
            lblEspecie.setText(obtenerTextoSeguro(d.getEspecieMascota(), "Sin especie"));
            lblVeterinario.setText(obtenerTextoSeguro(d.getNombreVeterinario(), "Sin veterinario"));
            lblFecha.setText(java.time.LocalDate.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            lblIdDiagnostico.setText("#" + idDiag);

            // ✅ SIGNOS VITALES CON VALIDACIÓN
            lblTemperatura.setText(formatearValor(d.getTemperatura(), " °C", "--"));
            lblCardiaca.setText(formatearValor(d.getFrecuenciaCardiaca(), " bpm", "--"));
            lblRespiratoria.setText(formatearValor(d.getFrecuenciaRespiratoria(), " rpm", "--"));

            // ✅ LISTAS CON VALIDACIÓN
            txtEnfermedades.setText(formatearLista(d.getEnfermedades(), "Sin enfermedades registradas"));
            txtAlergias.setText(formatearLista(d.getAlergias(), "Sin alergias registradas"));
            txtObservaciones.setText(formatearLista(d.getObservaciones(), "Sin observaciones"));

            // ✅ RECETA
            String receta_texto = receta.obtenerRecetaPorDiagnostico(idDiag);
            txtReceta.setText(obtenerTextoSeguro(receta_texto, "Sin receta"));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar el diagnóstico: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}