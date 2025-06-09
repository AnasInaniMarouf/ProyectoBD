package modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import fechas.LibFechas8;

/**
 * Esta calse implementa el patrón DAO con la tabla Discos, este patrón ofrece 
 * operaciones para interactuar con la base de datos. 
 * 
 * Se encarga de realizar todas las operaciones CRUD es el acrónimo de 
 * "Crear, Leer, Actualizar y Borrar" (del original en inglés: Create, 
 * Read, Update and Delete), que se usa para referirse a las funciones 
 * básicas en bases de datos o la capa de persistencia en un software.
 * 
 * Lo que este patrón pretende principalmente es independizar la aplicación
 * de la forma de acceder a la base de datos.
 * 
 */

public class DAODni {

	/**
	 * Variables de instancia
	 */
	private Connection con;   // Objeto con la conexión a la BD
	private Statement stmt;   // Objeto que permite ejecutar sentencias SQL
	private ResultSet rsNavegar; // Resultado de la consulta para navergar por las filas de la tabal
	
	
	/**
	 * Constructor
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public DAODni() throws ClassNotFoundException, SQLException {
		
		this.estableceConexion();  // Dar valor a la variable con (Connection)
		
		this.crearStatement();  // Dar valor a la variable stmt (Statement)
		
		this.crearConsulta();  // Dar valor a la variable rsNavegar (ResultSet)
		
	}

	/**
	 * Método que establece la conexión con la BD
	 * @throws MiExcepcion 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void estableceConexion() throws ClassNotFoundException, SQLException {
		
		this.con = BDConnection.getConnection();
		System.out.println("Conexión establecida");
	}

/*	public void estableceConexion() throws MiExcepcion {
		
		try {
			this.con = BDConnection.getConnection();
		} 
		catch (ClassNotFoundException | SQLException e) {
			throw new MiExcepcion(e.getMessage());
		}
		System.out.println("Conexión establecida");
	}
	*/
	
	/**
	 * Crear el objeto Statement, el cual nos va a permitir ejecutar instrucciones SQL
	 * @throws SQLException 
	 */
	public void crearStatement() throws SQLException {
//		con.setAutoCommit(false);
		
		this.stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_SENSITIVE,  // Indica que el cursol es bidireccional
						                                  // y que refleja los cambios en la BD
						ResultSet.CONCUR_UPDATABLE);  // Los datos del ResultSet son actualizables 

