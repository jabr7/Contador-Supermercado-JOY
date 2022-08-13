package programa;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {

	static Connection connection = null;
	static String databaseName = "joy";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName;

	static String username = "root";
	static String password = "Control_pp7";

	public static void DBInsertar(String instrucion) {

		try {
			connection = DriverManager.getConnection(url, username, password);
			DriverManager.getConnection(url, username, password);
			PreparedStatement ps = connection.prepareStatement(instrucion);
			ps.executeUpdate();
			System.out.println("Se ejecuto correctamente");

		} catch (Exception e) {
			System.out.println("No se pudo ejecutar la instruccion");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("No se pudo cerrar la conexion");
				e.printStackTrace();
			}
		}

	}

	public static String DBSeleccionar(String instrucion) {
		ResultSet rs;
		String respuesta = "";
		try {
			connection = DriverManager.getConnection(url, username, password);
			DriverManager.getConnection(url, username, password);
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(instrucion);

			while (rs.next()) {
				respuesta = rs.getString(1);
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return respuesta;

	}

	public static boolean DBPrendida() {
		ResultSet rs;
		boolean hubo = false;
		String instrucion = "SELECT settingscol FROM joy.settings WHERE idsettings='Tiempo de Promocion';";
		try {
			connection = DriverManager.getConnection(url, username, password);
			DriverManager.getConnection(url, username, password);
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(instrucion);

			if (rs.next()) {
				hubo = true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return hubo;

	}

}
