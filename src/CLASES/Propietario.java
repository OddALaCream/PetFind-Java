package CLASES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import PSQL.ConexionPostgreSQL;

public class Propietario {
    private int idpropietario; // corresponde a id_persona
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String CI;
    private String telefono;
    private String fechaNacimiento;

    // Constructor vacío
    public Propietario() {
    }

    // Constructor con parámetros
    public Propietario(int idpropietario, String nombre, String apellidoPaterno, 
                      String apellidoMaterno, String CI, String telefono, String fechaNacimiento) {
        this.idpropietario = idpropietario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.CI = CI;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public int getIdPropietario() {
        return idpropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idpropietario = idPropietario;
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

    public String getCI() {
        return CI;
    }

    public void setCI(String CI) {
        this.CI = CI;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Registra un nuevo propietario en la base de datos
     * @return el ID del propietario registrado o -1 si hay error
     */
    public int registrarPropietario(String nombre, String apellidos, String CI, String telefono, String fechaNacimiento) {
        int idPropietario = -1;
        String sqlPersonas = "INSERT INTO personas (nombre, apellido_paterno, apellido_materno, fecha_nacimiento, CI, telefono) " +
                             "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_persona";
        String sqlPropietarios = "INSERT INTO propietarios (id_propietario) VALUES (?)";

        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement psPersonas = con.prepareStatement(sqlPersonas)) {

            // Separar apellido paterno y materno
            String[] apellidosSplit = apellidos.split(" ", 2);
            String apellidoP = apellidosSplit.length > 0 ? apellidosSplit[0] : "";
            String apellidoM = apellidosSplit.length > 1 ? apellidosSplit[1] : "";

            psPersonas.setString(1, nombre);
            psPersonas.setString(2, apellidoP);
            psPersonas.setString(3, apellidoM);
            psPersonas.setDate(4, java.sql.Date.valueOf(fechaNacimiento)); // yyyy-MM-dd
            psPersonas.setInt(5, Integer.parseInt(CI));
            psPersonas.setString(6, telefono);

            ResultSet rs = psPersonas.executeQuery();
            if (rs.next()) {
                idPropietario = rs.getInt("id_persona");
            }

            // Insertar en tabla Propietarios
            try (PreparedStatement psProp = con.prepareStatement(sqlPropietarios)) {
                psProp.setInt(1, idPropietario);
                psProp.executeUpdate();
            }

            System.out.println("Propietario registrado con id: " + idPropietario);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar propietario:\n" + e.getMessage());
        }

        return idPropietario;
    }

    /**
     * Obtiene el nombre completo del propietario por su ID
     * Método usado en Panel_Agendar4
     */
    public String obtenerNombrePropietario(int idPropietario) {
        String nombreCompleto = "";
        String sql = "SELECT nombre, apellido_paterno, apellido_materno FROM personas WHERE id_persona = ?";

        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPropietario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellidoP = rs.getString("apellido_paterno");
                String apellidoM = rs.getString("apellido_materno");
                
                nombreCompleto = nombre + " " + apellidoP;
                if (apellidoM != null && !apellidoM.isEmpty()) {
                    nombreCompleto += " " + apellidoM;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener nombre del propietario:\n" + e.getMessage());
        }

        return nombreCompleto;
    }

    /**
     * Obtiene todos los datos de un propietario por su ID
     */
    public Propietario obtenerDatosPropietario(int idPropietario) {
        Propietario propietario = null;
        String sql = "SELECT p.nombre, p.apellido_paterno, p.apellido_materno, p.CI, p.telefono, p.fecha_nacimiento " +
                     "FROM personas p " +
                     "INNER JOIN propietarios prop ON p.id_persona = prop.id_propietario " +
                     "WHERE prop.id_propietario = ?";

        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPropietario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                propietario = new Propietario();
                propietario.setIdPropietario(idPropietario);
                propietario.setNombre(rs.getString("nombre"));
                propietario.setApellidoPaterno(rs.getString("apellido_paterno"));
                propietario.setApellidoMaterno(rs.getString("apellido_materno"));
                propietario.setCI(rs.getString("CI"));
                propietario.setTelefono(rs.getString("telefono"));
                propietario.setFechaNacimiento(rs.getString("fecha_nacimiento"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener datos del propietario:\n" + e.getMessage());
        }

        return propietario;
    }

    /**
     * Verifica si existe un propietario con el CI dado
     */
    public boolean existePropietarioPorCI(String CI) {
        String sql = "SELECT COUNT(*) FROM personas p " +
                     "INNER JOIN propietarios prop ON p.id_persona = prop.id_propietario " +
                     "WHERE p.CI = ?";

        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(CI));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Obtiene el nombre completo formateado
     */
    public String getNombreCompleto() {
        String nombreCompleto = nombre + " " + apellidoPaterno;
        if (apellidoMaterno != null && !apellidoMaterno.isEmpty()) {
            nombreCompleto += " " + apellidoMaterno;
        }
        return nombreCompleto;
    }

    @Override
    public String toString() {
        return "Propietario{" +
                "idpropietario=" + idpropietario +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", CI='" + CI + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                '}';
    }
    
    public static int obtenerIdPropietarioPorCI(int ci) {
        int idPropietario = -1;
        String sql = "SELECT prop.id_propietario " +
                     "FROM Propietarios prop " +
                     "INNER JOIN Personas p ON prop.id_propietario = p.id_persona " +
                     "WHERE p.CI = ?";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, ci);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idPropietario = rs.getInt("id_propietario");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al buscar propietario por CI:\n" + e.getMessage());
        }
        
        return idPropietario;
    }
    

}