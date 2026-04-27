package CLASES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import PSQL.ConexionPostgreSQL;

public class Diagnostico {
    private int idDiagnostico;
    private int idVeterinario;
    private int idMascota;
    private String temperatura;
    private String frecuenciaCardiaca;
    private String frecuenciaRespiratoria;
    
    private String nombreMascota;
    private String especieMascota;
    private String nombreVeterinario;
    
    private List<String> enfermedades;
    private List<String> alergias;
    private List<String> observaciones;
    
    // ✅ INICIALIZAR AQUÍ
    private Enfermedad enfermedad;
    private Alergia alergia;
    private Observacion observacion;
    
    // Constructor vacío
    public Diagnostico() {
        this.enfermedades = new ArrayList<>();
        this.alergias = new ArrayList<>();
        this.observaciones = new ArrayList<>();
        
        // ✅ INICIALIZAR LOS OBJETOS
        this.enfermedad = new Enfermedad();
        this.alergia = new Alergia();
        this.observacion = new Observacion();
    }
    
    // Constructor completo
    public Diagnostico(int idVeterinario, int idMascota, String temperatura,
                              String frecuenciaCardiaca, String frecuenciaRespiratoria) {
        this();
        this.idVeterinario = idVeterinario;
        this.idMascota = idMascota;
        this.temperatura = temperatura;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }
    
    // Getters y Setters
    public int getIdDiagnostico() {
        return idDiagnostico;
    }
    
    public void setIdDiagnostico(int idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }
    
