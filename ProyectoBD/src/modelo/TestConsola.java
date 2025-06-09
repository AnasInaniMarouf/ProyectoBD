package modelo;

import java.sql.SQLException;

public class TestConsola {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, MiExcepcion {
		Dni d1 = null;
		// -----------------------------------------------
		// Prueba clase disco
		try {
			
			d1 = new Dni("10294129F","Antonio",621293541, "19/11/2004");
			System.out.println(d1);
			
		} catch (MiExcepcion e) {
			
			//e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		}

		System.out.println(d1);
		
		System.out.println("-------------");
		
	}

}
