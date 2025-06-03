package modelo;

import javax.swing.*;

import controlador.Controlador;
import vista.Vista;

public class Main {

	public static void main(String[] args) {

		Vista vista = new Vista();
		Controlador ctr = new Controlador(vista);
		
		vista.control(ctr);
		
		vista.pack();
		vista.setVisible(true);
		vista.setResizable(false);
		vista.setLocationRelativeTo(null);
		vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
