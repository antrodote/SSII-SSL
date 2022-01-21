import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BDHandle {

	private static String driver = "com.mysql.jdbc.Driver";
	private static String database = "dbSSIIMITH"; // MITHM = Man-in-the-middle
	private static String hostname = "localhost";
	private static String port = "3306";
	private static String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
	private static String username = "root";
	private static String password = "";
	private static Connection conn;
	
	public static void init() {
		
		try {
			
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			
			
			
		}catch (Exception e) {
			System.out.println("No se pudo abrir conexión con la BD Mysql");
			System.out.println(e.getMessage());
		}
		
	}
	
	public static boolean existThisOnce(String NONCE,boolean cs) {
		
		boolean exist = false;
		try {
			
		
		String query = "SELECT * FROM nonce WHERE nonce = ? AND cs = ?";
		PreparedStatement sentencia= conn.prepareStatement(query);
		sentencia.setString(1, NONCE);
		sentencia.setInt(2,cs? 1 : 0);
		ResultSet rs = sentencia.executeQuery();
		exist = rs.next();
		}catch (Exception e) {
			System.out.println("No se puedo realizar la consulta a BD");
			System.out.println(e.getMessage());
		}
		
		return exist;
		
	}
	
	public static boolean existThisUserAndPass(String username,String password) {
		
		boolean exist = false;
		try {
			
		
		String query = "SELECT * FROM cliente WHERE username = ? AND password = ?";
		PreparedStatement sentencia= conn.prepareStatement(query);
		sentencia.setString(1, username);
		sentencia.setString(2,password);
		ResultSet rs = sentencia.executeQuery();
		exist = rs.next();
		}catch (Exception e) {
			System.out.println("No se puedo realizar la consulta a BD");
			System.out.println(e.getMessage());
		}
		
		return exist;
		
	}
	
	
	public static boolean insertarTransaccion(String NONCE,boolean cs) {
		
		
		boolean res = false;
		boolean q1 = false;
		try {
			
		
		String query = "INSERT INTO nonce VALUES (?,?)";
		PreparedStatement sentencia= conn.prepareStatement(query);
		sentencia.setString(1,NONCE);
		sentencia.setInt(2,cs? 1:0);
		q1 = sentencia.execute();
		
		
		
		
		
		}catch (Exception e) {
			System.out.println("No se puedo realizar la insercion a BD");
			System.out.println(e.getMessage());
		}
		
		res = q1;
		return res;
		

	}
	
	public static boolean insertarLog(String transaccion,int success,int integrityFail,int replyDetect) {
		
		
		boolean res = false;
		boolean q1 = false;
		try {
			
		
		String query = "INSERT INTO log VALUES (?,?,?,?)";
		PreparedStatement sentencia= conn.prepareStatement(query);
		sentencia.setString(1,transaccion);
		sentencia.setInt(2,success);
		sentencia.setInt(3,integrityFail);
		sentencia.setInt(4,replyDetect);
		q1 = sentencia.execute();
		
		
		
		
		
		}catch (Exception e) {
			System.out.println("No se puedo realizar la insercion a BD");
			System.out.println(e.getMessage());
		}
		
		res = q1;
		return res;
		

	}
		
	public static boolean close() {
		boolean res = false;
		try {
			conn.close();
			res = true;
		} catch (Exception e) {
			System.out.println("Error al cerrar la conexion con BD");
			System.out.println(e.getMessage());
		}
		return res;
		
	}
	
}
