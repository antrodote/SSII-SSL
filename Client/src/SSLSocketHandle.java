import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SSLSocketHandle {

	
	private SSLSocket socket;
	private PrintWriter output;
	private BufferedReader input;
	private static String key = "clientPass";
	private static final String[] protocols = new String[] {"TLSv1.3"};
	private static final String[] cipher_suite = new String[] {"TLS_AES_128_GCM_SHA256"};		
	
	//TLS solo admite estos dos cipher_suite
	//private static final String[] cipher_suite = new String[] {"TLS_AES_128_GCM_SHA256"};		
	//private static final String[] cipher_suite = new String[] {"TLS_AES_256_GCM_SHA384"};		
			
	
	
	public SSLSocketHandle(String serverIp,int port) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException, KeyManagementException {
		
				//Almacenes de confianza
				KeyStore keyStoreTM = KeyStore.getInstance("JKS");
				keyStoreTM.load(new FileInputStream("./certs/clientTrustedCerts.jks"), key.toCharArray());
				TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
				tmf.init(keyStoreTM);
				TrustManager[] trustManagers = tmf.getTrustManagers();
				//Fin Almacenes de confianza
				
				//Certificados
				KeyStore keyStore = KeyStore.getInstance("JKS");
				keyStore.load(new FileInputStream("./certs/clientKey.jks"), key.toCharArray());
				
				KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
				kmf.init(keyStore,key.toCharArray());
				KeyManager[] keyManagers = kmf.getKeyManagers();
				//Fin Certificados
				
				SSLContext sc = SSLContext.getInstance("TLS");
				sc.init(keyManagers, trustManagers, null);
				SSLSocketFactory ssf = sc.getSocketFactory();
				
				socket = (SSLSocket) ssf.createSocket(serverIp, port);
				socket.setEnabledProtocols(protocols);
				socket.setEnabledCipherSuites(cipher_suite);
				socket.startHandshake();
				output = new PrintWriter(new OutputStreamWriter(
				socket.getOutputStream()));
				input = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));

	}
	
	
	public void write(String message) {

		if(this.socket.isConnected()) {
			this.output.println(message);
		}
	}
	
	
	public String read() throws IOException {
		
		String res = "";
		if(this.socket.isConnected()) {
			res = this.input.readLine();
		}
		return res;
	}
	
	
	public void send() {
		
		if(this.socket.isConnected()) {
			this.output.flush();
		}
	}
	
	public void closeConnection() throws IOException {
		
		if(!this.socket.isClosed()) {
			this.output.close();
			this.input.close();
			this.socket.close();
		}
		
	}
	
}
