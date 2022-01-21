import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;




public class SSLSocketHandle {

	private SSLServerSocket serverSocket;
	private static String key = "servPass";
	private static final String[] protocols = new String[] {"TLSv1.3"};
	private static final String[] cipher_suite = new String[] {"TLS_AES_128_GCM_SHA256"};		
	
	//TLSv1.3 solo admite estos dos cipher_suite
	//private static final String[] cipher_suite = new String[] {"TLS_AES_128_GCM_SHA256"};		
	//private static final String[] cipher_suite = new String[] {"TLS_AES_256_GCM_SHA384"};		
	
	
	public SSLSocketHandle(int port) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException, KeyManagementException {
		
		
				//Almacenes de confianza
				KeyStore keyStoreTM = KeyStore.getInstance("JKS");
				keyStoreTM.load(new FileInputStream("./certs/serverTrustedCerts.jks"), key.toCharArray());
				
				TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
				tmf.init(keyStoreTM);
				TrustManager[] trustManagers = tmf.getTrustManagers();
				//Fin Almacenes de confianza
						
				//Certificados
				KeyStore keyStore = KeyStore.getInstance("JKS");
				keyStore.load(new FileInputStream("./certs/serverKey.jks"), key.toCharArray());
				
				KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
				kmf.init(keyStore,key.toCharArray());
				KeyManager[] keyManagers = kmf.getKeyManagers();
				//Fin Certificados
								
				SSLContext sc = SSLContext.getInstance("TLS");
				sc.init(keyManagers, trustManagers, null);
				SSLServerSocketFactory ssf = sc.getServerSocketFactory();	
				this.serverSocket = (SSLServerSocket) ssf.createServerSocket(port);   
				this.serverSocket.setEnabledProtocols(protocols);
				this.serverSocket.setEnabledCipherSuites(cipher_suite);

		
	}
	
	

	
	public SSLSocket accepConnection() throws IOException {
		
		
		return this.serverSocket != null && !this.serverSocket.isClosed() ? (SSLSocket)this.serverSocket.accept() : null;

		
	}
	
	public void closeServer() throws IOException {
		
		if(this.serverSocket != null && !this.serverSocket.isClosed()) {
			this.serverSocket.close();
			
		}
		
	}
	

	
}
