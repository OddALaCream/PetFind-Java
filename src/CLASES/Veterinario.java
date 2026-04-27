package CLASES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import PSQL.ConexionPostgreSQL;

public class Veterinario {
    private int idVeterinario; // corresponde a id_persona

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }





////////////////////METODOS/////////////////////////

	public static void insertarVeterinario(int idPersona) throws SQLException {
	    String sql = "INSERT INTO veterinarios (id_veterinario) VALUES (?)";
	
	    try (Connection con = ConexionPostgreSQL.crearConexion();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	
	        pst.setInt(1, idPersona);
	        pst.executeUpdate();
	    }
	}

}