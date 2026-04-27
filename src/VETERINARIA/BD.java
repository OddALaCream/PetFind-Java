package VETERINARIA;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import CLASES.Cuenta;
import CLASES.Diagnostico;
import CLASES.Ficha;
import CLASES.HistorialDiagnostico;
import CLASES.Mascota;
import CLASES.Medicamento;
import CLASES.Persona;
import CLASES.MascotaCompleta;
import PSQL.ConexionPostgreSQL;
import VETERINARIA.Panel_Historial2.MascotaDatos;

public class BD {



	 // Agregar este método a la clase BD.java

	    /**
	     * Obtiene la receta completa de un diagnóstico en formato texto
	     * @param idDiagnostico El ID del diagnóstico
	     * @return String con la receta formateada, o null si no hay receta
	     */


	    
	 // Reemplaza estos dos métodos en tu clase BD.java

	    /**
	     * Obtiene una ficha completa por su código
	     * @param codigoFicha El código de la ficha a buscar
	     * @return Objeto Ficha con todos los datos, o null si no existe
	     */


	    /**
	     * Obtiene el ID de la mascota por el ID del propietario
	     * @param idPropietario El ID del propietario
	     * @return El ID de la mascota, o -1 si no existe
	     */

	 // Agregar estos métodos a tu clase BD.java

	    /**
	     * Obtiene el ID del propietario por su CI
	     * @param ci El carnet de identidad del propietario
	     * @return El ID del propietario, o -1 si no existe
	     */
	
    


	    /**
	     * Obtiene el nombre completo de un propietario
	     * @param idPropietario El ID del propietario
	     * @return El nombre completo, o "Desconocido" si no existe
	     */


	    /**
	     * Obtiene todas las mascotas de un propietario
	     * @param idPropietario El ID del propietario
	     * @return Lista de mascotas, o lista vacía si no tiene
	     */


	    /**
	     * Obtiene el ID de una mascota por su nombre y propietario
	     * @param nombreMascota El nombre de la mascota
	     * @param idPropietario El ID del propietario
	     * @return El ID de la mascota, o -1 si no existe
	     */



	    /**
	     * Obtiene todas las mascotas registradas con información del propietario
	     * @return Lista completa de mascotas
	     */
	   

	    
	     // Agregar este método a tu clase BD



	    // Método para obtener todas las cuentas (corregido con nombres en plural)
	

	    // Método para obtener persona por ID (corregido)


	    // Método para eliminar cuenta (corregido con nombres en plural)

	    // También puedes usar este método de login alternativo sin setId

	    
	    }