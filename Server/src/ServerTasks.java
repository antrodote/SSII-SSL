import java.io.IOException;
import java.net.Socket;

public class ServerTasks {

	public static void doSecureAuthentication(ConnectionThread connectionThread) throws IOException {
		
		String usernameAndPassword;
		String username = "";
		String password = "";
		String authenticationResponse;
		
		
		do {
			
			
			usernameAndPassword = connectionThread.read();
			String[] loginData = usernameAndPassword.split(" ");
			if(loginData.length == 2) {
				username = loginData[0];
				password = loginData[1];
			}
			System.out.println("Mensaje recibido:");
			System.out.println("username and password: " + usernameAndPassword);
			System.out.println("\n");
			
			if(!BDHandle.existThisUserAndPass(username, password)) {
				authenticationResponse = "Datos de login incorrectos";
				connectionThread.write(authenticationResponse);
				connectionThread.send();
				
			}
		
			
		
		}
		while(!BDHandle.existThisUserAndPass(username, password));
		
		
		authenticationResponse = "Acceso exitoso";
		connectionThread.write(authenticationResponse);
		connectionThread.send();
		connectionThread.closeConnection();
		
		
		
		
	}
	
}
