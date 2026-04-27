package CLASES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import PSQL.ConexionPostgreSQL;

public class Recepcionista {
    private int idRecepcionista; // corresponde a id_persona

    public int getIdRecepcionista() {
        return idRecepcionista;
    }

    public void setIdRecepcionista(int idRecepcionista) {
        this.idRecepcionista = idRecepcionista;
    }
    ///////////////////////////Metodos
    
    public static void insertarRecepcionista(int idPersona) throws SQLException {
        String sql = "INSERT INTO recepcionistas (id_recepcionista) VALUES (?)";

        try (Connection con = ConexionPostgreSQL.crearConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idPersona);
            pst.executeUpdate();
        }
    }    
}
