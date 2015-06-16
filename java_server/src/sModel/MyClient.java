package sModel;

import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyClient {
	Socket client;
	String IP;
	String port;
	String localPort;
	int clientNum;
	String timeConnected;
	
	public MyClient(Socket s, int num, Date t){
		if(num!=-1){
			client=s;
			String helper[] = client.getRemoteSocketAddress().toString().split(":");	//	/127.0.0.1:8327
			IP=helper[0].substring(1, helper[0].length());
			localPort=helper[1];
			helper=client.getLocalSocketAddress().toString().split(":");	//	/127.0.0.1:5401
			port=helper[1];
			clientNum=num;
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			timeConnected=df.format(t);
		}else{
			clientNum=num;
			IP=port=localPort=timeConnected="";
		}
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
	public String getTimeConnected() {
		return timeConnected;
	}
	public void setTimeConnected(String timeConnected) {
		this.timeConnected = timeConnected;
	}
	public int getClientNum() {
		return clientNum;
	}
	public void setClientNum(int clientNum) {
		this.clientNum = clientNum;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getLocalPort() {
		return localPort;
	}
	public void setLocalPort(String localPort) {
		this.localPort = localPort;
	}

}	//MyClient


