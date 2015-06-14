package java_server;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sModel.MyClient;
import sModel.SModel;
import sPresenter.SPropertiesModel;
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
	
	public MazeServer(ClientHandler ch){
		this.ch=ch;
		//ch.newProp("resources/gameProperties.xml");
		run=true;
	}
	
	@Override
	public void start(){
		System.out.println("Maze server START");
		System.out.println("<---SERVER side--->");
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
		sendMsg("update");
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
	/**
	 * Setting the current properties with an inputed PropertiesModel Object.
	 */
	public void setProperties(SPropertiesModel prop){
		properties=(SPropertiesModel) prop;
		System.out.println("Setting Server props");
		if(prop!=null){
			this.port=properties.getPort();
			this.dely=properties.getDely();
			this.Allowed=properties.getAllowedClients();
		}
		else{

			this.setPort(5400);
			this.setDely(5000000);
		}
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
	/**
	 * Stops the work and saves the data.
	 */
	@Override
	public void stop() {

		try {
			myServer.close();
		} catch (IOException e) {
			sendMsg("Error while closing the server");
			e.printStackTrace();
		}
		executor.shutdown();
	}
	
	@Override
	public void killServer() {
		//Close all active users ..
		for(int i=0;i<Clients.size();i++){
			try {
				Clients.get(i).Close();
			} catch (Exception e) {
				sendMsg("Error while closing socket of client num "+Clients.get(i).getClientNum());
			}
		}
		Clients.removeAll(Clients);
		
		//Closing server
		run=false;
		try {
			if(myServer!=null)
				myServer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(executor!=null)
			executor.shutdown();
		XMLEncoder e = null;
		try {
			e = new XMLEncoder(new FileOutputStream("resources/serverProperties.xml"));
		} catch (FileNotFoundException e1) {
			this.setChanged();
			this.notifyObservers("error while saving the properties.");
		}
		e.writeObject(this.properties);
		e.flush();
		e.close();
		System.out.println("KILL");
	}

}		//MazeServer class close.