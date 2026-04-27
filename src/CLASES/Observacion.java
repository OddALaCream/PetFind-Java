package CLASES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import PSQL.ConexionPostgreSQL;

public class Observacion {
    private int idObservacion;
    private int idMascota;
    private String descripcion;
    private int esClinico;

   
    
    java.util.List<String> obtenerObservacionesPorDiagnostico(int idDiagnostico) {
        java.util.List<String> observaciones = new java.util.ArrayList<>();
        String sql = "SELECT descripcion FROM Observaciones WHERE id_diagnostico = ?";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idDiagnostico);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    observaciones.add(rs.getString("descripcion"));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return observaciones;
    }
}
