package java_server;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
	static int clientNum=1;
	int dely;
	ServerSocket myServer;
	ClientHandler ch;
	ExecutorService executor;
	Socket someClient;
	ArrayList<MyClient> Clients;
	private SPropertiesModel properties;
	
	private boolean flagK;
	
	public MazeServer(ClientHandler ch){
		this.ch=ch;
		ch.newProp("resources/properties.xml");
		run=true;
	}
	
	@Override
	public void start(){
		System.out.println("Maze server START");
		System.out.println("<---SERVER side--->");
		//this.port=4900;
		//System.out.println("Def Port is: "+this.port);
		Clients = new ArrayList<MyClient>();
		//
		try {
			try {
				this.myServer = new ServerSocket(port);
			} catch (BindException e) {
				sendMsg("Port is already used, choose another one");
			}
			if(myServer!=null)
				myServer.setSoTimeout(dely);
			executor = Executors.newFixedThreadPool(Allowed);
			if(myServer!=null){
				while(run){
					try {
						someClient=myServer.accept();
					} catch (SocketException e) {
						System.out.println("accept with port close//kill accept");
						return;
					}

					Clients.add(new MyClient(someClient, clientNum, new Date()));
					sendUpdate();
					if(executor!=null){
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
									sendMsg("Error handeling a client("+clientNum+")");
								}
							}
						});
					}
				}
			}
			if(ch!=null)
				ch.close();
			if(myServer!=null)
				myServer.close();
		} catch (IOException e1) {
			if(flagK==false)
				sendMsg("Error opening a server");
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
			sendMsg("Error checking external IP address");
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
			                whatismyip.openStream()));
		} catch (IOException e1) {
			sendMsg("Error checking external IP address");
		}

		String ip = null;
		try {
			ip = in.readLine();
		} catch (IOException e) {
			sendMsg("Error checking external IP address");
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
		//System.out.println("set Port func in model, port "+port);
		this.port=port;
		flagK=true;
		stop();
		run=true;
		try {
			if(myServer!=null)
				myServer.close();
		} catch (IOException e) {
			System.out.println("SEEAAE");
		}
		start();
		flagK=false;
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
		//Close all active users ..
		for(int i=0;i<Clients.size();i++){
			try {
				Clients.get(i).Close();
			} catch (Exception e) {
				sendMsg("Error while closing socket of client num "+Clients.get(i).getClientNum());
			}
		}
		Clients.removeAll(Clients);
		sendUpdate();
		try {
			if(myServer!=null)
				myServer.close();
		} catch (IOException e) {
			sendMsg("Error closing the server");
		}
		if(flagK)
			return;
		//Closing server
		if(ch!=null)
			ch.close();
		
		run=false;
		//if(executor!=null)
		//	executor.shutdown();
		XMLEncoder e = null;
		try {
			e = new XMLEncoder(new FileOutputStream("resources/serverProperties.xml"));
		} catch (FileNotFoundException e1) {
			sendMsg("error while saving the properties.");
		}
		e.writeObject(this.properties);
		e.flush();
		e.close();
		System.out.println("KILL");
		flagK=false;
	}

}		//MazeServer class close.