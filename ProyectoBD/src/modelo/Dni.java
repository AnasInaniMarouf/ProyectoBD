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
		super();
		
		if (!LibFechas8.isFechaCorrecta(fecha))  // Validar que la fecha sea correcta
			throw new MiExcepcion("Fecha no válida");
		
		this.dni = dni;
		this.nombre = nombre;
		this.numTelefono = numTelefono;
		this.fechaNacimiento = LibFechas8.convierteStringToLocalDate(fecha);
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