    public int getIdVeterinario() {
        return idVeterinario;
    }
    
    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }
    
    public int getIdMascota() {
        return idMascota;
    }
    
    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
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
    
    public String getNombreMascota() {
        return nombreMascota;
    }
    
    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }
    
    public String getEspecieMascota() {
        return especieMascota;
    }
    
    public void setEspecieMascota(String especieMascota) {
        this.especieMascota = especieMascota;
    }
    
    public String getNombreVeterinario() {
        return nombreVeterinario;
    }
    
    public void setNombreVeterinario(String nombreVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
    }
    
    public List<String> getEnfermedades() {
        return enfermedades;
    }
    
    public void setEnfermedades(List<String> enfermedades) {
        this.enfermedades = enfermedades;
    }
    
    public void agregarEnfermedad(String enfermedad) {
        this.enfermedades.add(enfermedad);
    }
    
    public List<String> getAlergias() {
        return alergias;
    }
    
    public void setAlergias(List<String> alergias) {
        this.alergias = alergias;
    }
    
    public void agregarAlergia(String alergia) {
        this.alergias.add(alergia);
    }
    
    public List<String> getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(List<String> observaciones) {
        this.observaciones = observaciones;
    }
    
    public void agregarObservacion(String observacion) {
        this.observaciones.add(observacion);
    }
    
    @Override
    public String toString() {
        return "DiagnosticoCompleto{" +
                "idDiagnostico=" + idDiagnostico +
                ", temperatura='" + temperatura + '\'' +
                ", frecuenciaCardiaca='" + frecuenciaCardiaca + '\'' +
                ", frecuenciaRespiratoria='" + frecuenciaRespiratoria + '\'' +
                ", nombreMascota='" + nombreMascota + '\'' +
                ", enfermedades=" + enfermedades.size() +
                ", alergias=" + alergias.size() +
                ", observaciones=" + observaciones.size() +
                '}';
    }
    
    public int registrarDiagnostico(int idVeterinario, int idMascota, 
            String temperatura, String frecuenciaCardiaca, 
            String frecuenciaRespiratoria,
            java.util.List<String> enfermedades,
            java.util.List<String> alergias,
            java.util.List<String> observaciones) {
	int idDiagnostico = -1;
	
	String sqlDiagnostico = "INSERT INTO Diagnosticos (id_veterinario, id_mascota, Temperatura, Cardiaca, Respiratoria) " +
	       "VALUES (?, ?, ?, ?, ?) RETURNING id_diagnostico";
	
	try (Connection con = ConexionPostgreSQL.crearConexion();
		PreparedStatement psDiag = con.prepareStatement(sqlDiagnostico)) {
		
		// 1. Insertar el diagnóstico principal
		psDiag.setInt(1, idVeterinario);
		psDiag.setInt(2, idMascota);
		psDiag.setString(3, temperatura);
		psDiag.setString(4, frecuenciaCardiaca);
		psDiag.setString(5, frecuenciaRespiratoria);
		
		ResultSet rs = psDiag.executeQuery();
			if (rs.next()) {
				idDiagnostico = rs.getInt("id_diagnostico");
				System.out.println("Diagnóstico registrado con ID: " + idDiagnostico);
			}
			

			if (idDiagnostico != -1 && enfermedades != null && !enfermedades.isEmpty()) {
			String sqlEnfermedades = "INSERT INTO Enfermedades (nombre, id_diagnostico) VALUES (?, ?)";
			try (PreparedStatement psEnf = con.prepareStatement(sqlEnfermedades)) {
				for (String enfermedad : enfermedades) {
				if (enfermedad != null && !enfermedad.trim().isEmpty()) {
					    psEnf.setString(1, enfermedad); 
					    psEnf.setInt(2, idDiagnostico);
					    psEnf.addBatch();
					}
					}
					psEnf.executeBatch();
					System.out.println("Enfermedades registradas: " + enfermedades.size());
				}
			}
			
			// 3. Insertar alergias
			if (idDiagnostico != -1 && alergias != null && !alergias.isEmpty()) {
			String sqlAlergias = "INSERT INTO Alergias (nombre, id_diagnostico) VALUES (?, ?)";
			try (PreparedStatement psAler = con.prepareStatement(sqlAlergias)) {
			for (String alergia : alergias) {
				if (alergia != null && !alergia.trim().isEmpty()) {
				    psAler.setString(1, alergia); // Cambiar a setInt si es ID
				    psAler.setInt(2, idDiagnostico);
				    psAler.addBatch();
				}
			}
		psAler.executeBatch();
		System.out.println("Alergias registradas: " + alergias.size());
		}
	}
	
	// 4. Insertar observaciones
	if (idDiagnostico != -1 && observaciones != null && !observaciones.isEmpty()) {
	String sqlObservaciones = "INSERT INTO Observaciones (descripcion, id_diagnostico) VALUES (?, ?)";
	try (PreparedStatement psObs = con.prepareStatement(sqlObservaciones)) {
	for (String observacion : observaciones) {
	if (observacion != null && !observacion.trim().isEmpty()) {
	    psObs.setString(1, observacion);
	    psObs.setInt(2, idDiagnostico);
	    psObs.addBatch();
	}
	}
	psObs.executeBatch();
	System.out.println("Observaciones registradas: " + observaciones.size());
	}
	}
	
	} catch (SQLException e) {
	e.printStackTrace();
	JOptionPane.showMessageDialog(null, "Error al registrar diagnóstico:\n" + e.getMessage());
	return -1;
}

return idDiagnostico;
}
    
    public Diagnostico obtenerDiagnosticoPorId(int idDiagnostico) {
        Diagnostico diagnostico = new Diagnostico();
        
        System.out.println("DEBUG Diagnostico.obtenerDiagnosticoPorId: Buscando ID = " + idDiagnostico);
        
        String sqlDiag = "SELECT d.*, m.nombre as nombre_mascota, m.especie, " +
                         "p.nombre || ' ' || p.apellido_paterno as nombre_veterinario " +
                         "FROM Diagnosticos d " +
                         "JOIN Mascotas m ON d.id_mascota = m.id_mascota " +
                         "JOIN Veterinarios v ON d.id_veterinario = v.id_veterinario " +
                         "JOIN Personas p ON v.id_veterinario = p.id_persona " +
                         "WHERE d.id_diagnostico = ?";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sqlDiag)) {
            
            ps.setInt(1, idDiagnostico);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("✅ Registro encontrado en BD");
                    
                    diagnostico.setIdDiagnostico(rs.getInt("id_diagnostico"));
                    diagnostico.setTemperatura(rs.getString("temperatura"));
                    diagnostico.setFrecuenciaCardiaca(rs.getString("cardiaca"));
                    diagnostico.setFrecuenciaRespiratoria(rs.getString("respiratoria"));
                    diagnostico.setNombreMascota(rs.getString("nombre_mascota"));
                    diagnostico.setEspecieMascota(rs.getString("especie"));
                    diagnostico.setNombreVeterinario(rs.getString("nombre_veterinario"));
                    
                    // ✅ DEBUG
                    System.out.println("Temperatura BD: [" + rs.getString("temperatura") + "]");
                    System.out.println("Cardiaca BD: [" + rs.getString("cardiaca") + "]");
                    System.out.println("Respiratoria BD: [" + rs.getString("respiratoria") + "]");
                    
                    // Cargar listas relacionadas
                    diagnostico.setEnfermedades(diagnostico.enfermedad.obtenerEnfermedadesPorDiagnostico(idDiagnostico));
                    diagnostico.setAlergias(diagnostico.alergia.obtenerAlergiasPorDiagnostico(idDiagnostico));
                    diagnostico.setObservaciones(diagnostico.observacion.obtenerObservacionesPorDiagnostico(idDiagnostico));
                } else {
                    System.out.println("❌ NO se encontró registro con id_diagnostico = " + idDiagnostico);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("❌ ERROR SQL: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al obtener diagnóstico:\n" + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return diagnostico;
    }

    
    public List<HistorialDiagnostico> obtenerHistorialDiagnosticos(int idMascota) {
        List<HistorialDiagnostico> historial = new ArrayList<>();
        
        String sql = "SELECT d.id_diagnostico, " +
                     "       TO_CHAR(CURRENT_DATE, 'DD/MM/YYYY') as fecha, " +
                     "       p.nombre || ' ' || p.apellido_paterno || ' ' || p.apellido_materno AS nombre_veterinario, " +
                     "       d.Temperatura, " +
                     "       d.Cardiaca, " +
                     "       d.Respiratoria " +
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
                    diag.setIdDiagnostico(rs.getInt("id_diagnostico"));
                    diag.setFecha(rs.getString("fecha"));
                    diag.setNombreVeterinario(rs.getString("nombre_veterinario"));
                    diag.setTemperatura(rs.getString("temperatura"));
                    diag.setFrecuenciaCardiaca(rs.getString("cardiaca"));
                    diag.setFrecuenciaRespiratoria(rs.getString("respiratoria"));
                    
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
}