package CLASES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import PSQL.ConexionPostgreSQL;

public class HistorialDiagnostico {
    private int idHistorial;
    private int idDiagnostico;
    private int idFicha;
    private String diagnostico;
    private String tratamiento;
    private String fecha;
    private String nombreVeterinario;
    private String temperatura;
    private String frecuenciaCardiaca;
    private String frecuenciaRespiratoria;
    private int codigoFicha;

    // Constructores
    public HistorialDiagnostico() {}

    public HistorialDiagnostico(int idHistorial, int idFicha, String diagnostico, String tratamiento, 
                                String fecha, String nombreVeterinario) {
        this.idHistorial = idHistorial;
        this.idFicha = idFicha;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.fecha = fecha;
        this.nombreVeterinario = nombreVeterinario;
    }

    // Getters y Setters
    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public int getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(int idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
        this.idHistorial = idDiagnostico;
    }

    public int getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(int idFicha) {
        this.idFicha = idFicha;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombreVeterinario() {
        return nombreVeterinario;
    }

    public void setNombreVeterinario(String nombreVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(String frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public String getFrecuenciaRespiratoria() {
        return frecuenciaRespiratoria;
    }

    public void setFrecuenciaRespiratoria(String frecuenciaRespiratoria) {
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public int getCodigoFicha() {
        return codigoFicha;
    }

    public void setCodigoFicha(int codigoFicha) {
        this.codigoFicha = codigoFicha;
    }

    // ✅ MÉTODO CORREGIDO
    public List<HistorialDiagnostico> obtenerHistorialDiagnosticos(int idMascota) {
        List<HistorialDiagnostico> historial = new ArrayList<>();
        
        String sql = "SELECT d.id_diagnostico, " +
                     "       TO_CHAR(CURRENT_DATE, 'DD/MM/YYYY') as fecha, " +
                     "       p.nombre || ' ' || p.apellido_paterno || ' ' || COALESCE(p.apellido_materno, '') AS nombre_veterinario, " +
                     "       d.temperatura, " +
                     "       d.cardiaca, " +
                     "       d.respiratoria " +
                     "FROM Diagnosticos d " +
                     "JOIN Veterinarios v ON d.id_veterinario = v.id_veterinario " +
                     "JOIN Personas p ON v.id_veterinario = p.id_persona " +
                     "WHERE d.id_mascota = ? " +
                     "ORDER BY d.id_diagnostico DESC";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idMascota);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HistorialDiagnostico diag = new HistorialDiagnostico();
                    
                    // ✅ USAR setIdDiagnostico EN LUGAR DE setIdHistorial
                    diag.setIdDiagnostico(rs.getInt("id_diagnostico"));
                    diag.setFecha(rs.getString("fecha"));
                    diag.setNombreVeterinario(rs.getString("nombre_veterinario").trim());
                    diag.setTemperatura(rs.getString("temperatura"));
                    diag.setFrecuenciaCardiaca(rs.getString("cardiaca"));
                    diag.setFrecuenciaRespiratoria(rs.getString("respiratoria"));
                    
                    // ✅ DEBUG
                    System.out.println("DEBUG HistorialDiagnostico: ID = " + diag.getIdDiagnostico());
                    
                    historial.add(diag);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al obtener historial de diagnósticos:\n" + e.getMessage());
        }
        
        return historial;
    }

    @Override
    public String toString() {
        return "HistorialDiagnostico{" +
                "idHistorial=" + idHistorial +
                ", idDiagnostico=" + idDiagnostico +
                ", idFicha=" + idFicha +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento='" + tratamiento + '\'' +
                ", fecha='" + fecha + '\'' +
                ", nombreVeterinario='" + nombreVeterinario + '\'' +
                ", codigoFicha=" + codigoFicha +
                '}';
    }
}