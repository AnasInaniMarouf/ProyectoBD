package modelo;

import java.time.LocalDate;
import fechas.LibFechas8;

public class Dni implements Comparable<Dni>{

	/**
	 * Variables de instancia
	 */
	private String dni;
	private String nombre;
	private int numTelefono;
	private LocalDate fechaNacimiento;

	
	/**
	 * Constructor
	 * @throws MiExcepcion 
	 */
	public Dni(String dni, String nombre, int numTelefono, String fecha) 
			       throws MiExcepcion {
		
		if (!this.validarNif(dni)) {

			throw new MiExcepcion("DNI no válido");
		}
		
		this.dni = dni;

		if (numTelefono > 999999999 || numTelefono < 99999999) {

			throw new MiExcepcion("Número de teléfono no válido");
		}
		
		this.numTelefono = numTelefono;

		if (nombre.isEmpty()) {
			
			throw new MiExcepcion("Nombre no válido");
		}
		this.nombre = nombre;
		
		if (!LibFechas8.isFechaCorrecta(fecha)) {
			
			throw new MiExcepcion("Fecha no válida");
		}
		
		this.fechaNacimiento = LibFechas8.convierteStringToLocalDate(fecha);
	}
	
	public boolean validarNif(String nif) {

		int num=0;
		char letra=' ';

		try {
			num=Integer.parseInt(nif.substring(0,8));

			char[] arrayChar = new char[1];
			nif.substring(8).getChars(0, 1, arrayChar, 0);

			letra=arrayChar[0];

			char[] letrasNif = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 
					'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 
					'C', 'K', 'E', 'T'};

			if(letra!=letrasNif[num%23]) {
				
				//JOptionPane.showMessageDialog(this.vista, "Letra final correspondiente: "
						//+letrasNif[num%23], "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}

		}catch(NumberFormatException | StringIndexOutOfBoundsException e) {
			return false;
		}

		return true;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getNumTelefono() {
		return numTelefono;
	}


	public void setNumTelefono(int numTelefono) {
		this.numTelefono = numTelefono;
	}


	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	@Override
	public String toString() {
		return "Dni [dni=" + dni + ", nombre=" + nombre + ", numTelefono=" + numTelefono + ", fechaNacimiento="
				+ fechaNacimiento + "]";
	}



	@Override
	public int compareTo(Dni otroNombre) {
		
		return this.getNombre().compareTo(otroNombre.getNombre());
	}


	
}




