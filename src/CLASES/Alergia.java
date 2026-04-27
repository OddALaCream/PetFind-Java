package CLASES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import PSQL.ConexionPostgreSQL;

public class Alergia {
    private int idAlergia;
    private String nombre;

    // Getters y Setters
    java.util.List<String> obtenerAlergiasPorDiagnostico(int idDiagnostico) {
        java.util.List<String> alergias = new java.util.ArrayList<>();
        String sql = "SELECT nombre FROM Alergias WHERE id_diagnostico = ?";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idDiagnostico);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    alergias.add(rs.getString("nombre"));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return alergias;
    }
}
