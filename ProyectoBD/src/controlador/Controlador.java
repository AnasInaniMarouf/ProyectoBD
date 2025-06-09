package controlador;

import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import modelo.DAODni;
import modelo.Dni;
import modelo.MiExcepcion;
import vista.Vista;

public class Controlador implements ActionListener{

	private Vista vista;
	private DAODni daoDni;
	
	public Controlador(Vista v) {
		
		this.vista = v;
		
		try {
			
			this.daoDni = new DAODni();
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
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
		
		if (e.getSource() == vista.getAbrir() || e.getSource() == vista.getAbrirArchivo()) {
			
			//vista.setTitle("1");
		}
		
		if (e.getSource() == vista.getGuardar() || e.getSource() == vista.getGuardarArchivo()) {
			
			//vista.setTitle("2");
			
			try {
				
				Dni dni = new Dni(vista.getDni().getText(), vista.getNombre().getText(), Integer.parseInt(vista.getNumTelefono().getText()), vista.getFecha().getText());
				
				this.daoDni.insertaDni(dni);
				
				JOptionPane.showMessageDialog(vista, "El dni se ha guardado correctamente", "ÉXITO",JOptionPane.INFORMATION_MESSAGE);
				
			} catch (NumberFormatException e1) {
			
				JOptionPane.showMessageDialog(vista, "El número de teléfono no es válido", "ERROR", JOptionPane.ERROR_MESSAGE);
				
			} catch (MiExcepcion e1) {
				
				JOptionPane.showMessageDialog(vista, e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			
			
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
			
			String dni = JOptionPane.showInputDialog("Introduzca el dni de la persona a la que quieras eliminar");

			if (dni != null) {

				if (JOptionPane.showConfirmDialog(vista, "¿Estás seguro que quieres eliminar el usuario?") == JOptionPane.YES_OPTION) {

					try {
						
						this.daoDni.eliminaDni(dni);
						JOptionPane.showMessageDialog(vista, "El dni se ha eliminado correctamente");
						
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				} else {
					
					JOptionPane.showMessageDialog(vista, "El dni no se ha eliminado");
				}

			}

			
			
		}
		
	}
}
