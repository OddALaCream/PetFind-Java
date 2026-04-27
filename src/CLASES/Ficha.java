package CLASES;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import PSQL.ConexionPostgreSQL;

public class Ficha {
    private int codigoFicha;
    private LocalDate fecha;
    private int idMascota;
    private String nombreMascota;
    private String especieMascota;
    private String nombrePropietario;
    private int idPropietario;  // ← AGREGADO

    // Constructor vacío
    public Ficha() {}

    // Constructor con parámetros (versión completa)
    public Ficha(int codigoFicha, LocalDate fecha, int idMascota,
                 String nombreMascota, String especieMascota, 
                 String nombrePropietario, int idPropietario) {
        this.codigoFicha = codigoFicha;
        this.fecha = fecha;
        this.idMascota = idMascota;
        this.nombreMascota = nombreMascota;
        this.especieMascota = especieMascota;
        this.nombrePropietario = nombrePropietario;
        this.idPropietario = idPropietario;
    }

    // Constructor sin idPropietario (por compatibilidad)
    public Ficha(int codigoFicha, LocalDate fecha, int idMascota,
                 String nombreMascota, String especieMascota, String nombrePropietario) {
        this.codigoFicha = codigoFicha;
        this.fecha = fecha;
        this.idMascota = idMascota;
        this.nombreMascota = nombreMascota;
        this.especieMascota = especieMascota;
        this.nombrePropietario = nombrePropietario;
    }

    // Getters y Setters
    public int getCodigoFicha() {
        return codigoFicha;
    }

