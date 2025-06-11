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
		
		if (e.getSource() == vista.getAbrir()) {
			
			//vista.setTitle("1");
			
			
		}
		
		if (e.getSource() == vista.getGuardar()) {
			
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

				JOptionPane.showMessageDialog(vista, "Este DNI ya existe", "ERROR", JOptionPane.ERROR_MESSAGE);
				
			}
			
			
		}
		
		if (e.getSource() == vista.getLimpiar()) {
			
			//vista.setTitle("3");
			
			vista.getNombre().setText(null);
			vista.getDni().setText(null);
			vista.getNumTelefono().setText(null);
			vista.getFecha().setText(null);
		}
		
		if (e.getSource() == vista.getEliminar() ) {
			
			//vista.setTitle("4");
			
			String dni = JOptionPane.showInputDialog("Introduzca el dni de la persona a la que quieras eliminar");

			if (dni != null) {

				if (JOptionPane.showConfirmDialog(vista, "¿Estás seguro que quieres eliminar el usuario?") == JOptionPane.YES_OPTION) {

					try {
						
						this.daoDni.eliminaDni(dni);
						JOptionPane.showMessageDialog(vista, "El dni se ha eliminado correctamente");
						vista.getNombre().setText(null);
						vista.getDni().setText(null);
						vista.getNumTelefono().setText(null);
						vista.getFecha().setText(null);
						
					} catch (NumberFormatException e1) {

						e1.printStackTrace();
						
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
					
				} else {
					
					JOptionPane.showMessageDialog(vista, "El dni no se ha eliminado");
				}

			}

			
		}

		if (e.getSource() == vista.getPrimero()) {

			try {

				this.muestraDisco(this.daoDni.getPrimero());

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(vista, "No hay ningun DNI para cargar");
			} catch (MiExcepcion e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (e.getSource() == vista.getAnterior()) {

			try {

				this.muestraDisco(this.daoDni.getAnterior());

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				try {
					this.daoDni.getSiguiente();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(vista, "No hay ningun DNI para cargar");
				} catch (MiExcepcion e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			} catch (MiExcepcion e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == vista.getSiguiente()) {

			try {

				this.muestraDisco(this.daoDni.getSiguiente());

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				try {
					this.daoDni.getAnterior();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(vista, "No hay ningun DNI para cargar");
				} catch (MiExcepcion e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			} catch (MiExcepcion e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == vista.getUltimo()) {

			try {

				this.muestraDisco(this.daoDni.getUltimo());

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(vista, "No hay ningun DNI para cargar");
			} catch (MiExcepcion e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	//Metodo para cargar dnis
	private void muestraDisco(Dni ob) {

		this.vista.getNombre().setText(ob.getNombre());
		this.vista.getDni().setText(ob.getDni());
		this.vista.getNumTelefono().setText((ob.getNumTelefono() + ""));
		this.vista.getFecha().setText(ob.getFechaNacimiento() + "");
	}
	
	
}
