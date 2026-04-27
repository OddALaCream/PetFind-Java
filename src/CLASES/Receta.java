package CLASES;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import PSQL.ConexionPostgreSQL;

// ==================== CLASE RECETA ====================
public class Receta {
    private int idReceta;
    private int idDiagnostico;

    // Constructores
    public Receta() {}

    public Receta(int idReceta, int idDiagnostico) {
        this.idReceta = idReceta;
        this.idDiagnostico = idDiagnostico;
    }

    // Getters y Setters
    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public int getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(int idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }
    public int registrarReceta(int idDiagnostico, 
            java.util.List<Medicamento> medicamentos) {

	int idReceta = -1;
	
	String sqlReceta = "INSERT INTO Recetas (id_diagnostico) VALUES (?) RETURNING id_receta";
	
	try (Connection con = ConexionPostgreSQL.crearConexion();
	PreparedStatement psReceta = con.prepareStatement(sqlReceta)) {
	
	// 1. Insertar la receta
	psReceta.setInt(1, idDiagnostico);
	
	ResultSet rs = psReceta.executeQuery();
	if (rs.next()) {
	 idReceta = rs.getInt("id_receta");
	 System.out.println("Receta registrada con ID: " + idReceta);
	}
	
	// 2. Insertar medicamentos
	if (idReceta != -1 && medicamentos != null && !medicamentos.isEmpty()) {
	
	 String sqlMedicamentos = "INSERT INTO Medicamentos " +
	                          "(id_receta, nombre, dosis, duracion, frecuencia) " +
	                          "VALUES (?, ?, ?, ?, ?)";
	
	 try (PreparedStatement psMed = con.prepareStatement(sqlMedicamentos)) {
	
	     for (Medicamento med : medicamentos) {
	         psMed.setInt(1, idReceta);
	         psMed.setString(2, med.getNombre());
	         psMed.setString(3, med.getDosis());
	         psMed.setInt(4, med.getDuracion());
	         psMed.setInt(5, med.getFrecuencia());
	         psMed.addBatch();
	     }
	
	     psMed.executeBatch();
	     System.out.println("Medicamentos registrados: " + medicamentos.size());
	 }
	}
	
	} catch (Exception e) {
	e.printStackTrace();
	JOptionPane.showMessageDialog(null, 
	 "Error al registrar receta:\n" + e.getMessage());
	}
	
	return idReceta;
	}
    
    public String obtenerRecetaPorDiagnostico(int idDiagnostico) {
        StringBuilder receta = new StringBuilder();
        
        // Obtener ID de la receta
        String sqlReceta = "SELECT id_receta FROM Recetas WHERE id_diagnostico = ? LIMIT 1";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement psReceta = con.prepareStatement(sqlReceta)) {
            
            psReceta.setInt(1, idDiagnostico);
            
            try (ResultSet rsReceta = psReceta.executeQuery()) {
                if (rsReceta.next()) {
                    int idReceta = rsReceta.getInt("id_receta");
                    
                    // Obtener medicamentos
                    String sqlMedicamentos = "SELECT nombre, dosis, duracion, frecuencia " +
                                            "FROM Medicamentos " +
                                            "WHERE id_receta = ? " +
                                            "ORDER BY id_medicamento";
                    
                    try (PreparedStatement psMed = con.prepareStatement(sqlMedicamentos)) {
                        psMed.setInt(1, idReceta);
                        
                        try (ResultSet rsMed = psMed.executeQuery()) {
                            int contador = 1;
                            while (rsMed.next()) {
                                receta.append("═══════════════════════════════════════\n");
                                receta.append("MEDICAMENTO #").append(contador).append("\n");
                                receta.append("═══════════════════════════════════════\n");
                                receta.append("Nombre: ").append(rsMed.getString("nombre")).append("\n");
                                receta.append("Dosis: ").append(rsMed.getString("dosis")).append("\n");
                                receta.append("Duración: ").append(rsMed.getInt("duracion")).append(" días\n");
                                receta.append("Frecuencia: Cada ").append(rsMed.getInt("frecuencia")).append(" horas\n");
                                receta.append("\n");
                                contador++;
                            }
                        }
                    }
                    
                    // Obtener recordatorio si existe
                    String sqlRecordatorio = "SELECT Fecha_inicio, Hora_inicio " +
                                            "FROM Recordatorios " +
                                            "WHERE id_receta = ? " +
                                            "LIMIT 1";
                    
                    try (PreparedStatement psRec = con.prepareStatement(sqlRecordatorio)) {
                        psRec.setInt(1, idReceta);
                        
                        try (ResultSet rsRec = psRec.executeQuery()) {
                            if (rsRec.next()) {
                                receta.append("═══════════════════════════════════════\n");
                                receta.append("⏰ RECORDATORIO PRIMERA DOSIS\n");
                                receta.append("═══════════════════════════════════════\n");
                                receta.append("Fecha: ").append(rsRec.getDate("Fecha_inicio")).append("\n");
                                receta.append("Hora: ").append(rsRec.getInt("Hora_inicio")).append(":00\n");
                            }
                        }
                    }
                    
                    if (receta.length() > 0) {
                        return receta.toString();
                    }
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al obtener receta:\n" + e.getMessage());
        }
        
        return null;
    }
}

