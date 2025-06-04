package modelo;

public class TestConsola {

	public static void main(String[] args) {
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
		
		DniColeccion dni = new DniColeccion(d1);
		
		dni.añadeDni(d1);
		
		System.out.println(dni.toString());
		System.out.println(dni.compareTo(d1));
	}

}
