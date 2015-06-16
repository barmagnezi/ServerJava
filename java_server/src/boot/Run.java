package boot;

import sModel.MazeClientHandler;
import sModel.MazeClientHandlerwithoutMVP;
import sModel.MazeServer;
import sPresenter.SPresenter;
import sView.serverWindow;

public class Run {
	public static void main(String[] args) {
		MazeClientHandler ch = new MazeClientHandler();
		MazeServer m=new MazeServer(ch);
		//View setting
		serverWindow v = new serverWindow("Server", 600, 600);
		SPresenter p=new SPresenter(v, m); 
		v.addObserver(p);
		m.addObserver(p);
		v.start();
	}

}
