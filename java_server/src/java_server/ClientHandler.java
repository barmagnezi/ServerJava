package java_server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public interface ClientHandler {
	
	public void HandleClient(InputStream input,OutputStream outputStream);
	public ClientHandler CreateNewClientHandler();
}
