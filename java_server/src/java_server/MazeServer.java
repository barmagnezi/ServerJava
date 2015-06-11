package java_server;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import model.Model;
import model.OffLineModel;
import presenter.Presenter;
import presenter.PropertiesModel;
import sModel.SModel;
//public class MazeServer extends MyServer implements SModel{
public class MazeServer extends MyServer implements SModel{

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

	@Override
	public void generateMaze(String name, int rows, int cols) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Maze getMaze(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void solveMaze(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Solution getSolution(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		/*XMLEncoder e = null;	=================================================Not finished - YOM STUDENT
		try {
			e = new XMLEncoder(new FileOutputStream("resources/properties.xml"));
		} catch (FileNotFoundException e1) {
			this.setChanged();
			this.notifyObservers("error while saving the properties.");
		}
		e.writeObject(this.properties);
		e.flush();
		e.close();*/
	}

	@Override
	public void setProperties(PropertiesModel mproperties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getClue(String arg) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
