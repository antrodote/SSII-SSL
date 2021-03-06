import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.ssl.SSLSocket;

public class ConnectionThread implements Runnable{

	// new Thread(new ConnectionThread(socket)).start(); //Llamada para ejecutar el codigo de run() mediante el uso de hilos
	
	private SSLSocket socket;
	private PrintWriter output;
	private BufferedReader input;
	
	public ConnectionThread(SSLSocket socket) throws IOException {
		
		
		this.socket = socket;
		output = new PrintWriter(new OutputStreamWriter(
				this.socket.getOutputStream()));
		input = new BufferedReader(new InputStreamReader(
							this.socket.getInputStream()));
		
		
	}
	
	@Override
	public void run() {
		
		try {
			ServerTasks.doSecureAuthentication(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