		// En realidad no necesitamos que los datos del ResultSet sean actualizables, ya
		// que estamos creando un nuevo objeto Statement (en concreto PreparedStatement)
		// para realizar las actualizaciones de forma parametrizada, y evitar la SQL Injection 
	//	con.commit();
	//	con.setAutoCommit(true);
	}
	
	/**
	 * Método que permite crear la consulta SQL
	 * @throws SQLException 
	 */
	public void crearConsulta() throws SQLException {
		String sqlString = "SELECT * FROM DNI";
		
		this.rsNavegar = stmt.executeQuery(sqlString);
	}
	
	/**
	 * Método que cierra la conexión
	 * @throws SQLException
	 */
	public void cierraConexion() throws SQLException {
		con.close();
		System.out.println("Conexión cerrada");
	}
	

	/**
	 * Método que cierra el Statement
	 * @throws SQLException 
	 */
	public void cierraStatement() throws SQLException {
		stmt.close();
	}
	
	
	/**
	 * Método que recoge una fila o tupla de la tabla de resultados (ResultSet) 
	 * de la consulta, y devuelve con esos datos un objeto tipo Disco
	 * @return
	 * @throws SQLException 
	 * @throws MiExcepcion 
	 */
	public Dni crearDni() throws MiExcepcion, SQLException {
		return new Dni(
					rsNavegar.getString("dni"),
					rsNavegar.getString("nombre"),
					rsNavegar.getInt("numTelefono"),
					LibFechas8.transformaFecha(
							rsNavegar.getDate("fechaNacimiento")
							   .toLocalDate()
							   .toString()));
	}

	
	
	/**
	 * Método que devuelve el primer registro (trupla) de la consulta
	 * @return
	 * @throws SQLException 
	 * @throws MiExcepcion 
	 */
	public Dni getPrimero() throws SQLException, MiExcepcion {
		
		this.rsNavegar.first();
		return this.crearDni();
	}
	
	/**
	 * Metodo que devuelve el ultimo registro (tupla) de la consulta 
	 * @throws MiExcepcion 
	 */
	public Dni getUltimo() throws SQLException, MiExcepcion {
		rsNavegar.last();
		return this.crearDni();
	}
	
	/**	 
	 * Metodo que devuelve el registro siguiente al acual
	 * @throws MiExcepcion 
	 */
	public Dni getSiguiente() throws SQLException, MiExcepcion {
		rsNavegar.next();
		return this.crearDni();
	}
		
	/**
	 * Metodo que devuelve el registro anterior al actual
	 * @throws MiExcepcion 
	 */
	public Dni getAnterior() throws SQLException, MiExcepcion {
		rsNavegar.previous();
		return this.crearDni();
	}
	
	/**
	 * Metodo que permite añadir un nuevo disco (tupla) a la bs
	 * @param ob -- disco que se va a a�adir
	 * @throws SQLException
	 */
	public void insertaDni(Dni ob) throws SQLException {
		
		PreparedStatement ps = 
				con.prepareStatement("insert into documentoidentidad.dni values (?,?,?,?)");

		ps.setString(1,ob.getDni());
		ps.setString(2, ob.getNombre());
		ps.setInt(3, ob.getNumTelefono());
		ps.setDate(4, Date.valueOf(ob.getFechaNacimiento()));
		
		ps.executeUpdate();
		ps.close();
	
		this.crearConsulta();  // Actualizar el resultSet de navegaci�n con el nuevo disco
	}
	
	
	/**
	 * Metodo que permite modificar un disco existente en la talba disco, el disco
	 * con los datos modificados llega como par�metro, se puede modificar todo
	 * excepto el c�digo del disco
	 */
	public void modificaDni(Dni ob) throws SQLException {
		
		PreparedStatement ps = con.prepareStatement(
				"UPDATE dni SET nombre = ?, numTelefono = ?, fechaNacimiento = ? WHERE dni = ?");
		
		ps.setString(1,ob.getDni());
		ps.setString(2, ob.getNombre());
		ps.setInt(3, ob.getNumTelefono());
		ps.setDate(4, Date.valueOf(ob.getFechaNacimiento()));
		
		ps.executeUpdate();
		ps.close();
		
		// Volver a crear la consulta, para que se actualicen los datos en el resultSet de navegaci�n
		this.crearConsulta();		
	}
	
	
	/**
	 * Metodo que permite borrar el disco cuyo código coincide con el que nos
	 * llega como parámetro
	 */
	public void eliminaDni(String dni) throws SQLException{
		PreparedStatement ps = 
				con.prepareStatement("DELETE FROM dni WHERE dni = ?");
		
		ps.setString(1, dni);

		ps.executeUpdate();
		ps.close();

		// Volver a crear la consulta, para actualizar los datos del resultset de navegaci�n
		this.crearConsulta();
	}

	
	/**
	 * Metodo que devuelve una coleccion con todos los discos que hay en la tabla discos
	 * @return
	 * @throws SQLException
	 * @throws MiExcepcion
	 */
	public List<Dni> getAll() throws SQLException, MiExcepcion {
	
		rsNavegar.beforeFirst(); // Para posicionar la consulta al principio
		
		List<Dni> listaDni = new ArrayList<>();

		while (rsNavegar.next()) {
			
			listaDni.add(crearDni());
		}

		rsNavegar.beforeFirst();
		
		return listaDni;
	}
	
	
	/**
	 * Metodo que busca un disco cuyo codigo coincida con el indicado
	 * @param cod
	 * @return
	 * @throws SQLException
	 * @throws MiExcepcion
	 */
	public Dni buscaCodigo(int cod) throws SQLException, MiExcepcion {

		PreparedStatement ps = con.prepareStatement("SELECT * FROM dni WHERE dni = ?");
		ps.setInt(1, cod);

		ResultSet rs = ps.executeQuery();

		Dni dniBuscado = null;

		if (rs.next()) {
			dniBuscado = 
					new Dni(
							rs.getString("dni"),
							rs.getString("nombre"),
							rs.getInt("numTelefono"),
							LibFechas8.transformaFecha(rsNavegar.getDate("fechaNacimiento").toLocalDate().toString()));
		}

		rs.close();
		ps.close();

		return dniBuscado;

	}
	
	/**
	 * --- FORMA 1 -----
	 * Método que devuelve una matriz con una matriz que contiene el 
	 * resultado de una consulta
	 * @return
	 * @throws SQLException
	 */
	public String[][] datosConsulta1() throws SQLException{
		
		// Crear la consulta, mejor parametrizarla si la fecha se le va a pedir al usuario
		java.sql.Date fechaDateConsulta = Date.valueOf(LocalDate.of(2010, 01, 01));
		String consulta = "select * from dni where fechaNacimiento>'"+fechaDateConsulta+"'";
	
		ResultSet rsConsulta = null;  
		rsConsulta = this.stmt.executeQuery(consulta);
		
		// Averiguar el numero de filas devueltas
		rsConsulta.last();
		int numFilas = rsConsulta.getRow();
		rsConsulta.first();
		
		// Ir volcando cada fila en una fila de la matriz
		String [][]datos = new String[numFilas][5];
		
		for(int fila = 0; fila < numFilas; fila++){
			datos[fila][0] = rsConsulta.getString("dni");
			datos[fila][1] = rsConsulta.getString("nombre");
			datos[fila][2] = Integer.toString(rsConsulta.getInt("numTelefono"));
			datos[fila][3] = LibFechas8.transformaFecha(
									rsConsulta.getDate("fechaNacimiento").toLocalDate().toString());
			rsConsulta.next();
		}
		
		this.crearConsulta();
		
		return datos;
	}
	
	/**
	 * --- FORMA 2 ----

	 * Método que devuelve un objeto DefaultTableModel
	 * que contiene el resultado de una consulta
	 * @return
	 * @throws SQLException
	 */
	public DefaultTableModel datosConsulta2() throws SQLException{
		// Crear la consulta, mejor parametrizarla si la fecha se le va a pedir al usuario
		java.sql.Date fechaDateConsulta = Date.valueOf(LocalDate.of(2010, 01, 01));
		String consulta = "select * from dni where fechaNacimiento>'"+fechaDateConsulta+"'";
	
		ResultSet rsConsulta = this.stmt.executeQuery(consulta);
			
		// Obtener un objeto ResultSetMetaData, para obtener informacion de la tabla
		ResultSetMetaData rsmd = rsConsulta.getMetaData();
		
		// Obtener el n�mero de columnas de esta consulta
		int numColumnas = rsmd.getColumnCount();
	
		// Crear un objeto DefaultTableModel, para guardar el resultado de la consulta
		DefaultTableModel datos = new DefaultTableModel();
		
		// Establecer los nombres de las columnas, esto hay que hacerlo antes
		// de añadir las filas de datos, sino no saldran datos ¿?
		// Primera forma
			  for (int i=1; i<=numColumnas; i++)
					datos.addColumn(rsmd.getColumnLabel(i));
		
		// Segunda forma
			//String [] nombreColumnas = {"Código","Título","Autor","Fecha","Precio"};
			//datos.setColumnIdentifiers(nombreColumnas);
		
		// Añadir los datos 	
		while (rsConsulta.next()) {
			Object [] tupla = new Object[numColumnas];
			for (int i=0; i<numColumnas; i++) {
				if (i==3) // Fecha
					tupla[i] = LibFechas8.transformaFecha(
									rsConsulta.getDate("fechaNacimiento").toLocalDate().toString());
				else
					tupla[i] = rsConsulta.getObject(i+1);
			}
		//	System.out.println("Tupla"+Arrays.toString(tupla));
			datos.addRow(tupla);
		}
		
		this.crearConsulta();
		return datos; 
	}
	
	/**
	 * Metodo que muestra una descripcion completa de la excepcion
	 * que se ha producido
	 * @param ex -- Excepcion SQL generada
	 */
	public void printSQLException(SQLException ex)
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







