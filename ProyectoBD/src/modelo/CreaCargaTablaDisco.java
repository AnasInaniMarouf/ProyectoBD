package modelo;
import java.sql.*;
import java.util.Scanner;
import fechas.LibFechas8;

import java.io.*;

public class CreaCargaTablaDisco {

	public static void main(String[] args) {
		
		Connection conexion = null;
		try {
			// Registrar la conexion / Levantar el JDBC
			String driver = "com.mysql.cj.jdbc.Driver"; // Solo es necesario para versiones
			                                            // inferiores a la 4.0 Class.forName(driver);
			Class.forName(driver);
			System.out.println("Conexion registrada");
	
			
			// Establecer la conexion
			String url = "jdbc:mysql://localhost:3306/dni";
			String usuario = "admin";
			String clave   = "1234";
			conexion = DriverManager.getConnection(url, usuario, clave);
			
			System.out.println("Conexion establecida");
			
			// Metodo que crea la tabla disco
			creaTablaDiscos(conexion);
			
			// Metodo que carga datos en la tabla disco a partir de un fichero
			cargaTablaDiscos(conexion);
		}
		catch (SQLException e) {
			printSQLException(e);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conexion.close();
				System.out.println("Conexion cerrada");
			} 
			catch (SQLException e) {printSQLException(e);}
		}
	}
	
	
	/**
	 * Crea la tabla DISCOS
	 * @param con: Conexion
	 */
	public static void creaTablaDiscos(Connection con) throws SQLException
	{
		String creaTabla = "create table dni.dni " +
	                       "(dni VARCHAR(9) NOT NULL PRIMARY KEY, " +
				           "nombre VARCHAR(50) NOT NULL, " +
	                       "numTelefono VARCHAR(50) NOT NULL, " +
				           "fechaNacimiento DATE";
		System.out.println("Se va a ejecutar: "+creaTabla);
		
		Statement stmt = null;
		stmt = con.createStatement();
		stmt.executeUpdate(creaTabla);
		System.out.println("Tabla DNI creada");
		stmt.close();
	}

		
	/**
	 * A�ade datos desde un fichero a la tabla discos
	 * @param con
	 * @throws SQLException
	 */
	public static void cargaTablaDiscos(Connection con) throws SQLException {

		try (Scanner sc = new Scanner(new File("./files/dni.txt"),"UTF-8")){
			
			while (sc.hasNextLine()) {

				String [] arrayDatos = sc.nextLine().split("-");

				String sqlString = "INSERT INTO dni VALUES (?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sqlString);
				
				ps.setString(1, arrayDatos[0]); //DNI
				ps.setString(2, arrayDatos[1]); // Nombre
				ps.setInt(3, Integer.parseInt( arrayDatos[2])); // Telefono
				ps.setDate(4, Date.valueOf(LibFechas8.convierteStringToLocalDate(arrayDatos[3]))); // Fecha
				
				ps.executeUpdate();
				ps.close();
			}
			System.out.println("Dnis volcados desde fichero correctamente");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * M�todo que muestra una descripcion completa de la excepcion
	 * que se ha producido
	 * @param ex -- Excepcion SQL generada
	 */
	public static void printSQLException(SQLException ex)
	{
		ex.printStackTrace(System.err);
		System.err.println("SQLState: "+ex.getSQLState());
		System.err.println("Error code: "+ex.getErrorCode());
		System.err.println("Message: "+ex.getMessage());
		Throwable t = ex.getCause();
		while (t!=null) {
			System.out.println("Cause: "+t);
			t = t.getCause();
		}
	}
}
