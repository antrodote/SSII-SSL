
public class EntryPoint {

	public static void main(String[] args) {
		
		
		try {
			
			SSLSocketHandle sslSocketHandle = new SSLSocketHandle("127.0.0.1",3343);
			ClientTasks.doSecureAuthentication(sslSocketHandle);
			
			
		} catch (Exception e) {
			e.getStackTrace();
		}
		
	}

}
