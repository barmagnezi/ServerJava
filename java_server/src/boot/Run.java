package boot;

import java.util.Scanner;

import java_server.ClientHandlerStub;
import java_server.MyServer;


public class Run {

	public static void main(String[] args) {
		//For server side
		
		System.out.println("Enter command\nStart <port-number> <number-of-client>");
		int port,num;
		String s;
		Scanner in = new Scanner(System.in);
	    s = in.nextLine();
	    String[] ServerProp = s.split(" ");
		MyServer serv=new MyServer(Integer.parseInt(ServerProp[0]),5000000, Integer.parseInt(ServerProp[1]));
		ClientHandlerStub CH = new ClientHandlerStub();
		try {
			serv.Start(CH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
