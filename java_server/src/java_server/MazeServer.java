package java_server;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Observable;
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
public class MazeServer extends Observable implements SModel{
	
	//From old MyServer
	protected int port;
	protected boolean run;
	int Allowed;
	static int clientNum=0;
	int dely;
	ClientHandler CH;
	ServerSocket myServer;
	ClientHandler ch;
	
	public MazeServer(ClientHandler ch,int port, int Dely,int numOfClients){
		this.ch=ch;
		run=true;
		this.port=port;
		this.Allowed=numOfClients;
		this.dely=Dely;
	}
	
	@Override
	public void start(){
		System.out.println("Maze server START");
		System.out.println("<---SERVER side--->");
		this.port=4900;
		System.out.println("Def Port is: "+this.port);
		try {
			this.myServer = new ServerSocket(port);
			this.myServer.setSoTimeout(dely);
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

	@Override
	public String GetIP() {
		String ip = null;
		try {
			ip= Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ip;
	}


	@Override
	public int GetPort() {
		if(myServer!=null)
			return myServer.getLocalPort();
		else
			return -1;
		
	}
	
	//From old MyServer
	
	//START
	public void Stop(){
		//close all active servers ..
		run=false;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public int getAllowed() {
		return Allowed;
	}

	public void setAllowed(int allowed) {
		Allowed = allowed;
	}

	public static int getClientNum() {
		return clientNum;
	}

	public static void setClientNum(int clientNum) {
		//MyServer.clientNum = clientNum;
	}

	public int getDely() {
		return dely;
	}

	public void setDely(int dely) {
		this.dely = dely;
	}

	public ClientHandler getCH() {
		return CH;
	}

	public void setCH(ClientHandler cH) {
		CH = cH;
	}

}