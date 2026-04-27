package PSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionPostgreSQL {

    private static final String URL = "jdbc:postgresql://localhost:5432/PetFind";
    private static final String USER = "postgres";
    private static final String PASS = "31415";


    public static Connection crearConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
