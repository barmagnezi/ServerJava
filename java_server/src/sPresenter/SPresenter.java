package sPresenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import View.Command;
import sModel.SModel;
import sView.SView;
import view.View;
import model.Model;

/**
* The Presenter class implements Observer.
* sets the Model, View, and a hashmap of eligible commands.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 17.5.2015
*/
public class SPresenter implements Observer{
	SModel model;
	SView view;
	HashMap<String, Command> commands;
	
	/**
	 * A constructor receiving a View and a Model and sets them as his own.
	 * Also it sets the properties XML to be the properties.
	 * @param v The View object.
	 * @param m The Model object.
	 * @param row The number of rows our maze will have (>2)
	 */
	public SPresenter(SView v, SModel m) {
		model=m;
		view=v;
		commands=new HashMap<String, Command>();
		addAllCommands();
		view.setCommands(commands);
		//model.setCommands(commands);	//Only for sending an update when a client connects/disconnects.	-	changed to send "update" throw the notify.
		setNewProperties("resources/serverProperties.xml");
	}
	public void setNewProperties(String path){
		InputStream from = null;
		SPropertiesModel Mproperties;
		try {
			from = new FileInputStream(path);
			Mproperties = new SPropertiesModel(from);
		} catch (Exception e) {
			view.displayString(path+" not found");
			File theDir = new File("resources");
			theDir.mkdirs();
			Mproperties = new SPropertiesModel(null);}		
		model.setProperties(Mproperties);
		//view.getDiagsMode(Mproperties.isDiag());
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg!=null && ((String)arg).equals("start")){
			//System.out.println("model.start()");
			new Thread(new Runnable() {
				@Override
				public void run() {
					model.start();
				}
			}).start();
			//view.displayString("\nIf you run the program first time use the 'help -h' command to see how to use this command line interface\n");
			return;
		}
		else{
			if(o == view){
				Command c=view.getUserCommand();
				c.doCommand((String) arg,null);
			}
			if(o==model){
				if(arg==null)
					return;
				else{
					if(arg=="update")
						view.update(model.getUsers());
					else
						view.displayString((String)arg);
				}					
			}
		}
	}
	

	//create commands
	private void addAllCommands(){
		commands.put("getIP", new getIPCommand());
		commands.put("getPort", new getPortCommand());
		commands.put("setPort", new setPortCommand());
		commands.put("killServer", new killServerCommand());
		//Old
		commands.put("exit", new exitCommand());
		commands.put("help", new helpCommand());
		commands.put("setNewProperties", new setPropertiesCommand());
	}
	
	//commands
	public class getIPCommand implements Command{
		@Override
		public void doCommand(String arg, PrintStream out) {
			view.setIP(model.GetIP());
		}
	}
	public class getPortCommand implements Command{
		@Override
		public void doCommand(String arg, PrintStream out) {
			view.setPort(model.GetPort());
		}
	}
	public class setPortCommand implements Command{
		@Override
		public void doCommand(String arg, PrintStream out) {
			System.out.println("Presenter, port:"+arg);
			model.setPort(Integer.parseInt(arg));
		}
	}
	public class killServerCommand implements Command{
		@Override
		public void doCommand(String arg, PrintStream out) {
			model.killServer();
		}
	}
	
	public class exitCommand  implements Command {
		@Override
		public void doCommand(String arg,PrintStream out) {
			System.out.println("exit");
			model.stop();
			//close all we need
		}
	}
	public class helpCommand  implements Command {
		@Override
		public void doCommand(String arg,PrintStream out) {
			view.displayString("help-show all the commands\n\n"
					+ "generateMaze-create new maze with the parameters\n"
					+ "		syntax:generateMaze <mazeName> <numberOfrows>,<numberOfColumns>\n\n"
					+ "displayMaze-print the maze\n"
					+ "		syntax:displayMaze <mazeName>\n\n"
					+ "solveMaze-create solution to maze and save it\n"
					+ "		syntax:solveMaze <mazeName>\n\n"
					+ "displaySolution-print the solution of maze\n"
					+ "		syntax:displaySolution <mazeName>\n\n"
					+ "exit-exit from program and save all the changes\n"
					+ "---------------------------------------------------------\n");
			
		}
	}
	public class setPropertiesCommand implements Command{

		@Override
		public void doCommand(String arg, PrintStream out) {
			setNewProperties(arg);
		}
		
	}

		
}
