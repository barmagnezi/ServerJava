package sModel;

import java.io.IOException;
import java.net.Socket;

public class MyClient {
	Socket client;
	int clientNum;
	double timeConnected;
	
	public MyClient(Socket sock, int num, double t){
		client=sock;
		clientNum=num;
		timeConnected=t;
	}
	public void Close() throws Exception{
		client.close();
	}
	public Socket getClient() {
		return client;
	}
	public void setClient(Socket cli) {
		client = cli;
	}
	public double getTimeConnected() {
		return timeConnected;
	}
	public void setTimeConnected(double timeConnected) {
		this.timeConnected = timeConnected;
	}
	public int getClientNum() {
		return clientNum;
	}
	public void setClientNum(int clientNum) {
		this.clientNum = clientNum;
	}

}	//MyClient


