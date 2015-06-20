package boot;

import sModel.MazeClientHandler;
import sModel.Server;
import sPresenter.SPresenter;
import sView.serverWindow;

public class Run {
	public static void main(String[] args) {
		MazeClientHandler ch = new MazeClientHandler();
		Server m=new Server(ch);
		//View setting
		serverWindow v = new serverWindow("Server", 600, 600);
		SPresenter p=new SPresenter(v, m); 
		v.addObserver(p);
		m.addObserver(p);
		v.start();
	}
}
