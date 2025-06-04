package modelo;

import java.util.*;

public class DniColeccion {

	private SortedSet<Dni> coleccionDni;
	private Dni dni;
	
	public DniColeccion(Dni dni) {
		
		this.dni = dni;
		this.coleccionDni = new TreeSet<>();
	}

	public int compareTo(Dni otroNombre) {
		
		return this.dni.getNombre().compareTo(otroNombre.getNombre());
	}
	
	public void añadeDni(Dni dni) {
		
		this.coleccionDni.add(dni);
	}
	
	public int ordenaDni() {
		
		return this.compareTo(dni);
	}

	/*
	 * 
	 */
	public SortedSet<Dni> getColeccionDni() {
		return coleccionDni;
	}

	public Dni getDni() {
		return dni;
	}

	@Override
	public String toString() {
		return "DniColeccion [coleccionDni=" + this.coleccionDni + "]";
	}
	
	
	
	
}
