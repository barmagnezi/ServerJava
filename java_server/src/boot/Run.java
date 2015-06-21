package boot;

import sModel.MazeClientHandler;
import sModel.Server;
import sPresenter.SPresenter;
import sView.serverWindow;

/**
 * The class runs the Server side of the project.
 * @author Bar Magnezi(209043827) and Senia Kalma(321969941)
 * @version 1.0
 * @since 17.5.2015
 */
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
