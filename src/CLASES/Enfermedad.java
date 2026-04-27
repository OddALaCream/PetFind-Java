package CLASES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import PSQL.ConexionPostgreSQL;

public class Enfermedad {
    private int idEnfermedad;
    private String nombre;

    // Getters y Setters
    
    java.util.List<String> obtenerEnfermedadesPorDiagnostico(int idDiagnostico) {
        java.util.List<String> enfermedades = new java.util.ArrayList<>();
        String sql = "SELECT nombre FROM Enfermedades WHERE id_diagnostico = ?";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idDiagnostico);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    enfermedades.add(rs.getString("nombre"));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return enfermedades;
    }

}
