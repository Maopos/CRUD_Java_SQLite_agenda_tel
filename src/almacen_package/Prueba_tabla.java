package almacen_package;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Prueba_tabla extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prueba_tabla frame = new Prueba_tabla();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Prueba_tabla() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 200, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 173, 470, 175);
		contentPane.add(scrollPane);
		
	
		String[] titulos = {"Documento", "Nombre", "Teléfono", "Email"};     // Tabla - Primero incluir absoluteLayout y JScrollPane
		DefaultTableModel modelo = new DefaultTableModel(null, titulos);
		JTable table = new JTable(modelo);					
		scrollPane.setViewportView(table);
		table.setModel(modelo);
	}

}
