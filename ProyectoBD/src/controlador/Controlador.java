package controlador;

import java.awt.event.*;
import java.io.*;

import vista.Vista;

public class Controlador implements ActionListener{

	private Vista vista;
	
	public Controlador(Vista v) {
		
		this.vista = v;
	} //

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == vista.getSeleccionarImagen()) {
			
			try {
				vista.seleccionarImagen();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		//
		
		if (e.getSource() == vista.getAbrir() || e.getSource() == vista.getAbrirArchivo()) {
			
			//vista.setTitle("1");
		}
		
		if (e.getSource() == vista.getGuardar() || e.getSource() == vista.getGuardarArchivo()) {
			
			//vista.setTitle("2");
		}
		
		if (e.getSource() == vista.getLimpiar() || e.getSource() == vista.getLimpiarArchivo()) {
			
			//vista.setTitle("3");
			
			vista.getNombre().setText(null);
			vista.getDni().setText(null);
			vista.getNumTelefono().setText(null);
			vista.getFecha().setText(null);
		}
		
		if (e.getSource() == vista.getEliminar() || e.getSource() == vista.getEliminarArchivo()) {
			
			//vista.setTitle("4");
		}
		
	}
}
