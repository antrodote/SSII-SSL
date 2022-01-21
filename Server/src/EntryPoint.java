import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLSocket;

public class EntryPoint {

	public static void main(String[] args) throws KeyManagementException, UnrecoverableKeyException, UnknownHostException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
		
		
		SSLSocketHandle sslSocketHandle = new SSLSocketHandle(3343);
		BDHandle.init();
		
		while(true) {
			
			try {
				
				System.err.println("Waiting for connection...");
				SSLSocket connection = sslSocketHandle.accepConnection();
				System.err.println("Conexión de cliente aceptada procediendo a delegar en un hilo");
				new Thread(new ConnectionThread(connection)).start();
				
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println(e.getMessage());
			}
			
			
			
		}
		
		
		
	}

}
