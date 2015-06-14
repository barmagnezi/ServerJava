package java_server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import model.OffLineModel;

public interface ClientHandler {
	
	public void HandleClient(InputStream input,OutputStream outputStream);
}
