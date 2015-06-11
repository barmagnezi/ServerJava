package boot;

import java.util.Scanner;

import sModel.SModel;
import model.OffLineModel;
import sPresenter.Presenter;
import sView.serverWindow;
import java_server.ClientHandlerStub;
import java_server.MazeServer;
import java_server.MyServer;


public class Run {
	public static void main(String[] args) {
		//For server side
		
		//Add that conatin before
		//Model setting
		/*System.out.println("Enter command\nStart <port-number> <number-of-client>");
		int port,num;
		String s;
		Scanner in = new Scanner(System.in);
	    s = in.nextLine();
	    String[] ServerProp = s.split(" ");*/
		MazeServer m=new MazeServer(0,5000000, 0);
		//View setting
		serverWindow v = new serverWindow("Server", 600, 600);
		Presenter p=new Presenter(v, m); 
		MyServer m2 = new MyServer(0, 0, 0);
		v.addObserver(p);
		//m.addObserver(p);
		v.start();
		
		//before
		/*System.out.println("Enter command\nStart <port-number> <number-of-client>");
		int port,num;
		String s;
		Scanner in = new Scanner(System.in);
	    s = in.nextLine();
	    String[] ServerProp = s.split(" ");
		MazeServer serv=new MazeServer(Integer.parseInt(ServerProp[0]),5000000, Integer.parseInt(ServerProp[1]));
		ClientHandlerStub CH = new ClientHandlerStub();
		try {
			serv.Start(CH);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
