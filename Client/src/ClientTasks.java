import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.swing.JOptionPane;

public class ClientTasks {

	public static void doSecureAuthentication(SSLSocketHandle sslSocketHandle) throws IOException, KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException{
			
			
			
			String username;
			String password;
			String message;
			String response;
			
			do {
				
				
				username = JOptionPane.showInputDialog(null,
						"Insert an username:");
				password = JOptionPane.showInputDialog(null,
						"Insert a password:");
		
				
				System.out.println("Usuario y contraseña introducidos:");
				System.out.println("username: " + username);
				System.out.println("password: " + password);
				System.out.println("\n");
				
				
				message = username+" "+password;
				sslSocketHandle.write(message);
				sslSocketHandle.send();
				
		
				System.out.println("Mensaje enviado:");
				System.out.println("mensaje: " + message);
				System.out.println("\n");
				
				
				
				response = sslSocketHandle.read();
				
				
				System.out.println("Mensaje recibido:");
				System.out.println("Respuesta transaccion: " + response);
				System.out.println("\n");
				
				
				
				
				
	
			// display response to user
			JOptionPane.showMessageDialog(null, response);
			}while(!response.equals("Acceso exitoso"));
			
			sslSocketHandle.closeConnection();
			
			
		}
	
}
