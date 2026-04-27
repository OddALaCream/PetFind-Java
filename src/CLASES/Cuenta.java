package CLASES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.NoSuchAlgorithmException;

import PSQL.ConexionPostgreSQL;
import VETERINARIA.Panel_Cuenta;

public class Cuenta {
    
    private int id;
    private int idPersona;
    private String usuario;
    private String contrasenia;
    private int tipo; // 0=Administrador, 1=Veterinario, 2=Recepcionista
    
    // Constructor vacío
    public Cuenta() {
    }
    
    // Constructor con parámetros
    public Cuenta(int id, int idPersona, String usuario, String contrasenia, int tipo) {
        this.id = id;
        this.idPersona = idPersona;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.tipo = tipo;
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
    
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getContrasenia() {
        return contrasenia;
    }
    
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    public int getTipo() {
        return tipo;
    }
    
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    // Métodos auxiliares
    public String getTipoUsuario() {
        switch (tipo) {
            case 0:
                return "Administrador";
            case 1:
                return "Veterinario";
            case 2:
                return "Recepcionista";
            default:
                return "Desconocido";
        }
    }
    
    public boolean esAdministrador() {
        return tipo == 0;
    }
    
    public boolean esVeterinario() {
        return tipo == 1;
    }
    
    public boolean esRecepcionista() {
        return tipo == 2;
    }
    
    
    
    public static void insertarCuenta(Cuenta cuenta) throws SQLException {
        String sql = "INSERT INTO cuentas (id_persona, usuario, contrasenia, tipo) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, cuenta.getIdPersona());
            pst.setString(2, cuenta.getUsuario());
            pst.setString(3, cuenta.getContrasenia());
            pst.setInt(4, cuenta.getTipo());

            pst.executeUpdate();
        }
    }
    
    // Método para validar cuenta con contraseña cifrada
    public int validarCuenta(String usuario, String contrasenia) throws SQLException, NoSuchAlgorithmException {
        String sql = "SELECT tipo FROM cuentas WHERE usuario = ? AND contrasenia = ?";
        
        // Cifrar la contraseña ingresada
        String contraseniaCifrada = Panel_Cuenta.cifrarContrasenia(contrasenia);
        
        try (Connection conn = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, contraseniaCifrada);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return rs.getInt("tipo");
                else return -1;
            }
        }
    }
    
    ////////////////////////////////////////DUDA??????????????????
    public static int obtenerIdPorUsuario(String usuario) throws SQLException {
        String sql = "SELECT id_persona FROM cuentas WHERE usuario = ?";
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, usuario);
            try (ResultSet rs = pst.executeQuery()) {
                if(rs.next()) return rs.getInt("id_persona");
                else throw new SQLException("No se encontró el usuario");
            }
        }
    }
 
    
    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + id +
                ", idPersona=" + idPersona +
                ", usuario='" + usuario + '\'' +
                ", tipo=" + getTipoUsuario() +
                '}';
    }
    
    public static java.util.List<Cuenta> obtenerTodasLasCuentas() throws SQLException {
        java.util.List<Cuenta> cuentas = new java.util.ArrayList<>();
        String sql = "SELECT id_persona, usuario, contrasenia, tipo FROM cuentas ORDER BY usuario";
        
        try (Connection conn = ConexionPostgreSQL.crearConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setIdPersona(rs.getInt("id_persona"));
                cuenta.setUsuario(rs.getString("usuario"));
                cuenta.setContrasenia(rs.getString("contrasenia"));
                cuenta.setTipo(rs.getInt("tipo"));
                cuentas.add(cuenta);
            }
        }
        
        return cuentas;
    }
    
    public static void eliminarCuenta(int idCuenta, int idPersona, int tipo) throws SQLException {
        Connection conn = null;
        try {
            conn = ConexionPostgreSQL.crearConexion();
            conn.setAutoCommit(false);
            
            // Eliminar de la tabla de rol correspondiente
            String sqlRol = "";
            if (tipo == 0) {
                sqlRol = "DELETE FROM administradores WHERE id_administrador = ?";
            } else if (tipo == 1) {
                sqlRol = "DELETE FROM veterinarios WHERE id_veterinario = ?";
            } else if (tipo == 2) {
                sqlRol = "DELETE FROM recepcionistas WHERE id_recepcionista = ?";
            }
            
            if (!sqlRol.isEmpty()) {
                try (PreparedStatement pstmt = conn.prepareStatement(sqlRol)) {
                    pstmt.setInt(1, idPersona);
                    pstmt.executeUpdate();
                }
            }
            
            // Eliminar de la tabla cuentas
            String sqlCuenta = "DELETE FROM cuentas WHERE id_persona = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlCuenta)) {
                pstmt.setInt(1, idPersona);
                pstmt.executeUpdate();
            }
            
            // Eliminar de la tabla personas
            String sqlPersona = "DELETE FROM personas WHERE id_persona = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlPersona)) {
                pstmt.setInt(1, idPersona);
                pstmt.executeUpdate();
            }
            
            conn.commit();
            
        } catch (SQLException ex) {
            if (conn != null) {
                conn.rollback();
            }
            throw ex;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

}