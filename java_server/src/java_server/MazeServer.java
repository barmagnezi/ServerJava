package java_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.OffLineModel;
import presenter.Presenter;
public class MazeServer extends MyServer {

	public MazeServer(int port, int Dely, int numOfClients) {
		super(port, Dely, numOfClients);
		
	}
	
	@Override
	public void Start(ClientHandler ch){
		MazeClientHandler CH=(MazeClientHandler)ch;
		System.out.println("<---SERVER side--->");
		System.out.println("Enter port:");
		try {
			myServer = new ServerSocket(port);
			myServer.setSoTimeout(dely);
			System.out.println("Enter number of clients:");
			ExecutorService executor = Executors.newFixedThreadPool(Allowed);
			while(run){
				Socket someClient=myServer.accept();
				executor.execute (new Runnable() {
					@Override
					public void run() {
						try {
							clientNum++;
							System.out.println("Client "+clientNum+" CONNECTED");
							OffLineModel m=new OffLineModel();
							Presenter p=new Presenter(CH, m);
							CH.addObserver(p);
							m.addObserver(p);
							CH.HandleClient(someClient.getInputStream(), someClient.getOutputStream());
							CH.start();
							someClient.getInputStream().close();
							someClient.getOutputStream().close();
							someClient.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
			myServer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}
