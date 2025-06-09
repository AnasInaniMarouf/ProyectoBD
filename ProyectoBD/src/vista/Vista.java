package vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import controlador.Controlador;

public class Vista extends JFrame{
	
	private static final long serialVersionUID = -5752211613049689258L;

	private JButton guardar, limpiar, eliminar, abrirImagen, abrir;
	private JLabel nom, DNI, tel, fech;
	private JTextField nombre, dni, numTelefono, fecha;
	private ImageIcon icono;
	private JLabel textoImagen;
	private BufferedImage imagen;
	private JMenuBar barra;
	private JMenu archivo;
	private JMenuItem guardarArchivo, limpiarArchivo, eliminarArchivo, abrirArchivo;
	
	public Vista() {
		
		super("DNI");
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());
		
		this.setPreferredSize(new Dimension(700, 400));
		
		panelPrincipal.setBorder(new EmptyBorder(15, 20, 15, 20));
		
		this.add(panelPrincipal);
		panelPrincipal.add(this.preparaPanelNavegacion(), BorderLayout.NORTH);
		panelPrincipal.add(this.preparaPanelImagen(), BorderLayout.WEST);
		panelPrincipal.add(this.preparaPanelDatos(), BorderLayout.EAST);
	}
	
	private JPanel preparaPanelNavegacion() {
		
		JPanel panel = new JPanel();
		
		ImageIcon iconoAbrir = new ImageIcon("./src/imgs/abrir.png");
		ImageIcon iconoGuardar = new ImageIcon("./src/imgs/guardar.png");
		ImageIcon iconoLimpiar = new ImageIcon("./src/imgs/limpiar.png");
		ImageIcon iconoEliminar = new ImageIcon("./src/imgs/eliminar.png");
		
		this.barra = new JMenuBar();
		this.archivo = new JMenu("Archivo");
		this.guardarArchivo = new JMenuItem("Guardar");
		this.limpiarArchivo = new JMenuItem("Limpiar");
		this.eliminarArchivo = new JMenuItem("Eliminar");
		this.abrirArchivo = new JMenuItem("Abrir");
		//-------
		this.abrir = new JButton();
		this.guardar = new JButton();
		this.limpiar = new JButton();
		this.eliminar = new JButton();
		//-------
		this.abrir.setIcon(iconoAbrir);
		this.guardar.setIcon(iconoGuardar);
		this.limpiar.setIcon(iconoLimpiar);
		this.eliminar.setIcon(iconoEliminar);
		
		abrirArchivo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		guardarArchivo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		limpiarArchivo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		eliminarArchivo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		
		archivo.add(abrirArchivo);
		archivo.add(guardarArchivo);
		archivo.add(limpiarArchivo);
		archivo.add(eliminarArchivo);
		
		this.setJMenuBar(barra); //Añadir barra al frame
		
		//Añadir los menus a la barra de menus
		barra.add(archivo);
		//
		barra.add(Box.createHorizontalGlue()); //Separar los menus a la derecha e izquierda
		//
		barra.add(abrir);
		barra.add(guardar);
		barra.add(limpiar);
		barra.add(eliminar);
		
		return panel;
	}
	
	private JPanel preparaPanelImagen() {
		
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
	    
	    this.icono = new ImageIcon();
	    this.textoImagen = new JLabel(icono);
	    this.textoImagen.setPreferredSize(new Dimension(250, 300));
	    this.textoImagen.setHorizontalAlignment(JLabel.CENTER);
	    
	    this.abrirImagen = new JButton("Abrir imagen");
	    
	    panel.setPreferredSize(new Dimension(300, 400));
	    
	    panel.add(textoImagen, BorderLayout.CENTER);
	    panel.add(abrirImagen, BorderLayout.SOUTH);
	    
	    return panel;
	}
	
	private JPanel preparaPanelDatos() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new TitledBorder("Datos"));
		
		this.nom = new JLabel("Nombre: ");
		this.DNI = new JLabel("Documento de Identidad: ");
		this.tel = new JLabel("Número de teléfono: ");
		this.fech = new JLabel("Fecha de Nacimiento: ");
		
		this.nombre = new JTextField();
		this.dni = new JTextField();
		this.numTelefono = new JTextField();
		this.fecha = new JTextField();
		
		panel.setPreferredSize(new Dimension(300, 200));
		
		panel.add(nom);
		panel.add(nombre);
		panel.add(DNI);
		panel.add(dni);
		panel.add(tel);
		panel.add(numTelefono);
		panel.add(fech);
		panel.add(fecha);
		
		return panel;
	}
	
	public void seleccionarImagen() throws IOException {

		JFileChooser seleccionarImagen = new JFileChooser();
		
		int devuelveValor = seleccionarImagen.showOpenDialog(this);
	    
	    if (devuelveValor == JFileChooser.APPROVE_OPTION) {
	    	
	        File selectedFile = seleccionarImagen.getSelectedFile();
	        imagen = ImageIO.read(selectedFile);
	        
	        Image imagenEscalada = imagen.getScaledInstance(textoImagen.getWidth(), textoImagen.getHeight(), Image.SCALE_SMOOTH);
	        
	        icono = new ImageIcon(imagenEscalada);
	        textoImagen.setIcon(icono);
	    }
	}
	
	public void control(Controlador ctr) {
		
		this.abrirImagen.addActionListener(ctr);
		this.abrir.addActionListener(ctr);
		this.guardar.addActionListener(ctr);
		this.limpiar.addActionListener(ctr);
		this.eliminar.addActionListener(ctr);
		
		this.abrirArchivo.addActionListener(ctr);
		this.guardarArchivo.addActionListener(ctr);
		this.limpiarArchivo.addActionListener(ctr);
		this.eliminarArchivo.addActionListener(ctr);
	}
	

	/*
	 * 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JButton getGuardar() {
		return guardar;
	}

	public JButton getLimpiar() {
		return limpiar;
	}

	public JButton getEliminar() {
		return eliminar;
	}

	public JButton getSeleccionarImagen() {
		return abrirImagen;
	}

	public JTextField getNombre() {
		return nombre;
	}

	public JTextField getDni() {
		return dni;
	}

	public JTextField getNumTelefono() {
		return numTelefono;
	}

	public JTextField getFecha() {
		return fecha;
	}

	public ImageIcon getImagen() {
		return icono;
	}

	public JLabel getTextoImagen() {
		return textoImagen;
	}

	public JButton getAbrirImagen() {
		return abrirImagen;
	}

	public JLabel getNom() {
		return nom;
	}

	public JLabel getDNI() {
		return DNI;
	}

	public JLabel getTel() {
		return tel;
	}

	public JLabel getFech() {
		return fech;
	}

	public ImageIcon getIcono() {
		return icono;
	}

	public JMenuBar getBarra() {
		return barra;
	}

	public JMenu getArchivo() {
		return archivo;
	}


	public JMenuItem getGuardarArchivo() {
		return guardarArchivo;
	}

	public JMenuItem getLimpiarArchivo() {
		return limpiarArchivo;
	}

	public JMenuItem getEliminarArchivo() {
		return eliminarArchivo;
	}

	public JMenuItem getAbrirArchivo() {
		return abrirArchivo;
	}

	public JButton getAbrir() {
		return abrir;
	}
	
	
	
	
}