    public void setCodigoFicha(int codigoFicha) {
        this.codigoFicha = codigoFicha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
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

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    // ← GETTER Y SETTER PARA idPropietario
    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    @Override
    public String toString() {
        return "Ficha{" +
                "codigoFicha=" + codigoFicha +
                ", fecha=" + fecha +
                ", idMascota=" + idMascota +
                ", nombreMascota='" + nombreMascota + '\'' +
                ", especieMascota='" + especieMascota + '\'' +
                ", nombrePropietario='" + nombrePropietario + '\'' +
                ", idPropietario=" + idPropietario +
                '}';
    }
    
    public int generarCodigoFicha() {
        // puede ser un número aleatorio, o el siguiente disponible en la tabla
        return (int)(Math.random() * 90000) + 10000; // 5 dígitos
    }
    
    public void registrarFicha(int idRecep, int codFicha, Date fecha, int idMascota) {
        String sql = "INSERT INTO Fichas (id_recepcionista, cod_ficha, fecha, id_mascota) VALUES (?, ?, ?, ?)";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idRecep);
            ps.setInt(2, codFicha);
            ps.setDate(3, fecha);
            ps.setInt(4, idMascota);
            ps.executeUpdate();
            
            System.out.println("Ficha registrada correctamente para mascota ID: " + idMascota);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar ficha:\n" + ex.getMessage());
        }
    }

    public Ficha obtenerFichaPorCodigo(int codigoFicha) {
        String sql = "SELECT f.cod_ficha, f.fecha, " +
                     "m.id_mascota, " +
                     "m.nombre AS nombre_mascota, " +
                     "m.especie, " +
                     "p.nombre || ' ' || p.apellido_paterno || ' ' || p.apellido_materno AS nombre_propietario " +
                     "FROM Fichas f " +
                     "JOIN mascotas m ON f.id_mascota = m.id_mascota " +
                     "JOIN propietarios prop ON m.id_propietario = prop.id_propietario " +
                     "JOIN personas p ON prop.id_propietario = p.id_persona " +
                     "WHERE f.cod_ficha = ? " +
                     "LIMIT 1";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, codigoFicha);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ficha ficha = new Ficha();
                    ficha.setCodigoFicha(rs.getInt("cod_ficha"));
                    ficha.setFecha(rs.getDate("fecha").toLocalDate());
                    ficha.setIdMascota(rs.getInt("id_mascota"));
                    ficha.setNombreMascota(rs.getString("nombre_mascota"));
                    ficha.setEspecieMascota(rs.getString("especie"));
                    ficha.setNombrePropietario(rs.getString("nombre_propietario"));
                    return ficha;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener ficha:\n" + e.getMessage());
        }
        
        return null;
    }
    public Ficha obtenerFichaPorMascota(int idMascota) {
        String sql = "SELECT f.cod_ficha, f.fecha, " +
                     "m.id_mascota, " +
                     "m.nombre AS nombre_mascota, " +
                     "m.especie, " +
                     "p.nombre || ' ' || p.apellido_paterno || ' ' || p.apellido_materno AS nombre_propietario " +
                     "FROM Fichas f " +
                     "JOIN mascotas m ON f.id_mascota = m.id_mascota " +
                     "JOIN propietarios prop ON m.id_propietario = prop.id_propietario " +
                     "JOIN personas p ON prop.id_propietario = p.id_persona " +
                     "WHERE f.id_mascota = ? " +
                     "ORDER BY f.fecha DESC " +
                     "LIMIT 1";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idMascota);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ficha ficha = new Ficha();
                    ficha.setCodigoFicha(rs.getInt("cod_ficha"));
                    ficha.setFecha(rs.getDate("fecha").toLocalDate());
                    ficha.setIdMascota(rs.getInt("id_mascota"));
                    ficha.setNombreMascota(rs.getString("nombre_mascota"));
                    ficha.setEspecieMascota(rs.getString("especie"));
                    ficha.setNombrePropietario(rs.getString("nombre_propietario"));
                    return ficha;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener ficha:\n" + e.getMessage());
        }
        
        return null;
    }
    public Ficha obtenerFichaPorPropietario(int idPropietario) {
        String sql = "SELECT f.cod_ficha, f.fecha, " +
                     "m.id_mascota, " +
                     "m.nombre AS nombre_mascota, " +
                     "m.especie, " +
                     "p.nombre || ' ' || p.apellido_paterno || ' ' || p.apellido_materno AS nombre_propietario " +
                     "FROM Fichas f " +
                     "JOIN mascotas m ON f.id_mascota = m.id_mascota " +
                     "JOIN propietarios prop ON m.id_propietario = prop.id_propietario " +
                     "JOIN personas p ON prop.id_propietario = p.id_persona " +
                     "WHERE prop.id_propietario = ? " +
                     "ORDER BY f.fecha DESC " +
                     "LIMIT 1";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idPropietario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ficha ficha = new Ficha();
                    ficha.setCodigoFicha(rs.getInt("cod_ficha"));
                    ficha.setFecha(rs.getDate("fecha").toLocalDate());
                    ficha.setIdMascota(rs.getInt("id_mascota"));
                    ficha.setNombreMascota(rs.getString("nombre_mascota"));
                    ficha.setEspecieMascota(rs.getString("especie"));
                    ficha.setNombrePropietario(rs.getString("nombre_propietario"));
                    return ficha;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener ficha:\n" + e.getMessage());
        }
        
        return null;
    }
    
    public Ficha obtenerFichaPorCodigo1(int codigoFicha) {
        Ficha ficha = null;
        String sql = "SELECT f.cod_ficha, f.fecha, f.id_mascota, " +
                     "m.Nombre as nombre_mascota, m.Especie as especie_mascota, " +
                     "p.nombre || ' ' || p.apellido_paterno as nombre_propietario, " +
                     "m.id_propietario " +
                     "FROM Fichas f " +
                     "INNER JOIN Mascotas m ON f.id_mascota = m.id_mascota " +
                     "INNER JOIN Propietarios prop ON m.id_propietario = prop.id_propietario " +
                     "INNER JOIN Personas p ON prop.id_propietario = p.id_persona " +
                     "WHERE f.cod_ficha = ?";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, codigoFicha);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ficha = new Ficha();
                    ficha.setCodigoFicha(rs.getInt("cod_ficha"));
                    ficha.setFecha(rs.getDate("fecha").toLocalDate());
                    ficha.setIdMascota(rs.getInt("id_mascota"));
                    ficha.setNombreMascota(rs.getString("nombre_mascota"));
                    ficha.setEspecieMascota(rs.getString("especie_mascota"));
                    ficha.setNombrePropietario(rs.getString("nombre_propietario"));
                    ficha.setIdPropietario(rs.getInt("id_propietario"));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al obtener ficha por código:\n" + e.getMessage());
        }
        
        return ficha;
    }

}