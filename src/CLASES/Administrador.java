package CLASES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import PSQL.ConexionPostgreSQL;

public class Administrador {
    
    private int id;
    private int idPersona;
    private Persona persona;
    
    // Constructor vacío
    public Administrador() {
    }
    
    // Constructor con parámetros
    public Administrador(int id, int idPersona) {
        this.id = id;
        this.idPersona = idPersona;
    }
    
    // Constructor completo
    public Administrador(int id, int idPersona, Persona persona) {
        this.id = id;
        this.idPersona = idPersona;
        this.persona = persona;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdPersona() {
        return idPersona;
    }
    
    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }
    
    public Persona getPersona() {
        return persona;
    }
    
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    // Método toString
    @Override
    public String toString() {
        return "Administrador{" +
                "id=" + id +
                ", idPersona=" + idPersona +
                ", persona=" + persona +
                '}';
    }
    
    // Método para obtener nombre completo del administrador
    public String getNombreCompleto() {
        if (persona != null) {
            return persona.getNombre() + " " + 
                   persona.getApellidoPaterno() + " " + 
                   persona.getApellidoMaterno();
        }
        return "";
    }
    public static void insertarAdministrador(int idPersona) throws SQLException {
        String sql = "INSERT INTO administradores (id_administrador) VALUES (?)";
        
        try (Connection conn = ConexionPostgreSQL.crearConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idPersona);
            pstmt.executeUpdate();
        }
    }
}