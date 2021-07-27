package almacen_package;

import java.sql.*;



public class Conexion {
	
	Connection conectar = null;
	
	public Connection conectar() {
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			conectar = DriverManager.getConnection("jdbc:sqlite:almacen.db");
			
			
		} catch (Exception e) {
			
			System.out.println("Error al conectar...");
		}
		
		return conectar;
	}

}
