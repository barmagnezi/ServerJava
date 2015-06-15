package java_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;


public class ClientHandlerStub implements ClientHandler {

	@Override
	public void HandleClient(InputStream input, OutputStream outputStream) {
		PrintStream writer=new PrintStream(outputStream);
		BufferedReader reader=new BufferedReader(new InputStreamReader(input));
		while(true){
			try {
				String line=reader.readLine();
				System.out.println("The client send:"+line+"\n");
				if(line.equals("goodbye")){
					System.out.println("The client end the session"+"\n");
					outputStream.write("goodbye".getBytes());
					outputStream.flush();
				}
				System.out.println("write ok");
				writer.println("Ok");
				outputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newProp(String path) {
		// TODO Auto-generated method stub
		
	}


}