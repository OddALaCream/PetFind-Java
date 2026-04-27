package CLASES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import PSQL.ConexionPostgreSQL;

public class HorarioDisponible {
    private int idHorarioDisponible;
    private int idVeterinario;
    private java.sql.Date fecha;
    private String horario;

    // Getters y Setters
    
    public static void insertarHorarios(int idVeterinario, String fecha, String horario) {
        String sqlInsertar = "INSERT INTO horarios_disponibles (id_veterinario, fecha, horario) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionPostgreSQL.crearConexion();
             PreparedStatement pst = conexion.prepareStatement(sqlInsertar)) {

            pst.setInt(1, idVeterinario);
            pst.setDate(2, java.sql.Date.valueOf(fecha));
            pst.setString(3, horario);

            int filas = pst.executeUpdate();
            System.out.println("Filas insertadas: " + filas);

        } catch (SQLException e) {
            System.out.println("Error al insertar horario: " + e.getMessage());
        }
    }
    
    public static Object[][] obtenerHorarios() {
        String sql =
            "SELECT p.nombre, h.fecha, h.horario " +
            "FROM horarios_disponibles h " +
            "JOIN personas p ON h.id_veterinario = p.id_persona " +
            "ORDER BY h.fecha, h.horario";

        try (Connection conexion = ConexionPostgreSQL.crearConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql,
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = pstmt.executeQuery()) {

            rs.last();
            int totalFilas = rs.getRow();
            rs.beforeFirst();

            Object[][] datos = new Object[totalFilas][4];
            int i = 0;

            while (rs.next()) {
                datos[i][0] = (i + 1) + "";                       
                datos[i][1] = rs.getString("nombre");               
                datos[i][2] = rs.getDate("fecha").toString();       
                datos[i][3] = rs.getString("horario");              
                i++;
            }

            return datos;

        } catch (SQLException e) {
            System.out.println("Error al obtener horarios: " + e.getMessage());
            return new Object[0][0];
        }
    }
    public void eliminarHorario(String medico, String fecha, String horario) {
        String sql = "DELETE FROM horarios_disponibles hd " +
                     "USING veterinarios v, personas p " +
                     "WHERE hd.id_veterinario = v.id_veterinario " +
                     "AND v.id_veterinario = p.id_persona " +
                     "AND p.nombre = ? AND hd.fecha = ? AND hd.horario = ?";

        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "2312321");
            ps.setDate(2, java.sql.Date.valueOf(fecha)); 
            ps.setString(3, horario);

            int filas = ps.executeUpdate();
            System.out.println("Filas eliminadas: " + filas);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar horario:\n" + e.getMessage());
        }
    }
}

