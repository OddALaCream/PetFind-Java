package CLASES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import PSQL.ConexionPostgreSQL;

// ==================== CLASE MASCOTA ====================
public class Mascota {
    private int idMascota;
    private int idPropietario;
    private String nombre;
    private String especie;
    private String raza;
    private String sexo;
    

    
    private int edadAproximada;

    // Constructores
    public Mascota() {}

    public Mascota(int idMascota, int idPropietario, String nombre, String especie, 
                   String raza, String sexo, int edadAproximada) {
        this.idMascota = idMascota;
        this.idPropietario = idPropietario;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.edadAproximada = edadAproximada;
    }

    // Getters y Setters
    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getEdadAproximada() {
        return edadAproximada;
    }

    public void setEdadAproximada(int edadAproximada) {
        this.edadAproximada = edadAproximada;
    }

    // ==================== MÉTODOS ====================
    
    /**
     * Registra una nueva mascota en la base de datos (sin retorno)
     */
    public void registrarMascota(int idPropietario, String nombre, String especie, String raza, String sexo, int edad) {
        String sql = "INSERT INTO mascotas (id_propietario, nombre, especie, raza, edad_aproximada) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPropietario);
            ps.setString(2, nombre);
            ps.setString(3, especie);
            ps.setString(4, raza);
            ps.setInt(5, edad);

            ps.executeUpdate();
            System.out.println("Mascota registrada correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar mascota:\n" + e.getMessage());
        }
    }

    /**
     * Registra una nueva mascota y retorna su ID
     * Método usado en Panel_Agendar5
     */
    public int registrarMascotaConRetorno(int idPropietario, String nombre, String especie, String raza, String sexo, int edad) {
        String sql = "INSERT INTO mascotas (id_propietario, nombre, especie, raza, edad_aproximada) " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING id_mascota";
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPropietario);
            ps.setString(2, nombre);
            ps.setString(3, especie);
            ps.setString(4, raza);
            ps.setInt(5, edad);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idMascota = rs.getInt("id_mascota");
                System.out.println("Mascota registrada correctamente con ID: " + idMascota);
                return idMascota;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar mascota:\n" + e.getMessage());
        }
        return -1;
    }

    /**
     * Obtiene el ID de la primera mascota de un propietario
     * Retorna -1 si el propietario no tiene mascotas
     */
    public int obtenerIdMascotaPorPropietario(int idPropietario) {
        String sql = "SELECT id_mascota FROM mascotas WHERE id_propietario = ? LIMIT 1";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idPropietario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_mascota");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener ID de mascota:\n" + e.getMessage());
        }
        
        return -1;
    }

    /**
     * Obtiene los datos completos de una mascota por su ID
     * Método usado en Panel_Agendar4 (método registrarFichaYMostrar)
     */
    public Mascota obtenerDatosMascota(int idMascota) {
        String sql = "SELECT id_mascota, id_propietario, nombre, especie, raza, edad_aproximada " +
                     "FROM mascotas " +
                     "WHERE id_mascota = ?";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idMascota);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Mascota mascota = new Mascota();
                    mascota.setIdMascota(rs.getInt("id_mascota"));
                    mascota.setIdPropietario(rs.getInt("id_propietario"));
                    mascota.setNombre(rs.getString("nombre"));
                    mascota.setEspecie(rs.getString("especie"));
                    mascota.setRaza(rs.getString("raza"));
                    mascota.setEdadAproximada(rs.getInt("edad_aproximada"));
                    
                    return mascota;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener datos de mascota:\n" + e.getMessage());
        }
        
        return null;
    }

    /**
     * Obtiene todas las mascotas de un propietario específico
     * Método usado en Panel_Agendar4 (método cargarMascotasPropietario)
     */
    public List<Mascota> obtenerMascotasPorPropietario(int idPropietario) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT id_mascota, id_propietario, nombre, especie, raza, edad_aproximada " +
                     "FROM mascotas " +
                     "WHERE id_propietario = ? " +
                     "ORDER BY nombre";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idPropietario);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mascota mascota = new Mascota();
                    mascota.setIdMascota(rs.getInt("id_mascota"));
                    mascota.setIdPropietario(rs.getInt("id_propietario"));
                    mascota.setNombre(rs.getString("nombre"));
                    mascota.setEspecie(rs.getString("especie"));
                    mascota.setRaza(rs.getString("raza"));
                    mascota.setEdadAproximada(rs.getInt("edad_aproximada"));
                    
                    mascotas.add(mascota);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener mascotas del propietario:\n" + e.getMessage());
        }
        
        return mascotas;
    }

    /**
     * Obtiene todas las mascotas registradas en el sistema
     */
    public java.util.List<MascotaCompleta> obtenerTodasLasMascotas() {
        java.util.List<MascotaCompleta> mascotas = new java.util.ArrayList<>();
        
        String sql = "SELECT m.id_mascota, m.nombre as nombre_mascota, m.especie, m.raza, m.edad_aproximada, " +
                     "p.nombre || ' ' || p.apellido_paterno || ' ' || p.apellido_materno as nombre_propietario, " +
                     "p.telefono " +
                     "FROM Mascotas m " +
                     "INNER JOIN Propietarios prop ON m.id_propietario = prop.id_propietario " +
                     "INNER JOIN Personas p ON prop.id_propietario = p.id_persona " +
                     "ORDER BY m.nombre";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                MascotaCompleta mascota = new MascotaCompleta();
                mascota.setIdMascota(rs.getInt("id_mascota"));
                mascota.setNombreMascota(rs.getString("nombre_mascota"));
                mascota.setEspecie(rs.getString("especie"));
                mascota.setRaza(rs.getString("raza"));
                mascota.setEdad(rs.getInt("edad_aproximada"));
                mascota.setNombrePropietario(rs.getString("nombre_propietario"));
                mascota.setTelefono(rs.getString("telefono"));
                mascotas.add(mascota);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Error al obtener mascotas:\n" + e.getMessage());
        }
        
        return mascotas;
        }



    /**
     * Actualiza los datos de una mascota existente
     */
    public boolean actualizarMascota(int idMascota, String nombre, String especie, String raza, int edad) {
        String sql = "UPDATE mascotas SET nombre = ?, especie = ?, raza = ?, edad_aproximada = ? " +
                     "WHERE id_mascota = ?";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombre);
            ps.setString(2, especie);
            ps.setString(3, raza);
            ps.setInt(4, edad);
            ps.setInt(5, idMascota);
            
            int filasActualizadas = ps.executeUpdate();
            
            if (filasActualizadas > 0) {
                System.out.println("Mascota actualizada correctamente");
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar mascota:\n" + e.getMessage());
        }
        
        return false;
    }

    /**
     * Elimina una mascota de la base de datos
     */
    public boolean eliminarMascota(int idMascota) {
        String sql = "DELETE FROM mascotas WHERE id_mascota = ?";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idMascota);
            
            int filasEliminadas = ps.executeUpdate();
            
            if (filasEliminadas > 0) {
                System.out.println("Mascota eliminada correctamente");
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar mascota:\n" + e.getMessage());
        }
        
        return false;
    }

    /**
     * Busca mascotas por nombre (búsqueda parcial)
     */
    public List<Mascota> buscarMascotasPorNombre(String nombre) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT id_mascota, id_propietario, nombre, especie, raza, edad_aproximada " +
                     "FROM mascotas " +
                     "WHERE LOWER(nombre) LIKE LOWER(?) " +
                     "ORDER BY nombre";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, "%" + nombre + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mascota mascota = new Mascota();
                    mascota.setIdMascota(rs.getInt("id_mascota"));
                    mascota.setIdPropietario(rs.getInt("id_propietario"));
                    mascota.setNombre(rs.getString("nombre"));
                    mascota.setEspecie(rs.getString("especie"));
                    mascota.setRaza(rs.getString("raza"));
                    mascota.setEdadAproximada(rs.getInt("edad_aproximada"));
                    
                    mascotas.add(mascota);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar mascotas:\n" + e.getMessage());
        }
        
        return mascotas;
    }

    /**
     * Cuenta el número total de mascotas de un propietario
     */
    public int contarMascotasPorPropietario(int idPropietario) {
        String sql = "SELECT COUNT(*) AS total FROM mascotas WHERE id_propietario = ?";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idPropietario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al contar mascotas:\n" + e.getMessage());
        }
        
        return 0;
    }

    /**
     * Verifica si existe una mascota con un nombre específico para un propietario
     */
    public boolean existeMascota(String nombre, int idPropietario) {
        String sql = "SELECT COUNT(*) AS total FROM mascotas WHERE nombre = ? AND id_propietario = ?";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombre);
            ps.setInt(2, idPropietario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "idMascota=" + idMascota +
                ", idPropietario=" + idPropietario +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", sexo='" + sexo + '\'' +
                ", edadAproximada=" + edadAproximada +
                '}';
    }
    
    public int obtenerIdMascotaPorNombreYPropietario(String nombreMascota, int idPropietario) {
        int idMascota = -1;
        String sql = "SELECT id_mascota FROM Mascotas " +
                     "WHERE nombre = ? AND id_propietario = ? " +
                     "LIMIT 1";
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombreMascota);
            ps.setInt(2, idPropietario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idMascota = rs.getInt("id_mascota");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al obtener ID de mascota:\n" + e.getMessage());
        }
        
        return idMascota;
    }
    
    public java.util.List<MascotaCompleta> buscarMascotas(String termino, String tipo) {
        java.util.List<MascotaCompleta> mascotas = new java.util.ArrayList<>();
        String sql = "";
        
        // Construir la consulta según el tipo de búsqueda
        switch (tipo) {
            case "ID":
                sql = "SELECT m.id_mascota, m.nombre as nombre_mascota, m.especie, m.raza, m.edad_aproximada, " +
                      "p.nombre || ' ' || p.apellido_paterno || ' ' || p.apellido_materno as nombre_propietario, " +
                      "p.telefono " +
                      "FROM Mascotas m " +
                      "INNER JOIN Propietarios prop ON m.id_propietario = prop.id_propietario " +
                      "INNER JOIN Personas p ON prop.id_propietario = p.id_persona " +
                      "WHERE m.id_mascota = ? " +
                      "ORDER BY m.nombre";
                break;
                
            case "NOMBRE":
                sql = "SELECT m.id_mascota, m.nombre as nombre_mascota, m.especie, m.raza, m.edad_aproximada, " +
                      "p.nombre || ' ' || p.apellido_paterno || ' ' || p.apellido_materno as nombre_propietario, " +
                      "p.telefono " +
                      "FROM Mascotas m " +
                      "INNER JOIN Propietarios prop ON m.id_propietario = prop.id_propietario " +
                      "INNER JOIN Personas p ON prop.id_propietario = p.id_persona " +
                      "WHERE LOWER(m.nombre) LIKE LOWER(?) " +
                      "ORDER BY m.nombre";
                break;
                
            case "PROPIETARIO":
                sql = "SELECT m.id_mascota, m.nombre as nombre_mascota, m.especie, m.raza, m.edad_aproximada, " +
                      "p.nombre || ' ' || p.apellido_paterno || ' ' || p.apellido_materno as nombre_propietario, " +
                      "p.telefono " +
                      "FROM Mascotas m " +
                      "INNER JOIN Propietarios prop ON m.id_propietario = prop.id_propietario " +
                      "INNER JOIN Personas p ON prop.id_propietario = p.id_persona " +
                      "WHERE LOWER(p.nombre || ' ' || p.apellido_paterno || ' ' || p.apellido_materno) LIKE LOWER(?) " +
                      "ORDER BY nombre_propietario, m.nombre";
                break;
                
            case "CI":
                sql = "SELECT m.id_mascota, m.nombre as nombre_mascota, m.especie, m.raza, m.edad_aproximada, " +
                      "p.nombre || ' ' || p.apellido_paterno || ' ' || p.apellido_materno as nombre_propietario, " +
                      "p.telefono " +
                      "FROM Mascotas m " +
                      "INNER JOIN Propietarios prop ON m.id_propietario = prop.id_propietario " +
                      "INNER JOIN Personas p ON prop.id_propietario = p.id_persona " +
                      "WHERE CAST(p.CI AS TEXT) LIKE ? " +
                      "ORDER BY p.CI, m.nombre";
                break;
                
            default:
                return mascotas;
        }
        
        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            // Configurar el parámetro según el tipo
            if (tipo.equals("ID")) {
                try {
                    int id = Integer.parseInt(termino);
                    ps.setInt(1, id);
                } catch (NumberFormatException e) {
                    return mascotas; // Retornar lista vacía si el ID no es válido
                }
            } else {
                // Para búsquedas de texto, agregar % para búsqueda parcial
                ps.setString(1, "%" + termino + "%");
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MascotaCompleta mascota = new MascotaCompleta();
                    mascota.setIdMascota(rs.getInt("id_mascota"));
                    mascota.setNombreMascota(rs.getString("nombre_mascota"));
                    mascota.setEspecie(rs.getString("especie"));
                    mascota.setRaza(rs.getString("raza"));
                    mascota.setEdad(rs.getInt("edad_aproximada"));
                    mascota.setNombrePropietario(rs.getString("nombre_propietario"));
                    mascota.setTelefono(rs.getString("telefono"));
                    mascotas.add(mascota);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Error al buscar mascotas:\n" + e.getMessage());
        }
        
        return mascotas;
    }
    

    
 
}