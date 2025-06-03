package controlador;

import java.awt.event.*;
import java.io.*;

import vista.Vista;

public class Controlador implements ActionListener{

	private Vista vista;
	
	public Controlador(Vista v) {
		
		this.vista = v;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == vista.getSeleccionarImagen()) {
			
			try {
				vista.seleccionarImagen();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == vista.getGuardar()) {
			
			
		}
		
	}
}
