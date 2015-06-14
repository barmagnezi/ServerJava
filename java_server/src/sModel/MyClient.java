package sModel;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class MyClient {
	Socket client;
	int clientNum;
	Date timeConnected;
	
	public MyClient(Socket sock, int num, Date t){
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
	public Date getTimeConnected() {
		return timeConnected;
	}
	public void setTimeConnected(Date timeConnected) {
		this.timeConnected = timeConnected;
	}
	public int getClientNum() {
		return clientNum;
	}
	public void setClientNum(int clientNum) {
		this.clientNum = clientNum;
	}

}	//MyClient


