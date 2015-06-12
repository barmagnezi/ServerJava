package boot;

import java.util.Scanner;

import sModel.SModel;
import model.OffLineModel;
import sPresenter.SPresenter;
import sPresenter.SPresenter;
import sView.serverWindow;
import java_server.ClientHandlerStub;
import java_server.MazeClientHandler;
import java_server.MazeServer;


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
		SPresenter p=new SPresenter(v, m); 
		v.addObserver(p);
		m.addObserver(p);
		v.start();
		
		//before
		/*System.out.println("Enter command\nStart <port-number> <number-of-client>");
		int port,num;
		String s;
		Scanner in = new Scanner(System.in);
	    s = in.nextLine();
	    String[] ServerProp = s.split(" ");
		MazeServer serv=new MazeServer(Integer.parseInt(ServerProp[0]),5000000, Integer.parseInt(ServerProp[1]));
		MazeClientHandler CH = new MazeClientHandler();
		try {
			serv.Start(CH);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
