package almacen_package;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class Frm_clientes extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_documento;
	private JTextField txt_nombre;
	private JTextField txt_telefono;
	
	private JButton btn_agregar;
	private JButton btn_eliminar;
	private JButton btn_editar;
	
	DefaultTableModel modelo;
	private JTable table;
	private JButton btn_contactos;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frm_clientes frame = new Frm_clientes();
					frame.setVisible(true);
					
					frame.contactos();   // Mostrar los contactos al iniciar
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Frm_clientes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 200, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Agenda Telefónica");
		
		
		// ---------------- Labels
		
		JLabel lbl_documento = new JLabel("Documento");
		lbl_documento.setBounds(75, 31, 87, 16);
		contentPane.add(lbl_documento);
		
		JLabel lbl_nombre = new JLabel("Nombre");
		lbl_nombre.setBounds(75, 62, 87, 16);
		contentPane.add(lbl_nombre);
		
		JLabel lbl_telefono = new JLabel("Teléfono");
		lbl_telefono.setBounds(75, 96, 87, 16);
		contentPane.add(lbl_telefono);
		
		// ---------------- campos de texto
		
		txt_documento = new JTextField();
		txt_documento.setBounds(174, 26, 208, 26);
		contentPane.add(txt_documento);
		txt_documento.setColumns(10);
		
		
		txt_nombre = new JTextField();
		txt_nombre.setColumns(10);
		txt_nombre.setBounds(174, 57, 208, 26);
		contentPane.add(txt_nombre);
		
		txt_telefono = new JTextField();
		txt_telefono.setColumns(10);
		txt_telefono.setBounds(174, 91, 208, 26);
		contentPane.add(txt_telefono);
		
		// ----------------------------------------------------------------------- botones
		
		btn_agregar = new JButton("Agregar");         					// Boton Agregrar
		btn_agregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Conexion conexion_1 = new Conexion();
				Connection con = conexion_1.conectar();
				
				String documento = txt_documento.getText();
				String nombre = txt_nombre.getText();
				String telefono = txt_telefono.getText();
				
				try {
					PreparedStatement pst = con.prepareStatement("INSERT INTO Clientes VALUES(?, ?, ?)");
					pst.setString(1, documento);
					pst.setString(2, nombre);
					pst.setString(3, telefono);
					pst.executeUpdate();
					
					contactos();
					limpiar();
					con.close();
					
				} catch (SQLException ex) {
					System.out.println("Error en conexion.java...");
				}
				
			}
		});
		btn_agregar.setBounds(20, 148, 117, 29);
		contentPane.add(btn_agregar);
		
		// -------------------------------------------------------------------
		
		btn_eliminar = new JButton("Eliminar");							// Boton Eliminar
		btn_eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Conexion conexion_1 = new Conexion();
				Connection con = conexion_1.conectar();
				
				String documento = txt_documento.getText();
						
				
				try {
					
					// Confirmar eliminacion de registro
					int confirmacion = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar este registro?"); 
					if(confirmacion == 0) {
					PreparedStatement pst = con.prepareStatement("DELETE FROM Clientes WHERE documento = ?");
					pst.setString(1, documento);
					pst.executeUpdate();
					limpiar();
					contactos();
					}
					con.close();
					
				} catch (Exception e2) {
					System.out.println(e2);
				}
				
				
				
			}
		});
		btn_eliminar.setBounds(149, 148, 117, 29);
		contentPane.add(btn_eliminar);
		
		// -------------------------------------------------------------------
		
		btn_editar = new JButton("Editar");										// Editar
		btn_editar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Conexion conexion_1 = new Conexion();
				Connection con = conexion_1.conectar();
				
				String documento = txt_documento.getText();
				String nombre = txt_nombre.getText();
				String telefono = txt_telefono.getText();
				
				try {
					PreparedStatement pst = con.prepareStatement("UPDATE Clientes SET nombre = ?, telefono = ? WHERE documento = ?");
					pst.setString(1, nombre);
					pst.setString(2, telefono);
					pst.setString(3, documento);
					pst.executeUpdate();
					
					contactos();
					limpiar();
					con.close();
					
				} catch (SQLException ex) {
					System.out.println("Error en conexion.java...");
				}
				
			}
		});
		btn_editar.setBounds(278, 148, 117, 29);
		contentPane.add(btn_editar);
		
		// -------------------------------------------------------------------
		
		JButton btn_cancelar = new JButton("Cancelar");							// Cancelar
		btn_cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				limpiar();
				contactos();
				
				
			}
		});
		btn_cancelar.setBounds(407, 148, 117, 29);
		contentPane.add(btn_cancelar);
		
		// -------------------------------------------------------------------
		
		JButton btn_buscar = new JButton("Buscar");								// Buscar
		btn_buscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String busqueda_documento = JOptionPane.showInputDialog(null, "Ingrese el número de Cédula");
				
				
				while(modelo.getRowCount() > 0) {  				// Limpia la tabla antes de mostrar todos los datos incluyendo el nuevo
					modelo.removeRow(0);
				}
				
				Conexion conexion_1 = new Conexion();
				Connection con = conexion_1.conectar();
				
				try {
					Statement st = con.createStatement();
					ResultSet datos = st.executeQuery("SELECT * FROM Clientes WHERE documento = " + busqueda_documento);
					
					if(datos.next()) {

						Object[] oUsuario = {datos.getString(1), datos.getString(2), datos.getString(3)}; // Numero de la columna
						modelo.addRow(oUsuario);
					} else {
						JOptionPane.showMessageDialog(null, "No se encontró ese documento");
					}
					
					con.close();
					
				} catch (SQLException e2) {
					System.out.println(e2);
				}
				
				
			}
		});
		btn_buscar.setBounds(407, 91, 117, 29);
		contentPane.add(btn_buscar);
		
		
		// -------------------------------------------------------------------
		
		
		JScrollPane scrollPane = new JScrollPane();				// Container de JTable
		scrollPane.setBounds(30, 190, 489, 161);
		contentPane.add(scrollPane);
		
		
		table = new JTable(modelo);								// Jtable
		
		
		
		
		table.addMouseListener(new MouseAdapter() { 			// Mostrar un registro seleccionado en los txt_fields
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(e.getClickCount()==1) {
					JTable receptor = (JTable)e.getSource();
					
					txt_documento.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 0).toString());
					txt_nombre.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 1).toString());
					txt_telefono.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 2).toString());
				}
				btn_agregar.setEnabled(false);
				btn_editar.setEnabled(true);
				btn_eliminar.setEnabled(true);
			}
		});
		
		
		
		
		String[] titulos = {"Documento", "Nombre", "Teléfono"};         			// JTable
		modelo = new DefaultTableModel(null, titulos);
		scrollPane.setViewportView(table);
		table.setModel(modelo);
		
		// ---------------------------------------------------
		
		btn_contactos = new JButton("Contactos");
		btn_contactos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				contactos();
				
			}
		});
		btn_contactos.setBounds(407, 57, 117, 29);
		contentPane.add(btn_contactos);
		
		
	}
	
	public void contactos() {							// Muestra los datos de la db
		
		while(modelo.getRowCount() > 0) {  				// Limpia la tabla antes de mostrar todos los datos incluyendo el nuevo
			modelo.removeRow(0);
		}
		
		Conexion conexion_1 = new Conexion();
		
		Connection con = conexion_1.conectar();
		
		try {
			Statement st = con.createStatement();
			ResultSet datos = st.executeQuery("SELECT * FROM Clientes");
			
			while(datos.next()) {

				Object[] oUsuario = {datos.getString(1), datos.getString(2), datos.getString(3)}; // Numero de la columna
				modelo.addRow(oUsuario);
			}
			con.close();
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}
	
	public void limpiar() {            				// Limpia los campos de texto
		txt_documento.setText("");
		txt_nombre.setText("");
		txt_telefono.setText("");
		txt_documento.requestFocus();
		
		btn_agregar.setEnabled(true);
		btn_editar.setEnabled(false);
		btn_eliminar.setEnabled(false);
	}
}
