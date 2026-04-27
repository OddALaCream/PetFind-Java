package CLASES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import PSQL.ConexionPostgreSQL;

public class Persona {
    private int idPersona;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fechaNacimiento;
    private int ci;
    private String telefono;  // ← NUEVO

    // Constructor vacío
    public Persona() {}

    // Constructor con parámetros
    public Persona(int idPersona, String nombre, String apellidoPaterno, 
                   String apellidoMaterno, String fechaNacimiento, int ci, String telefono) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.ci = ci;
        this.telefono = telefono;
    }

    // Getters y Setters
    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public String getTelefono() {  // ← NUEVO
        return telefono;
    }

    public void setTelefono(String telefono) {  // ← NUEVO
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "idPersona=" + idPersona +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", ci=" + ci +
                ", telefono='" + telefono + '\'' +
                '}';
    }
    
    
    public static int insertarPersona(Persona persona) throws SQLException {
        String sql = "INSERT INTO personas (nombre, apellido_paterno, apellido_materno, fecha_nacimiento, ci, telefono) " +
                     "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_persona";

        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, persona.getNombre());
            pst.setString(2, persona.getApellidoPaterno());
            pst.setString(3, persona.getApellidoMaterno());
            pst.setDate(4, java.sql.Date.valueOf(persona.getFechaNacimiento()));
            pst.setInt(5, persona.getCi());
            pst.setString(6, persona.getTelefono());  // ← NUEVO

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_persona");
                }
            }
        }

        throw new SQLException("No se pudo insertar la persona");
    }
    public static Persona obtenerPersonaPorId(int idPersona) throws SQLException {
        String sql = "SELECT * FROM personas WHERE id_persona = ?";
        
        try (Connection conn = ConexionPostgreSQL.crearConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idPersona);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Persona persona = new Persona();
                persona.setIdPersona(rs.getInt("id_persona"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApellidoPaterno(rs.getString("apellido_paterno"));
                persona.setApellidoMaterno(rs.getString("apellido_materno"));
                persona.setCi(rs.getInt("ci"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                return persona;
            }
        }
        
        return null;
    }
}