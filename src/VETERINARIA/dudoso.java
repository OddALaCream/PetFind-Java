package VETERINARIA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import PSQL.ConexionPostgreSQL;

public class dudoso {
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
    public int obtenerIdMascotaPorPropietario1(int idPropietario) {
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
     * Obtiene una ficha por el ID del propietario (última ficha registrada)
     * @param idPropietario El ID del propietario
     * @return Objeto Ficha con todos los datos, o null si no existe
     */

    /**
     * Obtiene el ID de la mascota por el ID del propietario
     * @param idPropietario El ID del propietario
     * @return El ID de la mascota, o -1 si no existe
     */

 // Agregar estos métodos a tu clase BD

    /**
     * Registra un diagnóstico completo con sus enfermedades, alergias y observaciones
     * @return El ID del diagnóstico creado, o -1 si hay error
     */


    /**
     * Obtiene el ID de la mascota asociada a un propietario
     * Útil cuando necesitas el id_mascota para el diagnóstico
     */

    /**
     * Obtiene un diagnóstico completo por su ID
     */






 // Agregar este método a tu clase BD

    /**
     * Obtiene una ficha completa por su código
     * @param codigoFicha El código de la ficha a buscar
     * @return Objeto Ficha con todos los datos, o null si no existe
     */

    /**
     * Obtiene una ficha por el ID del propietario (última ficha registrada)
     * @param idPropietario El ID del propietario
     * @return Objeto Ficha con todos los datos, o null si no existe
     */


    /**
     * Obtiene el ID de la mascota por el ID del propietario
     * @param idPropietario El ID del propietario
     * @return El ID de la mascota, o -1 si no existe
     */


    /**
     * Registra una ficha con el nuevo esquema (usando id_mascota en lugar de id_propietario)
     */


    /**
     * Obtiene una ficha por su código
     * Ahora busca por id_mascota en lugar de id_propietario
     */
	public boolean mascotaTieneFicha(int idMascota) {
	    String sql = "SELECT COUNT(*) as total FROM Fichas WHERE id_mascota = ?";
	    
	    try (Connection con = ConexionPostgreSQL.crearConexion();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, idMascota);
	        
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

}
