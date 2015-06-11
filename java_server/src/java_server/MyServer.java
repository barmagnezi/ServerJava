package java_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import model.OffLineModel;
import presenter.Presenter;
import presenter.PropertiesModel;
import sModel.SModel;

public class MyServer {
	protected int port;
	protected boolean run;
	int Allowed;
	static int clientNum=0;
	int dely;
	ClientHandler CH;
	ServerSocket myServer;
	
	public MyServer(int port, int Dely,int numOfClients){
		run=true;
		this.port=port;
		this.Allowed=numOfClients;
		this.dely=Dely;
	}
	

	public void Start(ClientHandler ch) {
		this.CH=ch;
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
		MyServer.clientNum = clientNum;
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
