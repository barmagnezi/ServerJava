package sModel;

import java.io.InputStream;
import java.io.OutputStream;

/**
* The ClientHandler interface is a basic interface for handeling a client, used by the server.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 16.6.2015
*/
public interface ClientHandler {
	
	public void HandleClient(InputStream input,OutputStream outputStream);
	public void close();
	public void newProp(String path);
}
