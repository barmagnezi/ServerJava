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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import View.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.BFSSearcher;
import algorithms.search.Solution;
import algorithms.search.aStar.AstarSearcher;
import model.Model;
import model.OffLineModel;
import presenter.Presenter;
import presenter.PropertiesModel;
import sModel.MyClient;
import sModel.SModel;
import sPresenter.SPropertiesModel;
//public class MazeServer extends MyServer implements SModel{
public class MazeServer extends Observable implements SModel{
	
	//From old MyServer
	protected int port;
	protected boolean run;
	int Allowed;
	static int clientNum=0;
	int dely;
	ServerSocket myServer;
	ClientHandler ch;
	ExecutorService executor;
	Socket someClient;
	ArrayList<MyClient> Clients;
	private SPropertiesModel properties;
	
	public MazeServer(ClientHandler ch,int port, int Dely,int numOfClients){
		this.ch=ch;
		ch.newProp(path);
		run=true;
		this.port=port;
		this.Allowed=numOfClients;
		this.dely=Dely;
	}
	
	@Override
	public void start(){
		System.out.println("Maze server START");
		System.out.println("<---SERVER side--->");
		System.out.println("Port is: "+this.port);
		//this.port=4900;
		//System.out.println("Def Port is: "+this.port);
		Clients = new ArrayList<MyClient>();
		try {
			this.myServer = new ServerSocket(port);
			this.myServer.setSoTimeout(dely);
			executor = Executors.newFixedThreadPool(Allowed);
			while(run){
				someClient=myServer.accept();
				Clients.add(new MyClient(someClient, clientNum, System.currentTimeMillis()));
				sendUpdate();
				executor.execute (new Runnable() {
					@Override
					public void run() {
						try {
							clientNum++;
							System.out.println("Client "+clientNum+" CONNECTED");
							OffLineModel m=new OffLineModel();
							ch.HandleClient(someClient.getInputStream(), someClient.getOutputStream());
							someClient.getInputStream().close();
							someClient.getOutputStream().close();
							for(int i=0;i<Clients.size();i++)
								if(Clients.get(i).getClient()==someClient)
									Clients.remove(i);
							someClient.close();
							sendUpdate();
							clientNum--;
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
	
	protected void sendUpdate() {
		this.setChanged();
		this.notifyObservers("update");
	}
	protected void sendMsg(String msg) {
		this.setChanged();
		this.notifyObservers(msg);
	}

	@Override
	public String GetIP() {
		// http://stackoverflow.com/questions/2939218/getting-the-external-ip-address-in-java
		URL whatismyip = null;
		try {
			whatismyip = new URL("http://checkip.amazonaws.com");
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
			                whatismyip.openStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String ip = null;
		try {
			ip = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //you get the IP as a String
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
	/**
	 * Stops the work and saves the data.
	 */
	@Override
	public void stop() {
		//close all active users ..
		for(int i=0;i<Clients.size();i++){
			try {
				Clients.get(i).Close();
			} catch (Exception e) {
				sendMsg("Error while closing socket of client num "+Clients.get(i).getClientNum());
			}
		}
		Clients.removeAll(Clients);
		
		run=false;
		try {
			myServer.close();
		} catch (IOException e) {
			sendMsg("Error while closing the server");
			e.printStackTrace();
		}
		executor.shutdown();
	}
	
	/**
	 * Setting the current properties with an inputed PropertiesModel Object.
	 */
	public void setProperties(SPropertiesModel prop){
		properties=(SPropertiesModel) prop;
		this.port=properties.getPort();
		this.dely=properties.getDely();
		executor = Executors.newFixedThreadPool(properties.getAllowedClients());
	}

	public int getPort() {
		return port;
	}
	public boolean isRun() {
		return run;
	}
	public int getAllowed() {
		return Allowed;
	}
	public void setAllowed(int allowed) {
		Allowed = allowed;
	}
	public int getDely() {
		return dely;
	}
	public void setDely(int dely) {
		this.dely = dely;
	}
	@Override
	public void setPort(int port) {
		this.port=port;
		killServer();
		start();
	}
	@Override
	public ArrayList<MyClient> getUsers() {
		return Clients;
	}
	@Override
	public void killServer() {
		if(clientNum>0){
			this.setChanged();
			this.notifyObservers("Can't close server when some("+clientNum+") are connected");
		}
		else{
			try {
				if(myServer!=null)
					//if(!someClient.isClosed())
					//	someClient.close();
					myServer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(executor!=null)
				executor.shutdown();
		}
		System.out.println("KILL");
	}

}		//MazeServer class close.